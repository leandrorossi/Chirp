package com.leandrour.chat.presentation.mappers

import com.leandrour.chat.domain.models.MessageWithSender
import com.leandrour.chat.presentation.model.MessageUi
import com.leandrour.chat.presentation.util.DateUtils

fun MessageWithSender.toUi(localUserId: String): MessageUi {
    val isFromLocalUser = this.sender.userId == localUserId
    return if (isFromLocalUser) {
        MessageUi.LocalUserMessage(
            id = message.id,
            content = message.content,
            deliveryStatus = message.deliveryStatus,
            isMenuOpen = false,
            formattedSentTime = DateUtils.formattedMessageTime(instant = message.createdAt)
        )
    } else {
        MessageUi.OtherUserMessage(
            id = message.id,
            content = message.content,
            sender = sender.toUi(),
            formattedSentTime = DateUtils.formattedMessageTime(instant = message.createdAt)
        )
    }
}