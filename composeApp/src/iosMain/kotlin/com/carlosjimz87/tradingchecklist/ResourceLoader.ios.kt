package com.carlosjimz87.tradingchecklist

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
actual fun loadResource(fileName: String): String {
    val sanitized = fileName.substringAfterLast("/")         // e.g. strings.en.json
    val ext = sanitized.substringAfterLast(".", missingDelimiterValue = "")
    val name = sanitized.removeSuffix(".$ext")               // e.g. strings.en

    val path = NSBundle.mainBundle.pathForResource(name = name, ofType = ext)
        ?: error("File not found: $fileName")

    val contents = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null)
        ?: error("Unable to read file: $fileName")

    return contents as String
}