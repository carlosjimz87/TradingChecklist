package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.storage.IOSStrategyStorageImpl
import com.carlosjimz87.tradingchecklist.data.storage.StrategyStorage
import org.koin.dsl.module

actual fun platformModule() = module {
    single<StrategyStorage> { IOSStrategyStorageImpl() }
}