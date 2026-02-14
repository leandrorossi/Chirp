package com.leandrour.chirp

import com.leandrour.chirp.windows.WindowState

data class ApplicationState(
    val windows: List<WindowState> = listOf(WindowState())
)
