package com.leandrour.chat.presentation.chat_list

import com.leandrour.core.presentation.util.UiText

sealed interface ChatListEvent {
    data object OnLogoutSuccess: ChatListEvent
    data class OnLogoutError(val error: UiText): ChatListEvent
}