package com.carlosjimz87.tradingchecklist.data.repos

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

interface ChecklistRepository {
    suspend fun getChecklist(): List<ChecklistItem>
    suspend fun saveChecklist(items: List<ChecklistItem>)
}