package com.leandrour.chat.domain.participant

import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.chat.domain.models.ProfilePictureUploadUrls
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote>

    suspend fun getLocalParticipant(): Result<ChatParticipant, DataError.Remote>

    suspend fun getProfilePictureUploadUrl(mimeType: String): Result<ProfilePictureUploadUrls, DataError.Remote>

    suspend fun uploadProfilePicture(
        uploadUrl: String,
        imageBytes: ByteArray,
        headers: Map<String, String>
    ): EmptyResult<DataError.Remote>

    suspend fun confirmProfilePictureUpload(publicUrl: String): EmptyResult<DataError.Remote>
}