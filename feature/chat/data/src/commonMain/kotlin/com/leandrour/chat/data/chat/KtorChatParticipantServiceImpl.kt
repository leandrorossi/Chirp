package com.leandrour.chat.data.chat

import com.leandrour.chat.data.dto.ChatParticipantDto
import com.leandrour.chat.data.mappers.toDomain
import com.leandrour.chat.domain.chat.ChatParticipantService
import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.core.data.networking.get
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.map
import io.ktor.client.HttpClient

class KtorChatParticipantServiceImpl(
    private val httpClient: HttpClient
) : ChatParticipantService{

    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants",
            queryParams = mapOf(
                "query" to query
            )
        ).map { it.toDomain() }
    }
}