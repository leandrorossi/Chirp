package com.leandrour.chat.presentation.create_chat

import com.leandrour.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(val chat: Chat) : CreateChatEvent
}