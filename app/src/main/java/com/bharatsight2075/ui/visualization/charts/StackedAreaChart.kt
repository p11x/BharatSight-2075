package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.GradientFills

/**
 * C07. StackedAreaChart
 */
@Composable
fun StackedAreaChart(
    data: List<List<Float>>,
    brushes: List<Brush>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: listOf(listOf(30f, 40f, 35f, 50f), listOf(20f, 25f, 30f, 20f))
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "StackedAreaAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors
    val primary = colors.primary
    val accent = colors.accent

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val pointCount = safeData.firstOrNull()?.size ?: 0
        if (pointCount < 2) return@Canvas
        
        val spacing = size.width / (pointCount - 1)
        
        val stackedData = MutableList(safeData.size) { FloatArray(pointCount) }
        for (i in 0 until pointCount) {
            var currentStack = 0f
            for (j in safeData.indices) {
                currentStack += safeData[j][i]
                stackedData[j][i] = currentStack
            }
        }
        
        val globalMax = stackedData.last().maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

        clipRect(0f, size.height * (1 - currentProgress), size.width, size.height) {
            for (j in safeData.indices.reversed()) {
                val fillPath = Path()
                val color = when(j) {
                    0 -> primary
                    1 -> accent
                    else -> Color.Magenta
                }
                
                for (i in 0 until pointCount) {
                    val x = i * spacing
                    val y = size.height - (stackedData[j][i] / globalMax * size.height)
                    if (i == 0) fillPath.moveTo(x, y) else fillPath.lineTo(x, y)
                }
                
                fillPath.lineTo(size.width, size.height)
                fillPath.lineTo(0f, size.height)
                fillPath.close()
                
                drawPath(fillPath, color.copy(alpha = 0.5f))
                drawPath(fillPath, color, style = Stroke(1.dp.toPx()))
            }
        }
    }
}
