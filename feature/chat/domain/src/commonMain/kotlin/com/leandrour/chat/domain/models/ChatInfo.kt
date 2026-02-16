package com.leandrour.chat.domain.models

data class ChatInfo(
    val chat: Chat,
    val messages: List<MessageWithSender>
)
