package com.carlosjimz87.tradingchecklist.data.repos

import com.carlosjimz87.tradingchecklist.data.storage.ChecklistStorage
import com.carlosjimz87.tradingchecklist.di.defaultChecklist
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem


class ChecklistRepositoryImpl(
    private val storage: ChecklistStorage
) : ChecklistRepository {

    override suspend fun getChecklist(): List<ChecklistItem> {
        return storage.getChecklist() ?: defaultChecklist()
    }

    override suspend fun saveChecklist(items: List<ChecklistItem>) {
        storage.saveChecklist(items)
    }

    suspend fun resetChecklist() {
        storage.saveChecklist(defaultChecklist())
    }
}