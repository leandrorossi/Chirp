package com.leandrour.chat.domain.chat

import com.leandrour.chat.domain.models.Chat
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
}