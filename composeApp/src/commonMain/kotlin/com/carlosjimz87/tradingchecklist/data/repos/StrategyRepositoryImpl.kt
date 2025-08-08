package com.carlosjimz87.tradingchecklist.data.repos

import com.carlosjimz87.tradingchecklist.data.storage.StrategyStorage
import com.carlosjimz87.tradingchecklist.di.defaultStrategies
import com.carlosjimz87.tradingchecklist.domain.models.ChecklistItem
import com.carlosjimz87.tradingchecklist.domain.models.Strategy


class StrategyRepositoryImpl(
    private val storage: StrategyStorage
) : StrategyRepository {

    override suspend fun getStrategies(): List<Strategy> {
        val repoStrategies = storage.getAllStrategies()
        return if (repoStrategies != null && repoStrategies.isNotEmpty()) {
            repoStrategies
        } else {
            defaultStrategies()
        }
    }

    override suspend fun saveStrategyChecklist(strategyId: String, items: List<ChecklistItem>) {
        storage.saveChecklist(strategyId, items)
    }

    override suspend fun resetStrategyChecklist(strategyId: String) {
        val default = defaultStrategies().find { it.id == strategyId }?.checklist.orEmpty()
        storage.saveChecklist(strategyId, default)
    }
}