package com.leandrour.chat.presentation.model

import com.leandrour.chat.domain.models.ChatMessageDeliveryStatus
import com.leandrour.core.designsystem.components.avatar.ChatParticipantUi
import com.leandrour.core.presentation.util.UiText

sealed interface MessageUi {
    data class LocalUserMessage(
        val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val isMenuOpen: Boolean,
        val formattedSentTime: UiText
    ) : MessageUi

    data class OtherUserMessage(
        val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUi
    ) : MessageUi

    data class DataSeparator(
        val id: String,
        val date: UiText
    ) : MessageUi
}