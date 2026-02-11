package com.leandrour.chat.data.participant

import com.leandrour.chat.data.dto.ChatParticipantDto
import com.leandrour.chat.data.dto.ConfirmProfilePictureDto
import com.leandrour.chat.data.dto.ProfilePictureUploadUrlsDto
import com.leandrour.chat.data.mappers.toDomain
import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.chat.domain.models.ProfilePictureUploadUrls
import com.leandrour.chat.domain.participant.ChatParticipantService
import com.leandrour.core.data.networking.get
import com.leandrour.core.data.networking.post
import com.leandrour.core.data.networking.safeCall
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class KtorChatParticipantServiceImpl(
    private val httpClient: HttpClient
) : ChatParticipantService {

    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants",
            queryParams = mapOf(
                "query" to query
            )
        ).map { it.toDomain() }
    }

    override suspend fun getLocalParticipant(): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants"
        ).map { it.toDomain() }
    }

    override suspend fun getProfilePictureUploadUrl(mimeType: String): Result<ProfilePictureUploadUrls, DataError.Remote> {
        return httpClient.post<Unit, ProfilePictureUploadUrlsDto>(
            route = "/participants/profile-picture-upload",
            queryParams = mapOf(
                "mimeType" to mimeType
            ),
            body = Unit
        ).map { it.toDomain() }
    }

    override suspend fun uploadProfilePicture(
        uploadUrl: String,
        imageBytes: ByteArray,
        headers: Map<String, String>
    ): EmptyResult<DataError.Remote> {
        return safeCall {
            httpClient.put {
                url(uploadUrl)
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                setBody(imageBytes)
            }
        }
    }

    override suspend fun confirmProfilePictureUpload(publicUrl: String): EmptyResult<DataError.Remote> {
        return httpClient.post<ConfirmProfilePictureDto, Unit>(
            route = "/participants/confirm-profile-picture",
            body = ConfirmProfilePictureDto(publicUrl)
        )
    }
}