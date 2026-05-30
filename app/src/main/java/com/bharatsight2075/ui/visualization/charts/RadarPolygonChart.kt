package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * C09. RadarPolygonChart
 * FIXED: Angle offset and centering.
 */
@Composable
fun RadarPolygonChart(
    data: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.RADAR).filterIsInstance<Float>() }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "RadarAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors
    val primary = colors.primary

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val radius = size.minDimension / 2 * 0.7f // Leave room for labels
        val angleStep = 2 * PI / safeData.size
        val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
        val radarCenter = center

        // Draw Axes & Web
        for (step in 1..4) {
            val r = radius * (step / 4f)
            drawCircle(colors.textDisabled.copy(alpha = 0.05f), r, radarCenter, style = Stroke(1.dp.toPx()))
        }

        safeData.indices.forEach { i ->
            val angle = -PI / 2 + i * angleStep
            val end = Offset(
                radarCenter.x + (radius * cos(angle)).toFloat(),
                radarCenter.y + (radius * sin(angle)).toFloat()
            )
            drawLine(colors.textDisabled.copy(alpha = 0.2f), radarCenter, end, 1.dp.toPx())
        }

        // Polygon
        val points = safeData.mapIndexed { i, v ->
            val angle = -PI / 2 + i * angleStep
            val r = (v / maxVal) * radius * currentProgress
            Offset(
                radarCenter.x + (r * cos(angle)).toFloat(),
                radarCenter.y + (r * sin(angle)).toFloat()
            )
        }

        if (points.size >= 3) {
            val path = Path().apply {
                moveTo(points[0].x, points[0].y)
                points.forEach { lineTo(it.x, it.y) }
                close()
            }
            
            drawPath(path, primary, alpha = 0.3f)
            drawPath(path, primary, style = Stroke(2.dp.toPx(), join = StrokeJoin.Round))
            
            // Glow
            drawPath(path, primary.copy(alpha = 0.2f), style = Stroke(6.dp.toPx(), join = StrokeJoin.Round))
        }
    }
}
