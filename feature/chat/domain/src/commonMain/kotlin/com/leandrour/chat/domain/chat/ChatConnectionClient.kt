package com.leandrour.chat.domain.chat

import com.leandrour.chat.domain.models.ChatMessage
import com.leandrour.chat.domain.models.ConnectionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatConnectionClient {
    val chatMessages: Flow<ChatMessage>
    val connectionState: StateFlow<ConnectionState>
}