package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.GradientFills

/**
 * C19. WaterfallBarChart
 */
@Composable
fun WaterfallBarChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.WATERFALL).filterIsInstance<Float>() }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "WaterfallAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val count = safeData.size.coerceAtLeast(1)
        val totalGap = size.width * 0.3f
        val barWidth = (size.width - totalGap) / count
        val gap = totalGap / (count + 1)
        
        // Calculate running totals
        val runningTotals = FloatArray(count + 1)
        for (i in 0 until count) {
            runningTotals[i + 1] = runningTotals[i] + safeData[i]
        }
        
        val maxVal = runningTotals.maxOf { Math.abs(it) }.coerceAtLeast(0.001f)
        val centerY = size.height / 2
        
        runningTotals.drop(1).forEachIndexed { i, total ->
            val prevTotal = runningTotals[i]
            val x = gap + i * (barWidth + gap)
            
            val yPrev = centerY - (prevTotal / maxVal) * (size.height / 2)
            val yCurr = centerY - (total / maxVal) * (size.height / 2)
            
            val isPositive = safeData[i] >= 0
            val color = if (isPositive) Color(0xFF00E676) else Color(0xFFFF5252)
            
            val barHeight = Math.abs(yPrev - yCurr) * currentProgress
            val barTop = if (isPositive) yCurr else yPrev
            
            drawRect(
                brush = GradientFills.barFill(color, color.copy(alpha = 0.4f), barTop, barTop + barHeight),
                topLeft = Offset(x, barTop),
                size = Size(barWidth, barHeight)
            )
            
            // Connector
            if (i > 0) {
                drawLine(
                    color = Color.White.copy(alpha = 0.3f),
                    start = Offset(x - gap, yPrev),
                    end = Offset(x, yPrev),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                )
            }
        }
    }
}
