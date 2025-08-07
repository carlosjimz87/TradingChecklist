package com.carlosjimz87.tradingchecklist

import android.os.Build
import androidx.compose.runtime.Composable
import org.koin.compose.koinInject
import java.util.Locale

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getCurrentLocale(): String {
    return Locale.getDefault().language // e.g., "es", "en", "de"
}

@Composable
actual inline fun <reified T : Any> inject(): T {
    return koinInject()
}