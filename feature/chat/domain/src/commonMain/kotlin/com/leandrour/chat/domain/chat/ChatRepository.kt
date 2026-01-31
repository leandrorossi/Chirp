package com.leandrour.chat.domain.chat

import com.leandrour.chat.domain.models.Chat
import com.leandrour.chat.domain.models.ChatInfo
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    fun getChatInfoById(chatId: String): Flow<ChatInfo>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
    suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote>
}