package com.leandrour.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.leandrour.chat.data.chat.KtorChatParticipantServiceImpl
import com.leandrour.chat.data.chat.KtorChatServiceImpl
import com.leandrour.chat.data.chat.OfflineFirstChatRepositoryImpl
import com.leandrour.chat.database.DatabaseFactory
import com.leandrour.chat.domain.chat.ChatParticipantService
import com.leandrour.chat.domain.chat.ChatRepository
import com.leandrour.chat.domain.chat.ChatService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformChatDataModule: Module

val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantServiceImpl) bind ChatParticipantService::class
    singleOf(::KtorChatServiceImpl) bind ChatService::class
    singleOf(::OfflineFirstChatRepositoryImpl) bind ChatRepository::class
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}