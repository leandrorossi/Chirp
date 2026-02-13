package com.leandrour.chat.presentation.profile.mediapicker

import androidx.compose.runtime.remember

@androidx.compose.runtime.Composable
actual fun rememberImagePickerLauncher(onResult: (PickedImageData) -> Unit): ImagePickerLauncher {
    return remember {
        ImagePickerLauncher(
            onLaunch = {}
        )
    }
}