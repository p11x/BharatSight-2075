package com.bharatsight2075.ui.maps

import com.bharatsight2075.viewmodel.MapStyle
import kotlin.random.Random

object IndiaMapMockData {
    fun getValuesFor(style: MapStyle): Map<String, Float> {
        val stateIds = listOf("AN", "AP", "AR", "AS", "BR", "CH", "CT", "DD", "DL", "DN", "GA", "GJ", "HR", "HP", "JK", "JH", "KA", "KL", "LA", "LD", "MP", "MH", "MN", "ML", "MZ", "NL", "OR", "PY", "PB", "RJ", "SK", "TN", "TS", "TR", "UP", "UK", "WB")
        val random = Random(style.ordinal)
        return when (style) {
            MapStyle.GDP_CHOROPLETH -> mapOf(
                "MH" to 0.95f, "TN" to 0.85f, "GJ" to 0.80f, "UP" to 0.65f, "KA" to 0.75f, "BR" to 0.25f, "JH" to 0.30f
            ).withDefault { random.nextFloat() * 0.6f + 0.2f }
            MapStyle.GROWTH_PULSE -> mapOf(
                "UP" to 0.90f, "MP" to 0.85f, "TS" to 0.80f, "AP" to 0.75f, "TN" to 0.70f
            ).withDefault { random.nextFloat() * 0.5f + 0.3f }
            else -> stateIds.associateWith { random.nextFloat() }
        }
    }

    fun getTopStates(style: MapStyle): List<Triple<String, Float, Int>> {
        val values = getValuesFor(style)
        return values.entries
            .sortedByDescending { it.value }
            .take(5)
            .mapIndexed { i, entry -> Triple(entry.key, entry.value, i + 1) }
    }

    fun getDistrictsForState(state: String?, style: MapStyle): List<Pair<String, Float>> {
        if (state == null) return emptyList()
        val random = Random(state.hashCode() + style.ordinal)
        return List(8) { "District $it" to random.nextFloat() }
    }
}
