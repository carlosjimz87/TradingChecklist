package com.carlosjimz87.tradingchecklist

import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice
import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages
import org.koin.mp.KoinPlatform.getKoin

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getCurrentLocale(): String {
    val preferred = NSLocale.preferredLanguages.first() as? String ?: return "en"
    return preferred.substringBefore("-") // e.g., "es-ES" -> "es"
}

@Composable
actual inline fun <reified T : Any> inject(): T {
    return getKoin().get()
}