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
fun MultiLineChart(
    modifier: Modifier = Modifier,
    lines: List<List<Float>> = emptyList(),
    datasets: List<List<Float>> = emptyList(),
    data: List<List<Float>> = emptyList(), // Added for compatibility
    colors: List<Color> = emptyList(), // Added for compatibility
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    @Suppress("UNCHECKED_CAST")
    val finalLines = lines.takeIf { it.isNotEmpty() } 
        ?: datasets.takeIf { it.isNotEmpty() }
        ?: data.takeIf { it.isNotEmpty() }
        ?: (ChartMockData.generateMockData(ChartType.MULTI_LINE) as List<List<Float>>)
    
    val maxVal = finalLines.flatten().maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

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

    val defaultColors = listOf(primaryColor, Color(0xFFFF6B35), Color(0xFF7C4DFF), Color(0xFF00E676))
    val finalColors = if (colors.isNotEmpty()) colors else defaultColors

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        onDrawBehind {
            clipRect(bottom = size.height * progress) {
                finalLines.forEachIndexed { lineIndex, lineData ->
                    val path = Path()
                    val count = lineData.size
                    val color = finalColors[lineIndex % finalColors.size]
                    
                    lineData.forEachIndexed { index, value ->
                        val x = index.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                        val y = size.height - (value / maxVal * size.height)
                        
                        if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                    }
                    
                    // Double-pass glow
                    drawPath(
                        path = path,
                        color = color.copy(alpha = 0.2f * glowPulse),
                        style = Stroke(width = 2.dp.toPx() * 3.5f, cap = StrokeCap.Round)
                    )
                    drawPath(path, color.copy(alpha = 0.9f), style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
                }
            }
            
            // Reference grid
            repeat(3) { i ->
                val gy = size.height * (i + 1) / 4
                drawLine(primaryColor.copy(alpha = 0.05f), Offset(0f, gy), Offset(size.width, gy), strokeWidth = 1.dp.toPx())
            }
        }
    }) { }
}
