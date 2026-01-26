package com.leandrour.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.leandrour.chat.database.entities.ChatEntity
import com.leandrour.chat.database.entities.ChatInfoEntity
import com.leandrour.chat.database.entities.ChatParticipantCrossRef
import com.leandrour.chat.database.entities.ChatParticipantEntity
import com.leandrour.chat.database.entities.ChatWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsertChat(chat: ChatEntity)

    @Upsert
    suspend fun upsertChats(chats: List<ChatEntity>)

    @Query("SELECT * FROM chatentity ORDER BY lastActivityAt DESC")
    @Transaction
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>

    @Query("SELECT * FROM chatentity WHERE chatId = :chatId")
    @Transaction
    suspend fun getChatById(chatId: String): ChatWithParticipants?

    @Query("SELECT chatId FROM chatentity")
    suspend fun getAllChatsId(): List<String>

    @Query("DELETE FROM chatentity")
    suspend fun deleteAllChats()

    @Query("DELETE FROM chatentity WHERE chatId = :chatId")
    suspend fun deleteChatById(chatId: String)

    @Transaction
    suspend fun deleteChatsByIds(chatIds: List<String>) {
        chatIds.forEach { chatId ->
            deleteChatById(chatId)
        }
    }

    @Query("SELECT COUNT(*) FROM chatentity")
    @Transaction
    fun getChatCount(): Flow<Int>

    @Query(
        """
        SELECT p.*
        FROM chatparticipantentity p
        JOIN chatparticipantcrossref cpcr ON p.userId = cpcr.userId
        WHERE cpcr.chatId = :chatId AND cpcr.isActive = true
        ORDER BY p.username
    """
    )
    fun getActiveParticipantsByChatId(chatId: String): Flow<List<ChatParticipantEntity>>

    @Query("SELECT * FROM chatentity WHERE chatId = :chatId")
    fun getChatInfoById(chatId: String): Flow<ChatInfoEntity?>

    @Transaction
    suspend fun upsertChatWithParticipantsAndCrossRefs(
        chat: ChatEntity,
        participants: List<ChatParticipantEntity>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantsCrossRefDao
    ) {
        upsertChat(chat)
        participantDao.upsertParticipants(participants)

        val crossRef = participants.map {
            ChatParticipantCrossRef(
                chatId = chat.chatId,
                userId = it.userId,
                isActive = true
            )
        }

        crossRefDao.upsetCrossRefs(crossRef)
        crossRefDao.syncChatParticipants(chat.chatId, participants)
    }

    @Transaction
    suspend fun upsertChatsWithParticipantsAndCrossRefs(
        chats: List<ChatWithParticipants>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantsCrossRefDao
    ) {
        upsertChats(chats.map { it.chat })

        val allParticipants = chats.flatMap { it.participants }
        participantDao.upsertParticipants(allParticipants)

        val allCrossRefs = chats.flatMap { chatWithParticipants ->
            chatWithParticipants.participants.map { participant ->
                ChatParticipantCrossRef(
                    chatId = chatWithParticipants.chat.chatId,
                    userId = participant.userId,
                    isActive = true
                )
            }
        }
        crossRefDao.upsetCrossRefs(allCrossRefs)

        chats.forEach { chat ->
            crossRefDao.syncChatParticipants(
                chatId = chat.chat.chatId,
                participantIds = chat.participants
            )
        }
    }
}