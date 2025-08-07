package com.carlosjimz87.tradingchecklist

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
actual fun loadResource(fileName: String): String {
    val path = NSBundle.mainBundle.pathForResource(name = fileName, ofType = null)
        ?: error("File not found: $fileName")

    val contents = NSString.stringWithContentsOfFile(path, encoding = NSUTF8StringEncoding, error = null)
        ?: error("Unable to read file: $fileName")

    return contents as String
}