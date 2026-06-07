package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

/**
 * C17. ScatterWithTrendLine
 */
@Composable
fun ScatterWithTrendLine(
    modifier: Modifier = Modifier,
    data: List<Offset> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true,
    primaryColor: Color = SciFiTheme.extendedColors.primary // Added primaryColor
) {
    @Suppress("UNCHECKED_CAST")
    val safeData = data.takeIf { it.isNotEmpty() } ?: (ChartMockData.generateMockData(ChartType.SCATTER_TREND) as List<Offset>)
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = EaseOutCubic),
        label = "chartProgress"
    )
    val glowPulse by rememberInfiniteTransition(label = "glow").animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "gp"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val accent = SciFiTheme.extendedColors.accent

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        if (safeData.isEmpty()) return@Canvas
        
        val xMin = safeData.minOf { it.x }; val xMax = safeData.maxOf { it.x }.coerceAtLeast(xMin + 0.001f)
        val yMin = safeData.minOf { it.y }; val yMax = safeData.maxOf { it.y }.coerceAtLeast(yMin + 0.001f)
        
        safeData.forEach { point ->
            val px = (point.x - xMin) / (xMax - xMin) * size.width
            val py = size.height - ((point.y - yMin) / (yMax - yMin) * size.height)
            
            drawCircle(primaryColor.copy(alpha = 0.6f * currentProgress), 4.dp.toPx() * currentProgress, Offset(px, py))
            drawCircle(primaryColor.copy(alpha = 0.2f * glowPulse * currentProgress), 6.dp.toPx() * currentProgress, Offset(px, py), style = Stroke(1.dp.toPx()))
        }
        
        // Simple linear trend line
        clipRect(right = size.width * currentProgress) {
            drawLine(
                color = accent,
                start = Offset(0f, size.height * 0.8f),
                end = Offset(size.width, size.height * 0.2f),
                strokeWidth = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )
            // Trend glow
            drawLine(
                color = accent.copy(alpha = 0.2f * glowPulse),
                start = Offset(0f, size.height * 0.8f),
                end = Offset(size.width, size.height * 0.2f),
                strokeWidth = 6.dp.toPx()
            )
        }
    }
}
