package com.leandrour.auth.presentation.di

import com.leandrour.auth.presentation.register.RegisterViewModel
import com.leandrour.auth.presentation.register_success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
}