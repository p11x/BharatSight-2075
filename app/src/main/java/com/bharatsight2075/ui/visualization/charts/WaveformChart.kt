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
import kotlin.math.sin

@Composable
fun WaveformChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 120.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.LINE)
    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = EaseOutCubic),
        label = "chartProgress"
    )
    val infiniteTransition = rememberInfiniteTransition(label = "Waveform")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "Phase"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.verticalGradient(listOf(primaryColor, Color.Transparent))
        onDrawBehind {
            clipRect(right = size.width * progress) {
                val path = Path()
                val count = safeData.size
                val midY = size.height / 2
                
                safeData.forEachIndexed { index, value ->
                    val x = index.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                    val amplitude = (value / maxVal) * (size.height / 3)
                    val y = midY + sin(index.toFloat() * 0.5f + phase) * amplitude
                    
                    if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                
                drawPath(path, primaryColor, style = Stroke(width = 2.dp.toPx()))
                drawPath(path, primaryColor.copy(alpha = 0.2f), style = Stroke(width = 8.dp.toPx()))
                
                // Mirror wave
                val mirrorPath = Path()
                safeData.forEachIndexed { index, value ->
                    val x = index.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                    val amplitude = (value / maxVal) * (size.height / 3)
                    val y = midY - sin(index.toFloat() * 0.5f + phase) * amplitude
                    
                    if (index == 0) mirrorPath.moveTo(x, y) else mirrorPath.lineTo(x, y)
                }
                drawPath(mirrorPath, primaryColor.copy(alpha = 0.4f), style = Stroke(width = 1.dp.toPx()))
            }
        }
    }) { }
}
