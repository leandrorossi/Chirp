package com.leandrour.chirp.di

import com.leandrour.auth.presentation.di.authPresentationModule
import com.leandrour.core.data.di.coreDataModule
import com.leandrour.core.presentation.di.corePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appModule,
            corePresentationModule
        )
    }
}