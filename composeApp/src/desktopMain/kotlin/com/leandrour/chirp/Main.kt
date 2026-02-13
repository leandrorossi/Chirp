package com.leandrour.chirp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.leandrour.chirp.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Chirp"
        ) {
            App()
        }
    }
}