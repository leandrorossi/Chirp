package com.leandrour.chat.data.di

import com.leandrour.chat.data.chat.KtorChatParticipantServiceImpl
import com.leandrour.chat.domain.chat.ChatParticipantService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf(::KtorChatParticipantServiceImpl) bind ChatParticipantService::class
}