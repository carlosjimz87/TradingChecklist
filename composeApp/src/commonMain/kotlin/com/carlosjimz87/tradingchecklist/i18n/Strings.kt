package com.carlosjimz87.tradingchecklist.i18n

import kotlinx.serialization.Serializable

@Serializable
data class Strings(
    val checklist_title: String,
    val reset_button: String,
    val completed_message: String,
    val confirm_reset: String
)