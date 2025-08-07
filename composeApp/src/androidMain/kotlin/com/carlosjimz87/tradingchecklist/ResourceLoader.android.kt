package com.carlosjimz87.tradingchecklist

import android.content.Context

lateinit var androidAppContext: Context

fun initAndroidContext(context: Context) {
    androidAppContext = context
}

actual fun loadResource(fileName: String): String {
    val inputStream = androidAppContext.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}