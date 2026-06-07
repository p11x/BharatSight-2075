package com.bharatsight2075.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bharatsight2075.ui.visualization.charts.GlowingNumberTicker

data class HeroStat(val label: String, val value: String)

@Composable
fun HeroStatsRow(
    chartId: String,
    navController: NavController,
    stats: List<HeroStat>,
    description: String = ""
) {
    DashCard(
        chartId = chartId,
        navController = navController,
        title = "KEY SECTOR METRICS",
        description = description
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            stats.forEach { stat ->
                val label = stat.label
                val value = stat.value
                val numericValue = value.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f
                val unit = value.filter { !it.isDigit() && it != '.' }
                
                Column(modifier = Modifier.weight(1f)) {
                    GlowingNumberTicker(
                        value = numericValue,
                        unit = unit,
                        delta = "▲ 4.2%", // Mock delta
                        modifier = Modifier.height(60.dp),
                        chartHeight = 60.dp
                    )
                }
            }
        }
    }
}
