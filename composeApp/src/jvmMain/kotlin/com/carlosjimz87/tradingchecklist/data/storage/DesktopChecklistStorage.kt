package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.di.defaultStrategies
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import kotlinx.serialization.json.Json
import java.io.File

class DesktopChecklistStorageImpl : StrategyStorage {

    private val baseDir = File(System.getProperty("user.home"), "trading_checklists")

    init {
        if (!baseDir.exists()) baseDir.mkdirs()
    }

    private fun fileFor(strategyId: String): File {
        return File(baseDir, "${strategyId}_checklist.json")
    }

    override fun saveStrategy(strategy: Strategy) {
        fileFor(strategy.id).writeText(Json.encodeToString(strategy))
    }

    override fun getAllStrategies(): List<Strategy> {
        val files = baseDir
            .listFiles { _, name -> name.endsWith("_checklist.json") }
            ?: return defaultStrategies()

        val saved = files.mapNotNull { file ->
            try {
                Json.decodeFromString<Strategy>(file.readText())
            } catch (e: Exception) {
                null
            }
        }

        val defaults = defaultStrategies()

        return defaults.map { default ->
            saved.find { it.id == default.id } ?: default
        }
    }
}