package com.bharatsight2075.ui.visualization.bar

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
import com.bharatsight2075.ui.visualization.isometric.IsometricUtils

data class BarData(val label: String, val value: Float, val max: Float, val color: Color? = null)

@Composable
fun IsometricBarChart(
    modifier: Modifier = Modifier,
    bars: List<BarData> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 160.dp,
    primaryColor: Color = Color(0xFF00F5FF)
) {
    @Suppress("UNCHECKED_CAST")
    val safeBars = bars.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).map { BarData("M", it, 100f) }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1500, easing = EaseOutCubic),
        label = "IsoBarAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val count = safeBars.size
        val barWidth = (size.width / (count * 2)).coerceAtLeast(10.dp.toPx())
        val spacing = barWidth
        
        safeBars.forEachIndexed { i, bar ->
            val h = (bar.value / bar.max) * size.height * 0.7f * progress
            val x = i * (barWidth + spacing) + spacing
            val y = size.height - 20.dp.toPx()
            
            val color = bar.color ?: primaryColor
            
            // Isometric faces
            with(IsometricUtils) {
                drawIsometricCube(
                    x = x,
                    y = y,
                    z = 0f,
                    width = barWidth,
                    height = h,
                    depth = barWidth,
                    color = color
                )
            }
        }
    }
}
