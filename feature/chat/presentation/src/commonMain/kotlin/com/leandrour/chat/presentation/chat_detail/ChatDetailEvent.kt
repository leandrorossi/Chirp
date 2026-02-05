package com.leandrour.chat.presentation.chat_detail

import com.leandrour.core.presentation.util.UiText

sealed interface ChatDetailEvent {
    data object OnChatLeft: ChatDetailEvent
    data object OnNewMessage: ChatDetailEvent
    data class OnError(val error: UiText): ChatDetailEvent
}