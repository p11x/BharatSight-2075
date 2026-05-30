package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

data class RingData(val value: Float, val label: String, val color: Color)

/**
 * C12. RingProgressCluster
 */
@Composable
fun RingProgressCluster(
    rings: List<RingData>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    centerStat: String = "",
    animated: Boolean = true
) {
    val safeRings = rings.takeIf { it.isNotEmpty() } ?: listOf(
        RingData(0.8f, "A", Color.Cyan),
        RingData(0.6f, "B", Color.Magenta),
        RingData(0.4f, "C", Color.Yellow)
    )

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "RingAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 10.dp.toPx()
            val baseRadius = (size.minDimension / 2 - 20.dp.toPx()).coerceAtLeast(1f)
            
            safeRings.forEachIndexed { i, ring ->
                val r = (baseRadius - i * (strokeWidth + 8.dp.toPx())).coerceAtLeast(1f)
                val arcSize = Size(r * 2, r * 2)
                val topLeft = Offset(center.x - r, center.y - r)
                
                // Track
                drawArc(
                    color = colors.textDisabled.copy(alpha = 0.1f),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth)
                )
                
                // Progress
                if (ring.value > 0.001f) {
                    drawArc(
                        brush = Brush.sweepGradient(listOf(ring.color.copy(alpha = 0.4f), ring.color)),
                        startAngle = -90f,
                        sweepAngle = 360f * ring.value * currentProgress,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                    
                    // Glow
                    drawArc(
                        color = ring.color.copy(alpha = 0.2f),
                        startAngle = -90f,
                        sweepAngle = 360f * ring.value * currentProgress,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth + 4.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
        }
        
        if (centerStat.isNotEmpty()) {
            Text(centerStat, style = SciFiTheme.typography.HeroNumber.copy(fontSize = 20.sp), color = colors.textPrimary)
        }
    }
}
