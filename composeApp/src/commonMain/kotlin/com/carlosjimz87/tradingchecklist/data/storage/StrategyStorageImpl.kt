package com.carlosjimz87.tradingchecklist.data.storage

import com.carlosjimz87.tradingchecklist.di.defaultStrategies
import com.carlosjimz87.tradingchecklist.domain.models.Strategy

class StrategyStorageImpl : StrategyStorage {

    private val savedStrategies: MutableMap<String, Strategy> = mutableMapOf()

    override fun saveStrategy(strategy: Strategy) {
        savedStrategies[strategy.id] = strategy
    }

    override fun getAllStrategies(): List<Strategy> {
        val defaults = defaultStrategies()

        return defaults.map { default ->
            savedStrategies[default.id] ?: default
        }
    }
}