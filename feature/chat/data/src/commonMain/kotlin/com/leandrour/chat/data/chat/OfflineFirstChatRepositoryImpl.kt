package com.leandrour.chat.data.chat

import com.leandrour.chat.data.mappers.toDomain
import com.leandrour.chat.data.mappers.toEntity
import com.leandrour.chat.data.mappers.toLastMessageView
import com.leandrour.chat.database.ChirpChatDatabase
import com.leandrour.chat.database.entities.ChatWithParticipants
import com.leandrour.chat.domain.chat.ChatRepository
import com.leandrour.chat.domain.chat.ChatService
import com.leandrour.chat.domain.models.Chat
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstChatRepositoryImpl(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase
) : ChatRepository {

    override fun getChats(): Flow<List<Chat>> {
        return db.chatDao.getChatsWithActiveParticipants()
            .map { chatWithParticipants ->
                chatWithParticipants.map { it.toDomain() }
            }
    }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> {
        return chatService
            .getChats()
            .onSuccess { chats ->
                val chatsWithParticipants = chats.map { chat ->
                    ChatWithParticipants(
                        chat = chat.toEntity(),
                        participants = chat.participants.map { it.toEntity() },
                        lastMessage = chat.lastMessage?.toLastMessageView()
                    )
                }

                db.chatDao.upsertChatsWithParticipantsAndCrossRefs(
                    chats = chatsWithParticipants,
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                    messageDao = db.chatMessageDao
                )
            }
    }
}