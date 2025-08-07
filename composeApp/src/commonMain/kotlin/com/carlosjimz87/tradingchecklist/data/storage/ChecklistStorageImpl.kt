package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem

class ChecklistStorageImpl : ChecklistStorage {
    private var savedItems: List<ChecklistItem> = emptyList()

    override fun saveChecklist(items: List<ChecklistItem>) {
        savedItems = items
    }

    override fun getChecklist(): List<ChecklistItem> = savedItems
}