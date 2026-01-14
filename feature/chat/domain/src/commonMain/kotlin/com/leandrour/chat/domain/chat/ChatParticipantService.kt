package com.leandrour.chat.domain.chat

import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote>
}