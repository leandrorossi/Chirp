package com.leandrour.chat.data.mappers

import com.leandrour.chat.data.dto.ChatMessageDto
import com.leandrour.chat.domain.models.ChatMessage
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = Instant.parse(createdAt),
        senderId = senderId,
    )
}