package com.carlosjimz87.tradingchecklist.data.repos

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem


class ChecklistRepositoryImpl : ChecklistRepository {
    override suspend fun getChecklist(): List<ChecklistItem> {
        return listOf(
            ChecklistItem("Am I focused?"),
            ChecklistItem("60m context clear?"),
            ChecklistItem("Zones marked?"),
            ChecklistItem("60m semaphore?"),
            ChecklistItem("1m semaphore?"),
            ChecklistItem("Big news?"),
            ChecklistItem("Risk combination worth it?"),
            ChecklistItem("Protected stop?"),
            ChecklistItem("Correct leverage?"),
            ChecklistItem("Entry zone clear?"),
            ChecklistItem("Trade management clear?"),
            ChecklistItem("No doubts?")
        )
    }
}