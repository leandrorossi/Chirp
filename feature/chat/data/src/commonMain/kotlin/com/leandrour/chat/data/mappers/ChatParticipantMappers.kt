package com.leandrour.chat.data.mappers

import com.leandrour.chat.data.dto.ChatParticipantDto
import com.leandrour.chat.domain.models.ChatParticipant

fun ChatParticipantDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePicture = profilePictureUrl
    )
}