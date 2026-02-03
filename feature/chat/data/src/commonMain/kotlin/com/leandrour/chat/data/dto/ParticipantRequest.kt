package com.leandrour.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantRequest(
    val userIds: List<String>
)
