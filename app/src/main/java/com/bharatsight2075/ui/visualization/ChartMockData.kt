package com.bharatsight2075.ui.visualization

import androidx.compose.ui.geometry.Offset
import com.bharatsight2075.ui.visualization.charts.CandleData
import kotlin.random.Random

object ChartMockData {
    fun generateMockData(chartType: ChartType): List<Any> {
        return when (chartType) {
            ChartType.AREA -> listOf(30f, 45f, 38f, 62f, 55f, 78f, 70f, 85f, 92f, 88f, 95f, 100f)
            ChartType.BAR -> listOf(65f, 78f, 45f, 90f, 55f, 70f, 85f, 60f, 75f, 95f, 80f, 50f)
            ChartType.MULTI_LINE -> listOf(40f, 55f, 48f, 65f, 72f, 68f, 80f, 75f, 88f, 85f, 92f, 100f)
            ChartType.CANDLE -> List(12) {
                val open = Random.nextInt(40, 80).toFloat()
                val high = open + Random.nextInt(5, 15)
                val low = open - Random.nextInt(5, 15)
                val close = Random.nextDouble(low.toDouble(), high.toDouble()).toFloat()
                CandleData(open, high, low, close)
            }
            ChartType.SCATTER_TREND -> List(20) {
                Offset(Random.nextFloat(), Random.nextFloat())
            }
            ChartType.RADAR -> listOf(0.7f, 0.6f, 0.8f, 0.5f, 0.9f, 0.65f, 0.75f, 0.55f)
            ChartType.BUBBLE -> List(12) {
                Triple(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 0.15f + 0.05f)
            }
            ChartType.HEATMAP -> List(6) { List(6) { Random.nextFloat() } }
            ChartType.TREEMAP -> listOf(100.0, 80.0, 60.0, 40.0, 20.0, 20.0, 10.0)
            ChartType.WATERFALL -> listOf(40f, 20f, -30f, 50f, -10f, 25f)
            else -> listOf(50f, 60f, 70f, 80f)
        }
    }
}
