package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

data class BubblePoint(val x: Float, val y: Float, val radius: Float, val color: Color? = null)

@Composable
fun BubbleScatterChart(
    data: List<BubblePoint> = emptyList(),
    points: List<Triple<Float, Float, Float>> = emptyList(),
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    @Suppress("UNCHECKED_CAST")
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: points.map { BubblePoint(it.first, it.second, it.third) }.takeIf { it.isNotEmpty() }
        ?: (ChartMockData.generateMockData(ChartType.BUBBLE) as List<Triple<Float, Float, Float>>).map { BubblePoint(it.first, it.second, it.third) }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1000, easing = EaseOutCubic),
        label = "BubbleAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.radialGradient(listOf(primaryColor, primaryColor.copy(alpha = 0.2f)))
        onDrawBehind {
            safeData.forEach { point ->
                val x = point.x * size.width
                val y = size.height - (point.y * size.height)
                val r = point.radius * size.minDimension * 0.2f * progress
                
                drawCircle(
                    brush = b,
                    radius = r.coerceAtLeast(4f),
                    center = Offset(x, y)
                )
                drawCircle(
                    color = primaryColor.copy(alpha = 0.6f),
                    radius = r.coerceAtLeast(4f),
                    center = Offset(x, y),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
        }
    }) { }
}
