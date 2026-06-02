package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C18. PolarAreaChart
 */
@Composable
fun PolarAreaChart(
    data: List<Float>,
    brushes: List<Brush>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: listOf(80f, 60f, 90f, 40f, 70f)
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "PolarAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val maxRadius = size.minDimension / 2 * 0.9f
        val angleStep = 360f / safeData.size
        val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
        
        var currentAngle = -90f
        
        safeData.forEachIndexed { index, value ->
            val radius = (value / maxVal) * maxRadius * currentProgress
            if (radius > 0.1f) {
                val color = when(index % 3) {
                    0 -> colors.primary
                    1 -> colors.accent
                    else -> Color(0xFF39FF14)
                }
                
                drawArc(
                    color = color.copy(alpha = 0.6f),
                    startAngle = currentAngle,
                    sweepAngle = angleStep,
                    useCenter = true,
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                    topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius)
                )
                
                drawArc(
                    color = color,
                    startAngle = currentAngle,
                    sweepAngle = angleStep,
                    useCenter = true,
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                    topLeft = androidx.compose.ui.geometry.Offset(center.x - radius, center.y - radius),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
            
            currentAngle += angleStep
        }
    }
}
