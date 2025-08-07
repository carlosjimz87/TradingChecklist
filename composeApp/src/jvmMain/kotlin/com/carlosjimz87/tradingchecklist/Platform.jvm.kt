package com.carlosjimz87.tradingchecklist

import androidx.compose.runtime.Composable
import org.koin.mp.KoinPlatform.getKoin
import java.util.Locale

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getCurrentLocale(): String {
    return Locale.getDefault().language
}

@Composable
actual inline fun <reified T : Any> inject(): T {
    return getKoin().get()
}