package com.leandrour.chat.data.di

import com.leandrour.chat.data.lifecycle.AppLifecycleObserver
import com.leandrour.chat.database.DatabaseFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformChatDataModule = module {
    single { DatabaseFactory() }
    singleOf(::AppLifecycleObserver)
}