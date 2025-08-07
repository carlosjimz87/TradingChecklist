package com.carlosjimz87.tradingchecklist

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.carlosjimz87.tradingchecklist.di.initKoin
import com.carlosjimz87.tradingchecklist.i18n.I18n

fun main() = application {
    initKoin()

    I18n.load(getCurrentLocale())

    Window(
        onCloseRequest = ::exitApplication,
        title = "TradingChecklist",
        state = rememberWindowState(
            width = 1000.dp,
            height = 700.dp
        )
    ) {
        ChecklistScreen()
    }
}