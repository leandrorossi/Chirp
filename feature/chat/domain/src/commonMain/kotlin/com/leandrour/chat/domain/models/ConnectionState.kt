package com.leandrour.chat.domain.models

enum class ConnectionState {
    CONNECTED,
    DISCONNECTED,
    CONNECTING,
    ERROR_NETWORK,
    ERROR_UNKNOWN,
}