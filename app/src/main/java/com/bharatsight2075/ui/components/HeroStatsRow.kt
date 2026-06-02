package com.bharatsight2075.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bharatsight2075.ui.visualization.charts.GlowingNumberTicker

@Composable
fun HeroStatsRow(
    chartId: String,
    navController: NavController,
    stats: List<Pair<String, String>>
) {
    DashCard(
        chartId = chartId,
        navController = navController,
        title = "KEY SECTOR METRICS"
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            stats.forEach { (label, value) ->
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
