package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
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

    override fun saveChecklist(strategyId: String, items: List<ChecklistItem>) {
        fileFor(strategyId).writeText(Json.encodeToString(items))
    }

    override fun getChecklist(strategyId: String): List<ChecklistItem>? {
        val file = fileFor(strategyId)
        return if (file.exists()) {
            try {
                Json.decodeFromString(file.readText())
            } catch (e: Exception) {
                null
            }
        } else null
    }

    override fun getAllStrategies(): List<Strategy>? {
        return baseDir
            .listFiles { _, name -> name.endsWith("_checklist.json") }
            ?.mapNotNull { file ->
                val strategyId = file.name.removeSuffix("_checklist.json")
                getChecklist(strategyId)?.let { checklist ->
                    Strategy(id = strategyId, name = strategyId, checklist = checklist, description = "Checklist for $strategyId")
                }
            }
    }
}