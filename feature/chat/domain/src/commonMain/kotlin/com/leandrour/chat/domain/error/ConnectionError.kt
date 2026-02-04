package com.leandrour.chat.domain.error

import com.leandrour.core.domain.util.Error

enum class ConnectionError: Error {
    NOT_CONNECTED,
    MESSAGE_SEND_FAILED
}