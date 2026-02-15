package com.leandrour.chirp

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.window.application
import com.leandrour.chirp.di.desktopModule
import com.leandrour.chirp.di.initKoin
import com.leandrour.chirp.theme.rememberAppTheme
import com.leandrour.chirp.windows.ChirpWindow
import org.koin.compose.koinInject

fun main() {
    initKoin {
        modules(desktopModule)
    }

    application {
        val applicationStateHolder = koinInject<ApplicationStateHolder>()
        val applicationState by applicationStateHolder.state.collectAsState()
        val windows = applicationState.windows

        LaunchedEffect(windows) {
            if (windows.isEmpty()) {
                exitApplication()
            }
        }

        val appTheme = rememberAppTheme(applicationState.themePreference)

        for (window in windows) {
            key(window.id) {
                ChirpWindow(
                    appTheme = appTheme,
                    onCloseRequest = {
                        applicationStateHolder.onWindowCloseRequest(window.id)
                    },
                    onAddWindowClick = {
                        applicationStateHolder.onAddWindowClick()
                    },
                    onFocusChange = {

                    }
                )
            }
        }

        ChirpTrayMenu(
            state = applicationState.trayState,
            themePreferenceFromAppSettings = applicationState.themePreference,
            onThemePreferenceClick = applicationStateHolder::onThemePreferenceClick
        )
    }
}