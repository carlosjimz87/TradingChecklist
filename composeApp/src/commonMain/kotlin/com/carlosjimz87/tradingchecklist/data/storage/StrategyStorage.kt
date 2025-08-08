package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy

interface StrategyStorage {
    fun saveChecklist(strategyId: String, items: List<ChecklistItem>)
    fun getChecklist(strategyId: String): List<ChecklistItem>?
    fun getAllStrategies(): List<Strategy>?
}