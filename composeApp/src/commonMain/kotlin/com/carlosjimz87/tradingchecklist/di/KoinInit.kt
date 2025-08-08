package com.carlosjimz87.tradingchecklist.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

@Suppress("unused")
public fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        commonModule(provideStrategyStorage()),
    )
}

@Suppress("unused")
fun doInitKoin() = initKoin()