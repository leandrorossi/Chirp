package com.leandrour.chat.domain.chat

import com.leandrour.chat.domain.error.ConnectionError
import com.leandrour.chat.domain.models.ChatMessage
import com.leandrour.chat.domain.models.ConnectionState
import com.leandrour.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatConnectionClient {
    val chatMessages: Flow<ChatMessage>
    val connectionState: StateFlow<ConnectionState>
    suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError>
}