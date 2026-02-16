package com.leandrour.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequestDto(
    val otherUserIds: List<String>,
)
