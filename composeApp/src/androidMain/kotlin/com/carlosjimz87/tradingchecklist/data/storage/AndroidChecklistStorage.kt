package com.carlosjimz87.tradingchecklist.data.storage

import android.content.Context
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import kotlinx.serialization.json.Json

class AndroidChecklistStorage(private val context: Context) : ChecklistStorage {

    private val fileName = "checklist.json"

    override fun saveChecklist(items: List<ChecklistItem>) {
        val file = context.getFileStreamPath(fileName)
        file.writeText(Json.encodeToString(items))
    }

    override fun getChecklist(): List<ChecklistItem>? {
        val file = context.getFileStreamPath(fileName)
        return if (file.exists()) {
            try {
                val content = file.readText()
                Json.decodeFromString(content)
            } catch (e: Exception) {
                null
            }
        } else null
    }
}