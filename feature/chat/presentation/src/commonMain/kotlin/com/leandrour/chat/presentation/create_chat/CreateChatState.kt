package com.leandrour.chat.presentation.create_chat

import androidx.compose.foundation.text.input.TextFieldState
import com.leandrour.core.designsystem.components.avatar.ChatParticipantUi
import com.leandrour.core.presentation.util.UiText

data class CreateChatState(
    val queryTextState: TextFieldState = TextFieldState(),
    val selectedChatParticipants: List<ChatParticipantUi> = emptyList(),
    val isAddingParticipant: Boolean = false,
    val isLoading: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipantUi? = null,
    val searchError: UiText? = null,
    val isCreatingChat: Boolean = false
)