package com.bharatsight2075.ui.visualization.batch2

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

/**
 * C25. AreaWaveChart
 */
@Composable
fun AreaWaveChart(
    modifier: Modifier = Modifier,
    values: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.AREA)
    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1500, easing = EaseOutCubic),
        label = "WaveAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val fillBrush = Brush.verticalGradient(listOf(primaryColor.copy(alpha = 0.4f), Color.Transparent))
        onDrawBehind {
            val path = Path()
            val count = safeData.size
            
            safeData.forEachIndexed { index, value ->
                val x = index.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                val y = size.height - (value / maxVal * size.height * progress)
                
                if (index == 0) {
                    path.moveTo(0f, size.height)
                    path.lineTo(x, y)
                } else {
                    val prevX = (index - 1).toFloat() / (count - 1).coerceAtLeast(1) * size.width
                    val prevY = size.height - (safeData[index-1] / maxVal * size.height * progress)
                    path.cubicTo(prevX + (x - prevX) / 2, prevY, prevX + (x - prevX) / 2, y, x, y)
                }
            }
            path.lineTo(size.width, size.height)
            path.close()
            
            drawPath(path, fillBrush)
            drawPath(path, primaryColor, style = Stroke(2.dp.toPx()))
        }
    }) { }
}

/**
 * C27. HeatmapRadialChart
 */
@Composable
fun HeatmapRadialChart(
    modifier: Modifier = Modifier,
    grid: List<List<Float>> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    @Suppress("UNCHECKED_CAST")
    val matrix = grid.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockData(ChartType.HEATMAP) as List<List<Float>>
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "RadialHeatAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val rings = matrix.size
        val slices = matrix.firstOrNull()?.size ?: 1
        val maxRadius = size.minDimension / 2 * 0.9f
        val ringWidth = maxRadius / rings
        val sliceAngle = 360f / slices
        
        matrix.forEachIndexed { r, row ->
            row.forEachIndexed { s, value ->
                val color = primaryColor.copy(alpha = value * progress)
                val innerR = r * ringWidth
                val outerR = (r + 1) * ringWidth
                
                drawArc(
                    color = color,
                    startAngle = s * sliceAngle,
                    sweepAngle = sliceAngle,
                    useCenter = false,
                    topLeft = Offset(center.x - outerR, center.y - outerR),
                    size = Size(outerR * 2, outerR * 2),
                    style = Stroke(width = ringWidth, cap = StrokeCap.Butt)
                )
            }
        }
    }
}
