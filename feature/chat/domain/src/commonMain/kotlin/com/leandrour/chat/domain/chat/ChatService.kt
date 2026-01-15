package com.leandrour.chat.domain.chat

import com.leandrour.chat.domain.models.Chat
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result

interface ChatService {
    suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote>
}