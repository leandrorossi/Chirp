package com.leandrour.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketMessageDto(
    val type: String,
    val payload: String
)
