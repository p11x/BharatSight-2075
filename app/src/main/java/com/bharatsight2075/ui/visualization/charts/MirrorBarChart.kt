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

data class MirrorData(val left: Float, val right: Float, val label: String)

/**
 * C22. MirrorBarChart
 */
@Composable
fun MirrorBarChart(
    data: List<MirrorData>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: listOf(MirrorData(40f, 60f, "A"), MirrorData(70f, 30f, "B"), MirrorData(50f, 50f, "C"))
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "MirrorAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val count = safeData.size
        val barHeight = (size.height / (count * 1.5f))
        val gap = (size.height - (barHeight * count)) / (count + 1)
        val maxVal = safeData.maxOf { maxOf(it.left, it.right) }.coerceAtLeast(0.001f)
        val centerX = size.width / 2

        safeData.forEachIndexed { i, d ->
            val y = gap + i * (barHeight + gap)
            
            // Left
            val wLeft = (d.left / maxVal) * (size.width / 2 - 20.dp.toPx()) * currentProgress
            drawRoundRect(
                brush = Brush.horizontalGradient(listOf(colors.primary, colors.primary.copy(alpha = 0.4f))),
                topLeft = Offset(centerX - wLeft, y),
                size = Size(wLeft, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx())
            )
            
            // Right
            val wRight = (d.right / maxVal) * (size.width / 2 - 20.dp.toPx()) * currentProgress
            drawRoundRect(
                brush = Brush.horizontalGradient(listOf(colors.accent.copy(alpha = 0.4f), colors.accent)),
                topLeft = Offset(centerX, y),
                size = Size(wRight, barHeight),
                cornerRadius = CornerRadius(2.dp.toPx())
            )
        }
        
        // Axis
        drawLine(Color.White.copy(alpha = 0.3f), Offset(centerX, 0f), Offset(centerX, size.height), 1.dp.toPx())
    }
}
