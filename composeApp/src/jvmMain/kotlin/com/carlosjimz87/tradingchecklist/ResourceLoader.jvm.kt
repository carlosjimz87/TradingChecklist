package com.carlosjimz87.tradingchecklist

import java.nio.charset.StandardCharsets

actual fun loadResource(fileName: String): String {
    val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
        ?: error("File not found: $fileName")
    return inputStream.bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
}