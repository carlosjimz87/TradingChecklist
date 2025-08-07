package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.storage.AndroidChecklistStorage
import com.carlosjimz87.tradingchecklist.data.storage.ChecklistStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single<ChecklistStorage> { AndroidChecklistStorage(context = androidContext()) }
}