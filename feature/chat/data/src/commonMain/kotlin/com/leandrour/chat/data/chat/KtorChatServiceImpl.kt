package com.leandrour.chat.data.chat

import com.leandrour.chat.data.dto.ChatDto
import com.leandrour.chat.data.dto.CreateChatRequestDto
import com.leandrour.chat.data.mappers.toDomain
import com.leandrour.chat.domain.chat.ChatService
import com.leandrour.chat.domain.models.Chat
import com.leandrour.core.data.networking.get
import com.leandrour.core.data.networking.post
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.map
import io.ktor.client.HttpClient

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
}