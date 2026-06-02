package com.bharatsight2075.ml

import com.bharatsight2075.data.local.StateEconomyEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class StateForecastEngine @Inject constructor() {

    fun generateStateForecast(state: StateEconomyEntity, yearsAhead: Int = 10): FloatArray {
        // Use state-specific seed to ensure unique forecasts per state
        val seed = state.stateId.hashCode().toLong()
        val random = Random(seed)
        
        val forecast = FloatArray(yearsAhead)
        var currentGsdp = state.gsdp.toFloat()
        
        for (i in 0 until yearsAhead) {
            // Apply state-specific growth with some noise
            val annualGrowth = (state.growthRate.toFloat() / 100f) + (random.nextFloat() * 0.02f - 0.01f)
            currentGsdp *= (1 + annualGrowth)
            forecast[i] = currentGsdp
        }
        
        return forecast
    }
}
