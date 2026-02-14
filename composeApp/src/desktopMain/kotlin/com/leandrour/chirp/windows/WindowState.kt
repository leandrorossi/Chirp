package com.leandrour.chirp.windows

import java.util.UUID

data class WindowState(
    val id: String = UUID.randomUUID().toString()
)
