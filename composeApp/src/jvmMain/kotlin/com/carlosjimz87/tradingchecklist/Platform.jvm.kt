package com.carlosjimz87.tradingchecklist

import java.util.Locale

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getCurrentLocale(): String {
    return Locale.getDefault().language
}