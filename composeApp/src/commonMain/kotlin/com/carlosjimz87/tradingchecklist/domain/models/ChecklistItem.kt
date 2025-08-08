package com.carlosjimz87.tradingchecklist.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ChecklistItem(
    val id: String,
    val title: String,
    val checked: Boolean = false
)