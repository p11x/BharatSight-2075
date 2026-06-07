package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

@Composable
fun WaterfallBarChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    @Suppress("UNCHECKED_CAST")
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockData(ChartType.WATERFALL) as List<Float>
    
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

    // Staggered progress for each bar
    val barProgresses = safeData.mapIndexed { index, _ ->
        val animatable = remember { Animatable(0f) }
        LaunchedEffect(triggered) {
            if (triggered) {
                kotlinx.coroutines.delay(index * 60L)
                animatable.animateTo(1f, tween(800, easing = EaseOutCubic))
            }
        }
        animatable
    }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val count = safeData.size
        val barWidth = (size.width / count.coerceAtLeast(1) * 0.6f).coerceAtLeast(4f)
        val gap = size.width / count.coerceAtLeast(1) * 0.4f
        
        var currentSum = 0f
        val maxAbsSum = safeData.scan(0f) { acc, f -> acc + f }.map { Math.abs(it) }.maxOrNull()?.coerceAtLeast(1f) ?: 1f
        val scale = (size.height / 2) / maxAbsSum
        
        safeData.forEachIndexed { i, value ->
            val barProgress = barProgresses.getOrNull(i)?.value ?: 0f
            val startY = size.height / 2 - (currentSum * scale)
            val endY = size.height / 2 - ((currentSum + value) * scale)
            val h = Math.abs(endY - startY).coerceAtLeast(2f) * barProgress
            val top = minOf(startY, endY)
            
            val x = i * (barWidth + gap) + gap / 2
            val color = if (value >= 0) Color(0xFF00E676) else Color(0xFFFF5252)
            
            drawRect(color.copy(alpha = 0.3f), Offset(x, top), Size(barWidth, h))
            
            // Double-pass glow
            drawRect(
                color = color.copy(alpha = 0.2f * glowPulse),
                topLeft = Offset(x, top),
                size = Size(barWidth, h),
                style = Stroke(width = 1.dp.toPx() * 3.5f)
            )
            drawRect(color, Offset(x, top), Size(barWidth, h), style = Stroke(1.dp.toPx()))
            
            // Connecting dashed line
            if (i > 0) {
                val prevX = (i - 1) * (barWidth + gap) + gap / 2 + barWidth
                drawLine(primaryColor.copy(alpha = 0.2f * barProgress), Offset(prevX, startY), Offset(x, startY), strokeWidth = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f)))
            }
            
            currentSum += value
        }
    }
}
