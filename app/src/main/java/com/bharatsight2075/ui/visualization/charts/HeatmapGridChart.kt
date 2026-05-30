package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

/**
 * C15. HeatmapGridChart
 */
@Composable
fun HeatmapGridChart(
    data: List<List<Float>>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val matrix = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.HEATMAP) as List<List<Float>> }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "HeatmapAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors
    val coldColor = colors.primary.copy(alpha = 0.1f)
    val hotColor = colors.accent

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val rows = matrix.size.coerceAtLeast(1)
        val cols = matrix[0].size.coerceAtLeast(1)
        val cellW = size.width / cols
        val cellH = size.height / rows
        
        val maxVal = matrix.flatten().maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
        val minVal = matrix.flatten().minOrNull() ?: 0f

        matrix.forEachIndexed { r, row ->
            row.forEachIndexed { c, value ->
                val normalized = ((value - minVal) / (maxVal - minVal).coerceAtLeast(0.001f)) * currentProgress
                val cellColor = lerp(coldColor, hotColor, normalized)
                
                drawRoundRect(
                    color = cellColor,
                    topLeft = Offset(c * cellW + 1.dp.toPx(), r * cellH + 1.dp.toPx()),
                    size = Size(cellW - 2.dp.toPx(), cellH - 2.dp.toPx()),
                    cornerRadius = CornerRadius(4.dp.toPx())
                )
            }
        }
    }
}

private fun lerp(start: Color, stop: Color, fraction: Float): Color {
    return Color(
        red = androidx.compose.ui.util.lerp(start.red, stop.red, fraction),
        green = androidx.compose.ui.util.lerp(start.green, stop.green, fraction),
        blue = androidx.compose.ui.util.lerp(start.blue, stop.blue, fraction),
        alpha = androidx.compose.ui.util.lerp(start.alpha, stop.alpha, fraction)
    )
}
