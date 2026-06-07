package com.bharatsight2075.ui.visualization.radar

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import kotlin.math.*

data class RadarDataSet(
    val label: String,
    val values: List<Float>,
    val maxValues: List<Float> = emptyList(),
    val color: Color
)

@Composable
fun RadarSpiderChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    labels: List<String> = emptyList(),
    dataSets: List<RadarDataSet> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = Color(0xFF00F5FF)
) {
    val axisCount = if (labels.isNotEmpty()) labels.size else if (dataSets.isNotEmpty()) dataSets.first().values.size else 5
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutBack),
        label = "SpiderAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val radius = size.minDimension / 2 * 0.8f
        
        // Circular web
        repeat(5) { rIdx ->
            val r = radius * (rIdx + 1) / 5
            drawCircle(primaryColor.copy(alpha = 0.1f), r, center, style = Stroke(1.dp.toPx()))
        }
        
        // Spider Axes
        for (i in 0 until axisCount) {
            val angle = -PI / 2 + i * (2 * PI / axisCount)
            val x = center.x + radius * cos(angle).toFloat()
            val y = center.y + radius * sin(angle).toFloat()
            drawLine(primaryColor.copy(alpha = 0.2f), center, Offset(x, y), strokeWidth = 1.dp.toPx())
        }
        
        // DataSets
        if (dataSets.isNotEmpty()) {
            dataSets.forEach { set ->
                val path = Path()
                set.values.forEachIndexed { i, value ->
                    val maxVal = set.maxValues.getOrNull(i) ?: 1f
                    val r = radius * (value / maxVal) * progress
                    val angle = -PI / 2 + i * (2 * PI / axisCount)
                    val x = center.x + r * cos(angle).toFloat()
                    val y = center.y + r * sin(angle).toFloat()
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                path.close()
                drawPath(path, set.color.copy(alpha = 0.3f))
                drawPath(path, set.color, style = Stroke(width = 2.dp.toPx()))
            }
        } else {
            val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.RADAR)
            val path = Path()
            safeData.take(axisCount).forEachIndexed { i, value ->
                val r = radius * value * progress
                val angle = -PI / 2 + i * (2 * PI / axisCount)
                val x = center.x + r * cos(angle).toFloat()
                val y = center.y + r * sin(angle).toFloat()
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
            drawPath(path, primaryColor.copy(alpha = 0.3f))
            drawPath(path, primaryColor, style = Stroke(width = 2.dp.toPx()))
        }
    }
}
