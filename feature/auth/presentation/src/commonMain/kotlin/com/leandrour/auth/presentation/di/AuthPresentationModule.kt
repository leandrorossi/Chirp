package com.leandrour.auth.presentation.di

import com.leandrour.auth.presentation.email_verification.EmailVerificationViewModel
import com.leandrour.auth.presentation.forgot_password.ForgotPasswordViewModel
import com.leandrour.auth.presentation.login.LoginViewModel
import com.leandrour.auth.presentation.register.RegisterViewModel
import com.leandrour.auth.presentation.register_success.RegisterSuccessViewModel
import com.leandrour.auth.presentation.reset_password.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
}