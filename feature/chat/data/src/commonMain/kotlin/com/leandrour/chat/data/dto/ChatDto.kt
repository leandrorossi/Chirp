package com.leandrour.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,
    val participantIds: List<ChatParticipantDto>,
    val lastActivityAt: String,
    val lastMessage: ChatMessageDto?,
)