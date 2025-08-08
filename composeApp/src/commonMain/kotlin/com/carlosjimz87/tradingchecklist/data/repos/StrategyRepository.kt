package com.carlosjimz87.tradingchecklist.data.repos

import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy

interface StrategyRepository {
    suspend fun getStrategies(): List<Strategy>
    suspend fun saveStrategyChecklist(strategyId: String, items: List<ChecklistItem>)
    suspend fun resetStrategyChecklist(strategyId: String)
}