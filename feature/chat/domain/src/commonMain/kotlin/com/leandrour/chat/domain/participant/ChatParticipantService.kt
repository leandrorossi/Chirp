package com.leandrour.chat.domain.participant

import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote>
    suspend fun getLocalParticipant(): Result<ChatParticipant, DataError.Remote>
}