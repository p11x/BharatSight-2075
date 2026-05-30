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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

data class RingData(val value: Float, val label: String, val brush: Brush)

/**
 * C12. RingProgressCluster
 * 3 concentric rings, each with gap and gradient arc, center stat.
 */
@Composable
fun RingProgressCluster(
    rings: List<RingData>,
    modifier: Modifier = Modifier,
    centerStat: String = "",
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "RingClusterAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            val baseRadius = size.minDimension / 2 - strokeWidth
            
            rings.forEachIndexed { index, ring ->
                val ringRadius = baseRadius - (index * (strokeWidth + 8.dp.toPx()))
                val diameter = ringRadius * 2
                val topLeft = Offset(center.x - ringRadius, center.y - ringRadius)
                val arcSize = Size(diameter, diameter)
                
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
                drawArc(
                    brush = ring.brush,
                    startAngle = -90f,
                    sweepAngle = ring.value * 360f * currentProgress,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                
                // Progress Glow
                drawArc(
                    brush = ring.brush,
                    startAngle = -90f,
                    sweepAngle = ring.value * 360f * currentProgress,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth + 4.dp.toPx(), cap = StrokeCap.Round),
                    alpha = 0.2f
                )
            }
        }
        
        if (centerStat.isNotEmpty()) {
            Text(
                text = centerStat,
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 18.sp),
                color = colors.textPrimary
            )
        }
    }
}

@Preview
@Composable
fun PreviewRingProgressCluster() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        RingProgressCluster(
            rings = listOf(
                RingData(0.81f, "GDP", GradPalette.TEAL_PURPLE),
                RingData(0.65f, "INF", GradPalette.ORANGE_PINK),
                RingData(0.42f, "TRD", GradPalette.GREEN_TEAL)
            ),
            centerStat = "81%",
            modifier = Modifier.size(200.dp)
        )
    }
}
