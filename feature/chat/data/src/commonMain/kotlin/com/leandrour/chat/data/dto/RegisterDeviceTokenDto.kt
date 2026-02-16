package com.leandrour.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterDeviceTokenDto(
    val token: String,
    val platform: String
)
