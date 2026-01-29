package com.leandrour.chat.presentation.mappers

import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.core.designsystem.components.avatar.ChatParticipantUi
import com.leandrour.core.domain.auth.User

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePicture
    )
}

fun User.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = id,
        username = username,
        initials = username.take(2).uppercase(),
        imageUrl = profilePictureUrl
    )
}