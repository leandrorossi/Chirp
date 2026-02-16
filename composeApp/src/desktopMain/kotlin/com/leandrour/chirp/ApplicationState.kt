package com.leandrour.chirp

import androidx.compose.ui.window.TrayState
import com.leandro.ur.core.domain.preferences.ThemePreference
import com.leandrour.chirp.windows.WindowState

data class ApplicationState(
    val windows: List<WindowState> = listOf(WindowState()),
    val themePreference: ThemePreference = ThemePreference.SYSTEM,
    val trayState: TrayState = TrayState()
)
