package com.leandrour.chat.presentation.mappers

import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.core.designsystem.components.avatar.ChatParticipantUi

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePicture
    )
}