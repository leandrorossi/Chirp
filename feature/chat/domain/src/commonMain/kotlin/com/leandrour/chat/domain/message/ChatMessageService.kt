package com.leandrour.chat.domain.message

import com.leandrour.chat.domain.models.ChatMessage
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result

interface ChatMessageService {
    suspend fun fetchMessages(
        chatId: String,
        before: String? = null,
    ): Result<List<ChatMessage>, DataError.Remote>
}