package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.di.defaultStrategies
import com.carlosjimz87.tradingchecklist.domain.models.Strategy
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSUserDomainMask
import platform.Foundation.stringWithContentsOfFile
import platform.Foundation.writeToFile

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

    override fun saveStrategy(strategy: Strategy) {
        val jsonString = Json.encodeToString(strategy)
        val nsString = jsonString as NSString
        nsString.writeToFile(
            path = getFilePath(strategy.id),
            atomically = true,
            encoding = NSUTF8StringEncoding,
            error = null
        )
    }

    override fun getAllStrategies(): List<Strategy> {
        val directories =
            NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
        val documentsDirectory = directories.first() as? String ?: return defaultStrategies()

        val fileManager = NSFileManager.defaultManager
        val fileNames = fileManager.contentsOfDirectoryAtPath(documentsDirectory, null)
            ?: return defaultStrategies()

        val saved = fileNames.filterIsInstance<String>()
            .filter { it.endsWith("_checklist.json") }
            .mapNotNull { fileName ->
                val path = "$documentsDirectory/$fileName"
                try {
                    val jsonString =
                        NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
                            ?.toString()
                    Json.decodeFromString<Strategy>(jsonString ?: return@mapNotNull null)
                } catch (_: Exception) {
                    null
                }
            }

        val defaults = defaultStrategies()

        return defaults.map { default ->
            saved.find { it.id == default.id } ?: default
        }
    }
}