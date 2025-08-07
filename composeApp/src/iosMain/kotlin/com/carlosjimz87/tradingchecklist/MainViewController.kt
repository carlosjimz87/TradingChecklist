package com.carlosjimz87.tradingchecklist

import androidx.compose.ui.window.ComposeUIViewController
import com.carlosjimz87.tradingchecklist.i18n.I18n

fun MainViewController() = ComposeUIViewController {
    I18n.load(getCurrentLocale())
    ChecklistScreen()
}