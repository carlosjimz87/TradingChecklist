package com.carlosjimz87.tradingchecklist.data.repos

import com.carlosjimz87.tradingchecklist.domain.models.Strategy

interface StrategyRepository {
    suspend fun getStrategies(): List<Strategy>
    suspend fun saveStrategy(strategy: Strategy)
    suspend fun resetStrategy(strategyId: String)
}