package com.leandrour.chat.presentation.manage_chats

sealed interface ManageChatEvent {
    data object OnMembersAdded : ManageChatEvent
}