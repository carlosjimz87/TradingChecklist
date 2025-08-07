package com.carlosjimz87.tradingchecklist.data.storage
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import kotlinx.serialization.json.Json
import java.io.File

class DesktopChecklistStorageImpl : ChecklistStorage {

    private val file = File(System.getProperty("user.home"), "checklist.json")

    override fun saveChecklist(items: List<ChecklistItem>) {
        file.writeText(Json.encodeToString(items))
    }

    override fun getChecklist(): List<ChecklistItem>? {
        return if (file.exists()) {
            try {
                Json.decodeFromString(file.readText())
            } catch (e: Exception) {
                null
            }
        } else null
    }
}