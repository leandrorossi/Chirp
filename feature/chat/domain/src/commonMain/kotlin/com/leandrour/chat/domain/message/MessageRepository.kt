package com.leandrour.chat.domain.message

import com.leandrour.chat.domain.models.ChatMessage
import com.leandrour.chat.domain.models.ChatMessageDeliveryStatus
import com.leandrour.chat.domain.models.MessageWithSender
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>

    suspend fun fetchMessages(
        chatId: String,
        before: String? = null,
    ): Result<List<ChatMessage>, DataError>

    fun getMessageForChat(chatId: String): Flow<List<MessageWithSender>>
}