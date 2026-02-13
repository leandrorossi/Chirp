package com.leandrour.core.data.di

import com.leandrour.core.data.auth.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformCoreDataModule = module {
    single { createDataStore() }
    single<HttpClientEngine> { OkHttp.create() }
}