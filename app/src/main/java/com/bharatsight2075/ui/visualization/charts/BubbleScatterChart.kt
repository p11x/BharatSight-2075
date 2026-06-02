package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.GradientFills

data class BubblePoint(val x: Float, val y: Float, val r: Float, val color: Color = Color.Cyan)

/**
 * C08. BubbleScatterChart
 */
@Composable
fun BubbleScatterChart(
    data: List<BubblePoint>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.BUBBLE).map { it as Triple<Float, Float, Float> }.map { BubblePoint(it.first, it.second, it.third) } }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "BubbleAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        if (safeData.isEmpty()) return@Canvas
        
        val xMin = safeData.minOf { it.x }; val xMax = safeData.maxOf { it.x }
        val yMin = safeData.minOf { it.y }; val yMax = safeData.maxOf { it.y }
        val maxR = safeData.maxOf { it.r }.coerceAtLeast(0.001f)
        
        safeData.forEach { point ->
            val normX = (point.x - xMin) / (xMax - xMin).coerceAtLeast(0.001f) * size.width
            val normY = size.height - (point.y - yMin) / (yMax - yMin).coerceAtLeast(0.001f) * size.height
            val radius = (point.r / maxR) * 30.dp.toPx() * currentProgress
            val center = Offset(normX, normY)
            
            if (radius > 0.01f) {
                // Glow
                drawCircle(point.color.copy(alpha = 0.2f), radius * 1.6f, center)
                
                // Fill
                val bubbleBrush = GradientFills.bubbleFill(point.color, center, radius)
                drawCircle(bubbleBrush, radius, center)
            }
        }
    }
}
