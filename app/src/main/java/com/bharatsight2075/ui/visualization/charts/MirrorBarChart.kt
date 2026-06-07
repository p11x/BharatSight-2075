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

data class MirrorData(val left: Float, val right: Float, val label: String)

/**
 * C22. MirrorBarChart
 */
@Composable
fun MirrorBarChart(
    data: List<MirrorData>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 160.dp,
    animated: Boolean = true
) {
    val rawData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).chunked(2).map { MirrorData(it[0] as Float, it[1] as Float, "M") }
    
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
    val colors = SciFiTheme.extendedColors

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val count = rawData.size.coerceAtLeast(1)
        val barHeight = (size.height / (count * 1.5f)).coerceAtLeast(4f)
        val gap = (size.height - (barHeight * count)) / (count + 1)
        val maxVal = rawData.maxOf { maxOf(it.left, it.right) }.coerceAtLeast(0.001f)
        val centerX = size.width / 2

        rawData.forEachIndexed { i, d ->
            val y = gap + i * (barHeight + gap)
            
            // Left
            val wLeft = (d.left / maxVal) * (size.width / 2 - 20.dp.toPx()) * currentProgress
            drawRoundRect(
                brush = Brush.horizontalGradient(listOf(colors.primary, colors.primary.copy(alpha = 0.4f))),
                topLeft = Offset(centerX - wLeft, y),
                size = Size(wLeft, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx())
            )
            // Left Glow
            drawRoundRect(
                color = colors.primary.copy(alpha = 0.2f * glowPulse),
                topLeft = Offset(centerX - wLeft, y),
                size = Size(wLeft, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx()),
                style = androidx.compose.ui.graphics.drawscope.Stroke(1.dp.toPx() * 3.5f)
            )
            
            // Right
            val wRight = (d.right / maxVal) * (size.width / 2 - 20.dp.toPx()) * currentProgress
            drawRoundRect(
                brush = Brush.horizontalGradient(listOf(colors.accent.copy(alpha = 0.4f), colors.accent)),
                topLeft = Offset(centerX, y),
                size = Size(wRight, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx())
            )
            // Right Glow
            drawRoundRect(
                color = colors.accent.copy(alpha = 0.2f * glowPulse),
                topLeft = Offset(centerX, y),
                size = Size(wRight, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx()),
                style = androidx.compose.ui.graphics.drawscope.Stroke(1.dp.toPx() * 3.5f)
            )
        }
        
        // Axis
        drawLine(Color.White.copy(alpha = 0.3f), Offset(centerX, 0f), Offset(centerX, size.height), 1.dp.toPx())
    }
}
