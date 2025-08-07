package com.carlosjimz87.tradingchecklist

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TradingChecklist",
    ) {
        ChecklistScreen()
    }
}