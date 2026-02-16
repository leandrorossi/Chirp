package com.leandrour.auth.presentation.forgot_password

sealed interface ForgotPasswordAction {
    data object OnSubmitClick : ForgotPasswordAction
}