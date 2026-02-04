package com.leandrour.chat.domain.message

import com.leandrour.chat.domain.models.ChatMessageDeliveryStatus
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.EmptyResult

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>
}