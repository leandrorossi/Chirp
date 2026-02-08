package com.leandrour.chat.data.message

import com.leandrour.chat.data.dto.OutgoingWebSocketDto
import com.leandrour.chat.data.dto.WebSocketMessageDto
import com.leandrour.chat.data.mappers.toDomain
import com.leandrour.chat.data.mappers.toEntity
import com.leandrour.chat.data.mappers.toWebSocketDto
import com.leandrour.chat.data.network.KtorWebSocketConnector
import com.leandrour.chat.database.ChirpChatDatabase
import com.leandrour.chat.domain.message.ChatMessageService
import com.leandrour.chat.domain.message.MessageRepository
import com.leandrour.chat.domain.models.ChatMessage
import com.leandrour.chat.domain.models.ChatMessageDeliveryStatus
import com.leandrour.chat.domain.models.MessageWithSender
import com.leandrour.chat.domain.models.OutgoingNewMessage
import com.leandrour.core.data.database.safeDatabaseUpdate
import com.leandrour.core.domain.auth.SessionStorage
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.onFailure
import com.leandrour.core.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.time.Clock

class OfflineFirstMessageRepositoryImpl(
    private val db: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val webSocketConnector: KtorWebSocketConnector,
    private val applicationScope: CoroutineScope
) : MessageRepository {

    override suspend fun sendMessage(message: OutgoingNewMessage): EmptyResult<DataError> {
        return safeDatabaseUpdate {
            val dto = message.toWebSocketDto()

            val localUser = sessionStorage.observeAuthInfo().first()?.user
                ?: return Result.Failure(DataError.Local.NOT_FOUND)

            val entity = dto.toEntity(
                senderId = localUser.id,
                deliveryStatus = ChatMessageDeliveryStatus.SENDING
            )
            db.chatMessageDao.upsertMessage(entity)

            return webSocketConnector
                .sendMessage(dto.toJsonPayload())
                .onFailure { error ->
                    applicationScope.launch {
                        db.chatMessageDao.updateDeliveryStatus(
                            messageId = entity.messageId,
                            timestamp = Clock.System.now().toEpochMilliseconds(),
                            status = ChatMessageDeliveryStatus.FAILED.name
                        )
                    }.join()
                }
        }
    }

    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            db.chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                status = status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }
    }

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError> {
        return chatMessageService
            .fetchMessages(chatId, before)
            .onSuccess { messages ->
                return safeDatabaseUpdate {
                    db.chatMessageDao.upsertMessagesAndSyncIfNecessary(
                        chatId = chatId,
                        serverMessages = messages.map { it.toEntity() },
                        pageSize = ChatMessageConstants.PAGE_SIZE,
                        shouldSync = before == null
                    )
                    messages
                }
            }
    }

    override suspend fun retryMessage(messageId: String): EmptyResult<DataError> {
        return safeDatabaseUpdate {
            val message = db.chatMessageDao.getMessageById(messageId)
                ?: return Result.Failure(DataError.Local.NOT_FOUND)

            db.chatMessageDao.upsertMessage(
                message.copy(
                    deliveryStatus = ChatMessageDeliveryStatus.SENDING.name,
                    timestamp = Clock.System.now().toEpochMilliseconds()
                )
            )

            val outgoingNewMessage = OutgoingWebSocketDto.NewMessage(
                chatId = message.chatId,
                messageId = messageId,
                content = message.content
            )
            return webSocketConnector
                .sendMessage(outgoingNewMessage.toJsonPayload())
                .onFailure {
                    applicationScope.launch {
                        db.chatMessageDao.updateDeliveryStatus(
                            messageId = messageId,
                            timestamp = Clock.System.now().toEpochMilliseconds(),
                            status = ChatMessageDeliveryStatus.FAILED.name
                        )
                    }.join()
                }
        }
    }

    override suspend fun deleteMessage(messageId: String): EmptyResult<DataError.Remote> {
        return chatMessageService
            .deleteMessage(messageId)
            .onSuccess {
                applicationScope.launch {
                    db.chatMessageDao.deleteMessageById(messageId)
                }.join()
            }
    }

    override fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>> {
        return db
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { messages ->
                messages.map { it.toDomain() }
            }
    }

    private fun OutgoingWebSocketDto.NewMessage.toJsonPayload(): String {
        val webSocketMessage = WebSocketMessageDto(
            type = type.name,
            payload = json.encodeToString(this)
        )

        return json.encodeToString(webSocketMessage)
    }
}