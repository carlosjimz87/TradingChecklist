package com.carlosjimz87.tradingchecklist.data.storage
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
class IOSChecklistStorage : ChecklistStorage {

    private val fileName = "checklist.json"

    private fun getFilePath(): String {
        val directories = NSSearchPathForDirectoriesInDomains(
            directory = NSDocumentDirectory,
            domainMask = NSUserDomainMask,
            expandTilde = true
        )
        val documentsDirectory = directories.first() as? String ?: error("Cannot access documents dir")
        return "$documentsDirectory/$fileName"
    }

    override fun saveChecklist(items: List<ChecklistItem>) {
        val jsonString = Json.encodeToString(items)
        val nsString = jsonString as NSString
        nsString.writeToFile(
            path = getFilePath(),
            atomically = true,
            encoding = NSUTF8StringEncoding,
            error = null
        )
    }

    override fun getChecklist(): List<ChecklistItem>? {
        val path = getFilePath()
        val fileManager = NSFileManager.defaultManager

        return if (fileManager.fileExistsAtPath(path)) {
            try {
                val content = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)?.toString()
                Json.decodeFromString(content ?: return null)
            } catch (e: Exception) {
                null
            }
        } else null
    }
}