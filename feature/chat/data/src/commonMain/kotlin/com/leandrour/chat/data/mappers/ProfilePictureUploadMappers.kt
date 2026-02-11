package com.leandrour.chat.data.mappers

import com.leandrour.chat.data.dto.ProfilePictureUploadUrlsDto
import com.leandrour.chat.domain.models.ProfilePictureUploadUrls

fun ProfilePictureUploadUrlsDto.toDomain(): ProfilePictureUploadUrls {
    return ProfilePictureUploadUrls(
        uploadUrl = uploadUrl,
        publicUrl = publicUrl,
        headers = headers
    )
}