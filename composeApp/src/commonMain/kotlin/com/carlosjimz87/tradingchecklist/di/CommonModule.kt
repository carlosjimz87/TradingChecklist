package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.repos.ChecklistRepository
import com.carlosjimz87.tradingchecklist.data.repos.ChecklistRepositoryImpl
import com.carlosjimz87.tradingchecklist.data.storage.ChecklistStorage
import com.carlosjimz87.tradingchecklist.data.storage.ChecklistStorageImpl
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import org.koin.dsl.module

fun commonModule() = module {
    single<ChecklistStorage> { ChecklistStorageImpl() }
    single<ChecklistRepository> { ChecklistRepositoryImpl(get()) }
}

fun defaultChecklist(): List<ChecklistItem> = listOf(
    ChecklistItem("Am I focused?"),
    ChecklistItem("60m context clear?"),
    ChecklistItem("Zones marked?"),
    ChecklistItem("60m semaphore?"),
    ChecklistItem("1m semaphore?"),
    ChecklistItem("Big news?"),
    ChecklistItem("Risk combination worth it?"),
    ChecklistItem("Protected stop?"),
    ChecklistItem("Correct leverage?"),
    ChecklistItem("Entry zone clear?"),
    ChecklistItem("Trade management clear?"),
    ChecklistItem("No doubts?")
)
