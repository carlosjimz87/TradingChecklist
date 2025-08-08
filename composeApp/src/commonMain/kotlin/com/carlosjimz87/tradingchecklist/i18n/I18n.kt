package com.carlosjimz87.tradingchecklist.i18n

import com.carlosjimz87.tradingchecklist.loadResource
import kotlinx.serialization.json.Json

object I18n {
    private var current: Strings = Strings()

    fun load(locale: String) {
        val fileName = when (locale) {
            "ar" -> "i18n/strings.ar.json"
            "de" -> "i18n/strings.de.json"
            "es" -> "i18n/strings.es.json"
            "fr" -> "i18n/strings.fr.json"
            "hi" -> "i18n/strings.hi.json"
            "ja" -> "i18n/strings.ja.json"
            "it" -> "i18n/strings.it.json"
            "pt" -> "i18n/strings.pt.json"
            "ru" -> "i18n/strings.ru.json"
            "zh" -> "i18n/strings.zh.json"
            else -> "i18n/strings.en.json"
        }

        val jsonString = loadResource(fileName)
        current = Json.decodeFromString(Strings.serializer(), jsonString)
    }

    val strings: Strings get() = current
}