package com.leandrour.core.data.di

import com.leandro.ur.core.domain.preferences.ThemePreferences
import com.leandrour.core.data.auth.createDataStore
import com.leandrour.core.data.preferences.DataStoreThemePreferencesImpl
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformCoreDataModule = module {
    single { createDataStore() }
    single<HttpClientEngine> { OkHttp.create() }
    singleOf(::DataStoreThemePreferencesImpl) bind ThemePreferences::class
}