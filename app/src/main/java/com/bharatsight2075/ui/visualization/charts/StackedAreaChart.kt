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

@Composable
fun StackedAreaChart(
    modifier: Modifier = Modifier,
    layers: List<List<Float>> = emptyList(),
    datasets: List<List<Float>> = emptyList(),
    data: List<List<Float>> = emptyList(), // Added data for compatibility
    brushes: List<Brush> = emptyList(), // Added brushes for compatibility
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val finalLayers = layers.takeIf { it.isNotEmpty() } 
        ?: datasets.takeIf { it.isNotEmpty() }
        ?: data.takeIf { it.isNotEmpty() } 
        ?: @Suppress("UNCHECKED_CAST") (ChartMockData.generateMockData(ChartType.MULTI_LINE) as List<List<Float>>)
    
    // Calculate stacked values
    val pointCount = finalLayers.firstOrNull()?.size ?: 0
    val stackedData = List(pointCount) { i ->
        var sum = 0f
        finalLayers.map { layer -> 
            sum += layer.getOrElse(i) { 0f }
            sum
        }
    }
    
    val maxVal = stackedData.lastOrNull()?.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

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

    val colors = listOf(primaryColor, Color(0xFFFF6B35), Color(0xFF7C4DFF), Color(0xFF00E676))

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        onDrawBehind {
            clipRect(bottom = size.height * progress) {
                // Draw from back to front
                for (layerIdx in finalLayers.indices.reversed()) {
                    val path = Path()
                    val color = colors[layerIdx % colors.size]
                    val brush = brushes.getOrNull(layerIdx)
                    
                    stackedData.forEachIndexed { i, stack ->
                        val x = i.toFloat() / (pointCount - 1).coerceAtLeast(1) * size.width
                        val valToDraw = stack[layerIdx]
                        val y = size.height - (valToDraw / maxVal * size.height)
                        
                        if (i == 0) {
                            path.moveTo(x, size.height)
                            path.lineTo(x, y)
                        } else {
                            path.lineTo(x, y)
                        }
                        
                        if (i == pointCount - 1) {
                            path.lineTo(x, size.height)
                            path.close()
                        }
                    }
                    
                    if (brush != null) {
                        drawPath(path, brush)
                    } else {
                        drawPath(path, color.copy(alpha = 0.6f))
                    }
                    
                    // Double-pass glow
                    drawPath(
                        path = path,
                        color = color.copy(alpha = 0.2f * glowPulse),
                        style = Stroke(width = 1.dp.toPx() * 3.5f)
                    )
                    drawPath(path, color.copy(alpha = 0.9f), style = Stroke(width = 1.dp.toPx()))
                }
            }
        }
    }) { }
}
