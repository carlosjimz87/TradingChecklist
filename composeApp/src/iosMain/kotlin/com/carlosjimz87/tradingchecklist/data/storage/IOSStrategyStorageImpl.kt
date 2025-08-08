package com.carlosjimz87.tradingchecklist.data.storage
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
class IOSStrategyStorageImpl : StrategyStorage {

    private fun getFilePath(strategyId: String): String {
        val directories = NSSearchPathForDirectoriesInDomains(
            directory = NSDocumentDirectory,
            domainMask = NSUserDomainMask,
            expandTilde = true
        )
        val documentsDirectory = directories.first() as? String
            ?: error("Cannot access documents dir")
        return "$documentsDirectory/${strategyId}_checklist.json"
    }


    override fun saveChecklist(
        strategyId: String,
        items: List<ChecklistItem>
    ) {
        val jsonString = Json.encodeToString(items)
        val nsString = jsonString as NSString
        nsString.writeToFile(
            path = getFilePath(strategyId),
            atomically = true,
            encoding = NSUTF8StringEncoding,
            error = null
        )
    }

    override fun getChecklist(strategyId: String): List<ChecklistItem>? {
        val path = getFilePath(strategyId)
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

    override fun getAllStrategies(): List<Strategy>? {
        val directories = NSSearchPathForDirectoriesInDomains(
            directory = NSDocumentDirectory,
            domainMask = NSUserDomainMask,
            expandTilde = true
        )
        val documentsDirectory = directories.first() as? String ?: return null

        val fileManager = NSFileManager.defaultManager
        val fileNames = fileManager.contentsOfDirectoryAtPath(documentsDirectory, null) ?: return null

        return fileNames.filterIsInstance<String>()
            .filter { it.endsWith("_checklist.json") }
            .mapNotNull { fileName ->
                val strategyId = fileName.removeSuffix("_checklist.json")
                getChecklist(strategyId)?.let { checklist ->
                    Strategy(id = strategyId, name = strategyId.replaceFirstChar { it.uppercase() }, checklist = checklist, description = "Checklist for $strategyId")
                }
            }
    }
}