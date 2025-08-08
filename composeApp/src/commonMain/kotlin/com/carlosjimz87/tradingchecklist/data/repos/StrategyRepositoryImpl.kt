package com.carlosjimz87.tradingchecklist.data.repos

import com.carlosjimz87.tradingchecklist.data.storage.StrategyStorage
import com.carlosjimz87.tradingchecklist.di.defaultStrategies
import com.carlosjimz87.tradingchecklist.domain.models.Strategy

class StrategyRepositoryImpl(
    private val storage: StrategyStorage
) : StrategyRepository {

    override suspend fun getStrategies(): List<Strategy> {
        return storage.getAllStrategies()
    }

    override suspend fun saveStrategy(strategy: Strategy) {
        storage.saveStrategy(strategy)
    }

    override suspend fun resetStrategy(strategyId: String) {
        val default = defaultStrategies().find { it.id == strategyId } ?: return
        storage.saveStrategy(default)
    }
}