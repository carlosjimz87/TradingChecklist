package com.carlosjimz87.tradingchecklist

import platform.UIKit.UIDevice
import platform.Foundation.NSLocale
import platform.Foundation.preferredLanguages

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getCurrentLocale(): String {
    val preferred = NSLocale.preferredLanguages.first() as? String ?: return "en"
    return preferred.substringBefore("-") // e.g., "es-ES" -> "es"
}