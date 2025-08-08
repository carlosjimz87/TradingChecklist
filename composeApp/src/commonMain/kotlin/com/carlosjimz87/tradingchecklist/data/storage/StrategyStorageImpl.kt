package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy

class StrategyStorageImpl : StrategyStorage {
    private val savedStrategies: MutableMap<String, List<ChecklistItem>> = mutableMapOf()

    override fun saveChecklist(strategyId: String, items: List<ChecklistItem>) {
        savedStrategies[strategyId] = items
    }

    override fun getChecklist(strategyId: String): List<ChecklistItem>? {
        return savedStrategies[strategyId]
    }

    override fun getAllStrategies(): List<Strategy>? {
        if (savedStrategies.isEmpty()) return null

        return savedStrategies.map { (id, checklist) ->
            Strategy(
                id = id,
                name = id.replaceFirstChar { it.uppercase() },
                description = "Checklist for $id",
                checklist = checklist
            )
        }
    }
}