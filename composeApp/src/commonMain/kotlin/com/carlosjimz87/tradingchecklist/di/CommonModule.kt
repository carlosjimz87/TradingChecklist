package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.storage.ChecklistStorage
import com.carlosjimz87.tradingchecklist.data.storage.ChecklistStorageImpl
import org.koin.dsl.module

fun commonModule() = module {
    single<ChecklistStorage> { ChecklistStorageImpl() }
}