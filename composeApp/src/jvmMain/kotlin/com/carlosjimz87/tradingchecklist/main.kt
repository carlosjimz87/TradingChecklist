package com.carlosjimz87.tradingchecklist

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
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
        title = "Trading Checklist",
        icon = BitmapPainter(useResource("icon.png", ::loadImageBitmap)),
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            width = 1000.dp,
            height = 800.dp
        )
    ) {
        ChecklistScreen()
    }
}