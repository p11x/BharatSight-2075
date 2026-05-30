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

/**
 * C02. MultiLineChart
 */
@Composable
fun MultiLineChart(
    data: List<List<Float>>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() && it.any { line -> line.isNotEmpty() } } 
        ?: remember { listOf(ChartMockData.generateMockData(ChartType.MULTI_LINE).filterIsInstance<Float>()) }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "LineAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .drawWithCache {
                onDrawBehind {
                    val globalMax = safeData.flatten().maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
                    val maxPoints = safeData.maxOf { it.size }
                    val pad = 24.dp.toPx()
                    val usableH = size.height - pad * 2
                    
                    safeData.forEachIndexed { lineIndex, linePoints ->
                        if (linePoints.size < 2) return@forEachIndexed
                        val strokeColor = colors.getOrElse(lineIndex) { Color.Cyan }
                        
                        val path = Path().apply {
                            linePoints.forEachIndexed { i, v ->
                                val x = i.toFloat() / (maxPoints - 1).coerceAtLeast(1) * size.width
                                val y = pad + usableH * (1 - (v / globalMax) * currentProgress)
                                if (i == 0) moveTo(x, y) else lineTo(x, y)
                            }
                        }
                        
                        // Pass 1: Glow
                        drawPath(
                            path = path,
                            color = strokeColor.copy(alpha = 0.2f),
                            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                        // Pass 2: Sharp
                        drawPath(
                            path = path,
                            color = strokeColor,
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                    }
                }
            }
    ) {}
}
