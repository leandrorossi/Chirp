package com.leandrour.auth.presentation.login

sealed interface LoginEvent {
    data object Success : LoginEvent
}