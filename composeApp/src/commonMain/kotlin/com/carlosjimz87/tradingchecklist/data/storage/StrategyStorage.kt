package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.domain.models.Strategy

interface StrategyStorage {
    fun saveStrategy(strategy: Strategy)
    fun getAllStrategies(): List<Strategy>
}