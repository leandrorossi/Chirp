package com.leandrour.chat.domain.models

data class ChatParticipant(
    val userId: String,
    val username: String,
    val profilePicture: String?,
) {
    val initials: String
        get() = username.take(2).uppercase()
}