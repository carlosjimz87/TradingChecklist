package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.storage.DesktopChecklistStorageImpl
import com.carlosjimz87.tradingchecklist.data.storage.StrategyStorage
import org.koin.dsl.module

actual fun provideStrategyStorage() = module {
    single<StrategyStorage> { DesktopChecklistStorageImpl() }
}