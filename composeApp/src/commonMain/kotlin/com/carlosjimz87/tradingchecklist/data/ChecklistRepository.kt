package com.carlosjimz87.tradingchecklist.data

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

interface ChecklistRepository {
    suspend fun getChecklist(): List<ChecklistItem>
}