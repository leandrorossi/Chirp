package com.leandrour.chat.data.di

import com.leandrour.chat.data.lifecycle.AppLifecycleObserver
import com.leandrour.chat.data.network.ConnectionErrorHandler
import com.leandrour.chat.data.network.ConnectivityObserver
import com.leandrour.chat.data.notification.FirebasePushNotificationService
import com.leandrour.chat.database.DatabaseFactory
import com.leandrour.chat.domain.notification.PushNotificationService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformChatDataModule = module {
    singleOf(::DatabaseFactory)
    singleOf(::ConnectionErrorHandler)
    singleOf(::ConnectivityObserver)
    singleOf(::AppLifecycleObserver)
    singleOf(::FirebasePushNotificationService) bind PushNotificationService::class
}