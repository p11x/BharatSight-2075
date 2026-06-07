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
 * C18. PolarAreaChart
 */
@Composable
fun PolarAreaChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    values: List<Float> = emptyList(),
    brushes: List<Brush> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true,
    primaryColor: Color = SciFiTheme.extendedColors.primary // Added primaryColor
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).take(6)
    
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

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val maxRadius = size.minDimension / 2 * 0.9f
        val angleStep = 360f / safeData.size
        val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
        
        var currentAngle = -90f
        safeData.forEachIndexed { i, value ->
            val r = (value / maxVal) * maxRadius * currentProgress
            val brush = brushes.getOrNull(i) ?: Brush.radialGradient(listOf(primaryColor.copy(alpha = 0.6f), Color.Transparent))
            
            drawArc(
                brush = brush,
                startAngle = currentAngle,
                sweepAngle = angleStep,
                useCenter = true,
                topLeft = Offset(center.x - r, center.y - r),
                size = Size(r * 2, r * 2)
            )
            // Double-pass glow
            drawArc(
                color = primaryColor.copy(alpha = 0.2f * glowPulse),
                startAngle = currentAngle,
                sweepAngle = angleStep,
                useCenter = true,
                topLeft = Offset(center.x - r, center.y - r),
                size = Size(r * 2, r * 2),
                style = Stroke(3.dp.toPx())
            )
            drawArc(
                color = primaryColor.copy(alpha = 0.9f),
                startAngle = currentAngle,
                sweepAngle = angleStep,
                useCenter = true,
                topLeft = Offset(center.x - r, center.y - r),
                size = Size(r * 2, r * 2),
                style = Stroke(1.dp.toPx())
            )
            currentAngle += angleStep
        }
    }
}
