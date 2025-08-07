package com.carlosjimz87.tradingchecklist.i18n

import com.carlosjimz87.tradingchecklist.loadResource
import kotlinx.serialization.json.Json

object I18n {
    private var current: Strings = Strings("?", "?", "?", "?")

    fun load(locale: String) {
        val fileName = when (locale) {
            "es" -> "i18n/strings.es.json"
            "de" -> "i18n/strings.de.json"
            else -> "i18n/strings.en.json"
        }

        val jsonString = loadResource(fileName)
        current = Json.decodeFromString(Strings.serializer(), jsonString)
    }

    val strings: Strings get() = current
}