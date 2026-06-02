package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

/**
 * C17. ScatterWithTrendLine
 */
@Composable
fun ScatterWithTrendLine(
    data: List<Offset>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.SCATTER_TREND) as List<Offset> }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "ScatterAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val primary = SciFiTheme.extendedColors.primary
    val accent = SciFiTheme.extendedColors.accent

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        if (safeData.isEmpty()) return@Canvas
        
        val xMin = safeData.minOf { it.x }; val xMax = safeData.maxOf { it.x }
        val yMin = safeData.minOf { it.y }; val yMax = safeData.maxOf { it.y }
        
        fun normalizeX(x: Float) = (x - xMin) / (xMax - xMin).coerceAtLeast(0.001f) * size.width
        fun normalizeY(y: Float) = size.height - (y - yMin) / (yMax - yMin).coerceAtLeast(0.001f) * size.height
        
        // Draw Dots
        safeData.forEach { point ->
            drawCircle(
                color = primary,
                radius = 4.dp.toPx() * currentProgress,
                center = Offset(normalizeX(point.x), normalizeY(point.y)),
                alpha = 0.6f
            )
        }
        
        // Linear Regression
        val n = safeData.size
        val sumX = safeData.sumOf { it.x.toDouble() }.toFloat()
        val sumY = safeData.sumOf { it.y.toDouble() }.toFloat()
        val sumXY = safeData.sumOf { (it.x * it.y).toDouble() }.toFloat()
        val sumX2 = safeData.sumOf { (it.x * it.x).toDouble() }.toFloat()
        
        val slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX).coerceAtLeast(0.001f)
        val intercept = (sumY - slope * sumX) / n
        
        val xStart = xMin
        val yStart = slope * xStart + intercept
        val xEnd = xMin + (xMax - xMin) * currentProgress
        val yEnd = slope * xEnd + intercept
        
        drawLine(
            color = accent,
            start = Offset(normalizeX(xStart), normalizeY(yStart)),
            end = Offset(normalizeX(xEnd), normalizeY(yEnd)),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}
