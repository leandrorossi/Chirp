package com.leandrour.chat.data.message

import com.leandrour.chat.data.mappers.toDomain
import com.leandrour.chat.data.mappers.toEntity
import com.leandrour.chat.database.ChirpChatDatabase
import com.leandrour.chat.domain.message.ChatMessageService
import com.leandrour.chat.domain.message.MessageRepository
import com.leandrour.chat.domain.models.ChatMessage
import com.leandrour.chat.domain.models.ChatMessageDeliveryStatus
import com.leandrour.chat.domain.models.MessageWithSender
import com.leandrour.core.data.database.safeDatabaseUpdate
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class OfflineFirstMessageRepositoryImpl(
    private val db: ChirpChatDatabase,
    private val chatMessageService: ChatMessageService
) : MessageRepository {

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
                    db.chatMessageDao.upsertMessageAndSyncIfNecessary(
                        chatId = chatId,
                        serverMessages = messages.map { it.toEntity() },
                        pageSize = ChatMessageConstants.PAGE_SIZE,
                        shouldSync = before == null
                    )
                    messages
                }
            }
    }

    override fun getMessageForChat(chatId: String): Flow<List<MessageWithSender>> {
        return db
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { messages ->
                messages.map { it.toDomain() }
            }
    }
}