package com.carlosjimz87.tradingchecklist

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getCurrentLocale(): String

@Composable
expect inline fun <reified T : Any> inject(): T