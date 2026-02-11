package com.leandrour.chat.domain.participant

import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result

interface ChatParticipantRepository {
    suspend fun fetchLocalParticipant(): Result<ChatParticipant, DataError>
}