package com.carlosjimz87.tradingchecklist.data.storage

import android.content.Context
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import kotlinx.serialization.json.Json
import java.io.File

class AndroidChecklistStorageImpl(private val context: Context) : StrategyStorage {

    private fun getFile(strategyId: String): File {
        return context.getFileStreamPath("${strategyId}_checklist.json")
    }

    override fun saveChecklist(strategyId: String, items: List<ChecklistItem>) {
        val file = getFile(strategyId)
        file.writeText(Json.encodeToString(items))
    }

    override fun getChecklist(strategyId: String): List<ChecklistItem>? {
        val file = getFile(strategyId)
        return if (file.exists()) {
            try {
                val content = file.readText()
                Json.decodeFromString(content)
            } catch (e: Exception) {
                null
            }
        } else null
    }

    override fun getAllStrategies(): List<Strategy>? {
        val files = context.filesDir?.listFiles()
            ?.filter { it.name.endsWith("_checklist.json") }
            ?: return null

        return files.mapNotNull { file ->
            val strategyId = file.name.removeSuffix("_checklist.json")
            val checklist = getChecklist(strategyId)
            checklist?.let {
                Strategy(id = strategyId, name = strategyId, checklist = it, description = "Checklist for $strategyId")
            }
        }
    }
}