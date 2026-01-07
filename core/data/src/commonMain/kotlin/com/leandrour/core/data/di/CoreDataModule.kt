package com.leandrour.core.data.di

import com.leandrour.core.data.auth.DataStoreSessionStorageImpl
import com.leandrour.core.data.auth.KtorAuthServiceImpl
import com.leandrour.core.data.logging.KermitLogger
import com.leandrour.core.data.networking.HttpClientFactory
import com.leandrour.core.domain.auth.AuthService
import com.leandrour.core.domain.auth.SessionStorage
import com.leandrour.core.domain.logging.ChirpLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    single<ChirpLogger> { KermitLogger }

    single {
        HttpClientFactory(get(), get()).create(get())
    }

    singleOf(::KtorAuthServiceImpl) bind AuthService::class
    singleOf(::DataStoreSessionStorageImpl) bind SessionStorage::class
}