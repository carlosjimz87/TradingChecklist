package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.storage.AndroidChecklistStorageImpl
import com.carlosjimz87.tradingchecklist.data.storage.StrategyStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single<StrategyStorage> { AndroidChecklistStorageImpl(context = androidContext()) }
}