package com.carlosjimz87.tradingchecklist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getCurrentLocale(): String