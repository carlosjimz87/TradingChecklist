package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

interface ChecklistStorage {
    fun saveChecklist(items: List<ChecklistItem>)
    fun getChecklist(): List<ChecklistItem>?
}