package com.leandrour.chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfilePictureUploadUrlsDto(
    val uploadUrl: String,
    val publicUrl: String,
    val headers: Map<String, String>
)
