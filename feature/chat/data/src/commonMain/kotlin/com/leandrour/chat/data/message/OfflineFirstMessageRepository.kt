package com.leandrour.chat.data.message

import com.leandrour.chat.database.ChirpChatDatabase
import com.leandrour.chat.domain.message.MessageRepository
import com.leandrour.chat.domain.models.ChatMessageDeliveryStatus
import com.leandrour.core.data.database.safeDatabaseUpdate
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val db: ChirpChatDatabase
) : MessageRepository {

    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            db.chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                status = status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }
    }
}