package com.leandrour.chat.data.chat

import com.leandrour.chat.data.dto.ChatDto
import com.leandrour.chat.data.dto.CreateChatRequestDto
import com.leandrour.chat.data.dto.ParticipantRequest
import com.leandrour.chat.data.mappers.toDomain
import com.leandrour.chat.domain.chat.ChatService
import com.leandrour.chat.domain.models.Chat
import com.leandrour.core.data.networking.delete
import com.leandrour.core.data.networking.get
import com.leandrour.core.data.networking.post
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.asEmptyResult
import com.leandrour.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.delete

class KtorChatServiceImpl(
    private val httpClient: HttpClient
) : ChatService {

    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return httpClient.post<CreateChatRequestDto, ChatDto>(
            route = "/chat",
            body = CreateChatRequestDto(
                otherUserIds = otherUserIds
            )
        ).map { it.toDomain() }
    }

    override suspend fun getChats(): Result<List<Chat>, DataError.Remote> {
        return httpClient.get<List<ChatDto>>(
            route = "/chat"
        ).map { chatDtos ->
            chatDtos.map { it.toDomain() }
        }
    }

    override suspend fun getChatById(chatId: String): Result<Chat, DataError.Remote> {
        return httpClient.get<ChatDto>(
            route = "/chat/$chatId"
        ).map { it.toDomain() }
    }

    override suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote> {
        return httpClient.delete<Unit>(
            route = "/chat/leave/$chatId"
        ).asEmptyResult()
    }

    override suspend fun addParticipantsToChat(
        chatId: String,
        userIds: List<String>
    ): Result<Chat, DataError.Remote> {
        return httpClient.post<ParticipantRequest, ChatDto>(
            route = "/chat/$chatId/add",
            body = ParticipantRequest(
                userIds = userIds
            )
        ).map { it.toDomain() }
    }
}