package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.GradientFills

/**
 * C01. GradientAreaChart
 * FIXED: Y-axis scaling with vertical padding.
 */
@Composable
fun GradientAreaChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    strokeColor: Color = SciFiTheme.extendedColors.primary,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.AREA).filterIsInstance<Float>() }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "AreaAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .drawWithCache {
                val fillBrush = GradientFills.areaFill(strokeColor, size.height)
                onDrawBehind {
                    if (safeData.isEmpty()) return@onDrawBehind
                    
                    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
                    val count = safeData.size
                    val pad = 24.dp.toPx() // Vertical padding
                    val usableH = size.height - pad * 2
                    
                    val points = safeData.mapIndexed { i, v ->
                        val x = i.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                        val y = pad + usableH * (1 - (v / maxVal) * currentProgress)
                        Offset(x, y)
                    }
                    
                    val path = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        for (i in 1 until points.size) {
                            lineTo(points[i].x, points[i].y)
                        }
                    }
                    
                    val fillPath = Path().apply {
                        addPath(path)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)
                        close()
                    }

                    // 1. Area Fill
                    drawPath(path = fillPath, brush = fillBrush)
                    
                    // 2. Glow Stroke
                    drawPath(
                        path = path,
                        color = strokeColor.copy(alpha = 0.2f),
                        style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                    
                    // 3. Sharp Stroke
                    drawPath(
                        path = path,
                        color = strokeColor,
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                    )
                }
            }
    ) {}
}
