package com.carlosjimz87.tradingchecklist.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Strategy(
    val id: String,
    val name: String,
    val color : String = "",
    val description: String,
    val checklist: List<ChecklistItem>
)