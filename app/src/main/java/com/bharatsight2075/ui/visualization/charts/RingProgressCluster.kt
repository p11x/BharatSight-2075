package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

data class RingData(val value: Float, val label: String, val color: Color)

/**
 * C12. RingProgressCluster
 */
@Composable
fun RingProgressCluster(
    modifier: Modifier = Modifier,
    rings: List<RingData> = emptyList(),
    values: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    centerStat: String = "",
    animated: Boolean = true,
    primaryColor: Color = SciFiTheme.extendedColors.primary // Added primaryColor
) {
    @Suppress("UNCHECKED_CAST")
    val safeRings = rings.takeIf { it.isNotEmpty() } 
        ?: values.mapIndexed { i, v -> RingData(v, "Item $i", SciFiTheme.extendedColors.primary) }.takeIf { it.isNotEmpty() }
        ?: (ChartMockData.generateMockData(ChartType.RING_CLUSTER) as List<RingData>)

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

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 10.dp.toPx()
            val baseRadius = (size.minDimension / 2 - 20.dp.toPx()).coerceAtLeast(1f)
            
            safeRings.forEachIndexed { i, ring ->
                val r = baseRadius - (i * (strokeWidth + 8.dp.toPx()))
                if (r <= 0) return@forEachIndexed
                
                // Track
                drawCircle(
                    color = ring.color.copy(alpha = 0.1f),
                    radius = r,
                    style = Stroke(width = strokeWidth)
                )
                
                // Progress
                // Double-pass glow
                drawArc(
                    color = ring.color.copy(alpha = 0.2f * glowPulse),
                    startAngle = -90f,
                    sweepAngle = ring.value * 360f * currentProgress,
                    useCenter = false,
                    topLeft = Offset(center.x - r, center.y - r),
                    size = Size(r * 2, r * 2),
                    style = Stroke(width = strokeWidth + 4.dp.toPx(), cap = StrokeCap.Round)
                )
                drawArc(
                    color = ring.color,
                    startAngle = -90f,
                    sweepAngle = ring.value * 360f * currentProgress,
                    useCenter = false,
                    topLeft = Offset(center.x - r, center.y - r),
                    size = Size(r * 2, r * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }
        
        if (centerStat.isNotEmpty()) {
            Text(
                text = centerStat,
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 20.sp),
                color = primaryColor
            )
        }
    }
}
