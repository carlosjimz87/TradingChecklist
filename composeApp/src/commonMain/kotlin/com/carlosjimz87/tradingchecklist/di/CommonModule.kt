package com.carlosjimz87.tradingchecklist.di

import com.carlosjimz87.tradingchecklist.data.repos.StrategyRepository
import com.carlosjimz87.tradingchecklist.data.repos.StrategyRepositoryImpl
import com.carlosjimz87.tradingchecklist.data.storage.StrategyStorage
import com.carlosjimz87.tradingchecklist.data.storage.StrategyStorageImpl
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import org.koin.dsl.module

fun commonModule() = module {
    single<StrategyStorage> { StrategyStorageImpl() }
    single<StrategyRepository> { StrategyRepositoryImpl(get()) }
}

fun defaultStrategies(): List<Strategy> = listOf(
    Strategy(
        id = "01",
        color = "#FFD700", // Gold color
        name = "GOLD Strategy Checklist",
        description = "Gold trading strategy checklist",
        checklist = listOf(
            ChecklistItem("01", "Am I focused?"),
            ChecklistItem("02", "60m context clear?"),
            ChecklistItem("03", "Zones marked?"),
            ChecklistItem("04", "60m semaphore?"),
            ChecklistItem("05", "1m semaphore?"),
            ChecklistItem("06", "Big news?"),
            ChecklistItem("07", "Risk combination worth it?"),
            ChecklistItem("08", "Protected stop?"),
            ChecklistItem("09", "Correct leverage?"),
            ChecklistItem("10", "Entry zone clear?"),
            ChecklistItem("11", "Trade management clear?"),
            ChecklistItem("12", "No doubts?")
        )
    ),Strategy(
        id = "02",
        color = "#FF4500", // OrangeRed color
        name = "GIRO Strategy Checklist",
        description = "Giro trading strategy checklist",
        checklist = listOf(
            ChecklistItem("01", "Am I focused?"),
            ChecklistItem("02", "60m context clear?"),
            ChecklistItem("03", "Zones marked?"),
            ChecklistItem("04", "60m semaphore?"),
        )
    ),
)
