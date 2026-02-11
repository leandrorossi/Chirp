package com.leandrour.chat.data.participant

import com.leandrour.chat.domain.models.ChatParticipant
import com.leandrour.chat.domain.participant.ChatParticipantRepository
import com.leandrour.chat.domain.participant.ChatParticipantService
import com.leandrour.core.domain.auth.SessionStorage
import com.leandrour.core.domain.util.DataError
import com.leandrour.core.domain.util.Result
import com.leandrour.core.domain.util.onSuccess
import kotlinx.coroutines.flow.first

class OfflineFirstChatParticipantRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val chatParticipantService: ChatParticipantService
) : ChatParticipantRepository {

    override suspend fun fetchLocalParticipant(): Result<ChatParticipant, DataError> {
        return chatParticipantService
            .getLocalParticipant()
            .onSuccess { participant ->
                val currentAuthInfo = sessionStorage.observeAuthInfo().first()
                sessionStorage.set(
                    currentAuthInfo?.copy(
                        user = currentAuthInfo.user.copy(
                            id = participant.userId,
                            username = participant.username,
                            profilePictureUrl = participant.profilePicture
                        )
                    )
                )
            }
    }
}