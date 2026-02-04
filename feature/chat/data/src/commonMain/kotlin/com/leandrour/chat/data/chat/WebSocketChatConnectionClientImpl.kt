package com.leandrour.chat.data.chat

import com.leandrour.chat.data.dto.WebSocketMessageDto
import com.leandrour.chat.data.mappers.toNewMessage
import com.leandrour.chat.data.network.KtorWebSocketConnector
import com.leandrour.chat.database.ChirpChatDatabase
import com.leandrour.chat.domain.chat.ChatConnectionClient
import com.leandrour.chat.domain.chat.ChatRepository
import com.leandrour.chat.domain.error.ConnectionError
import com.leandrour.chat.domain.message.MessageRepository
import com.leandrour.chat.domain.models.ChatMessage
import com.leandrour.chat.domain.models.ChatMessageDeliveryStatus
import com.leandrour.core.domain.auth.SessionStorage
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.onFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class WebSocketChatConnectionClientImpl(
    private val webSocketConnector: KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val db: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val messageRepository: MessageRepository
) : ChatConnectionClient {

    override val chatMessages: Flow<ChatMessage>
        get() = TODO("Not yet implemented")

    override val connectionState = webSocketConnector.connectionState

    override suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError> {
        val outgoingDto = message.toNewMessage()
        val webSocketMessage = WebSocketMessageDto(
            type = outgoingDto.type.name,
            payload = json.encodeToString(outgoingDto)
        )
        val rawJsonPayload = json.encodeToString(webSocketMessage)

        return webSocketConnector
            .sendMessage(rawJsonPayload)
            .onFailure { error ->
                messageRepository.updateMessageDeliveryStatus(
                    messageId = message.id,
                    status = ChatMessageDeliveryStatus.FAILED
                )
            }
    }
}