package com.leandrour.chat.presentation.mappers

import com.leandrour.chat.domain.models.MessageWithSender
import com.leandrour.chat.presentation.model.MessageUi
import com.leandrour.chat.presentation.util.DateUtils
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun List<MessageWithSender>.toUiList(localUserId: String): List<MessageUi> {
    return this
        .sortedByDescending { it.message.createdAt }
        .groupBy {
            it.message.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date
        }
        .flatMap { (date, messages) ->
            messages.map { it.toUi(localUserId) } + MessageUi.DateSeparator(
                id = date.toString(),
                date = DateUtils.formatDateSeparator(date)
            )
        }
}

fun MessageWithSender.toUi(localUserId: String): MessageUi {
    val isFromLocalUser = this.sender.userId == localUserId
    return if (isFromLocalUser) {
        MessageUi.LocalUserMessage(
            id = message.id,
            content = message.content,
            deliveryStatus = message.deliveryStatus,
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