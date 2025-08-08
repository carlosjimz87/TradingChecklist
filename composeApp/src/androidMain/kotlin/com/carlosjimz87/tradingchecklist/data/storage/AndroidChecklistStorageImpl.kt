package com.carlosjimz87.tradingchecklist.data.storage

import android.content.Context
import com.carlosjimz87.tradingchecklist.di.defaultStrategies
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import kotlinx.serialization.json.Json
import java.io.File

class AndroidChecklistStorageImpl(private val context: Context) : StrategyStorage {

    private fun getFile(strategyId: String): File {
        val folder = File(context.filesDir, "checklists")
        if (!folder.exists()) folder.mkdirs()
        return File(folder, "${strategyId}_checklist.json")
    }

    override fun saveStrategy(strategy: Strategy) {
        val file = getFile(strategy.id)
        file.writeText(Json.encodeToString(strategy))
    }

    override fun getAllStrategies(): List<Strategy> {
        val files = File(context.filesDir, "checklists")
            .takeIf { it.exists() }
            ?.listFiles { _, name -> name.endsWith("_checklist.json") }
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