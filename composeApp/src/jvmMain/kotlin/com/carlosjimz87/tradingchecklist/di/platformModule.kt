package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.storage.ChecklistStorage
import com.carlosjimz87.tradingchecklist.data.storage.DesktopChecklistStorageImpl
import org.koin.dsl.module

actual fun platformModule() = module {
    single<ChecklistStorage> { DesktopChecklistStorageImpl() }
}