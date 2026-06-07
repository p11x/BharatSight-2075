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
import kotlinx.coroutines.delay

/**
 * C30. MorphingRaceChart
 */
@Composable
fun MorphingRaceChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).take(8)
    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1500, easing = EaseOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.horizontalGradient(listOf(primaryColor, primaryColor.copy(alpha = 0.3f)))
        onDrawBehind {
            val barHeight = 20.dp.toPx()
            val spacing = 15.dp.toPx()
            safeData.forEachIndexed { i, value ->
                val w = (value / maxVal) * size.width * progress
                val y = i * (barHeight + spacing) + spacing
                drawRect(b, Offset(0f, y), Size(w.coerceAtLeast(4f), barHeight))
                drawRect(primaryColor.copy(alpha = 0.2f), Offset(0f, y), Size(size.width, barHeight), style = Stroke(1.dp.toPx()))
            }
        }
    }) { }
}

/**
 * C31. BubbleTreeChart
 */
@Composable
fun BubbleTreeChart(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 240.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = EaseOutBack),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val centerPoint = center
        val mainRadius = 40.dp.toPx() * progress
        drawCircle(primaryColor.copy(alpha = 0.8f * progress), mainRadius, centerPoint)
        drawCircle(primaryColor.copy(alpha = 0.2f * progress), mainRadius + 4.dp.toPx(), centerPoint, style = Stroke(2.dp.toPx()))
        
        repeat(5) { i ->
            val angle = i * 2 * Math.PI / 5
            val dist = 80.dp.toPx() * progress
            val pos = Offset(centerPoint.x + dist * Math.cos(angle).toFloat(), centerPoint.y + dist * Math.sin(angle).toFloat())
            val leafRadius = 20.dp.toPx() * progress
            
            drawLine(primaryColor.copy(alpha = 0.3f * progress), centerPoint, pos, 2.dp.toPx())
            drawCircle(primaryColor.copy(alpha = 0.5f * progress), leafRadius, pos)
        }
    }
}

/**
 * C33. RiverMapChart
 */
@Composable
fun RiverMapChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 240.dp,
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
    val glowPulse by rememberInfiniteTransition(label = "glow").animateFloat(
        initialValue = 0.6f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse), label = "gp"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.verticalGradient(listOf(primaryColor.copy(alpha = 0.5f), Color.Transparent))
        onDrawBehind {
            val path = Path()
            val count = safeData.size
            safeData.forEachIndexed { index, value ->
                val x = index.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                val y = size.height - (value / maxVal * size.height * 0.6f) - (size.height * 0.2f)
                
                if (index == 0) path.moveTo(x, y) else {
                    val prevX = (index - 1).toFloat() / (count - 1).coerceAtLeast(1) * size.width
                    val prevY = size.height - (safeData[index-1] / maxVal * size.height * 0.6f) - (size.height * 0.2f)
                    path.cubicTo(prevX + (x - prevX) / 2, prevY, prevX + (x - prevX) / 2, y, x, y)
                }
            }
            
            clipRect(right = size.width * progress) {
                // Double-pass glow
                drawPath(path, primaryColor.copy(alpha = 0.2f * glowPulse), style = Stroke(10.dp.toPx(), cap = StrokeCap.Round))
                drawPath(path, primaryColor, style = Stroke(4.dp.toPx(), cap = StrokeCap.Round))
                drawPath(path, b, style = Stroke(12.dp.toPx(), cap = StrokeCap.Round), alpha = 0.3f)
            }
        }
    }) { }
}

/**
 * C39. ChromaticWaterfallChart
 */
@Composable
fun ChromaticWaterfallChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).take(6)
    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

    var triggered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { triggered = true }

    val barProgresses = safeData.mapIndexed { index, _ ->
        val animatable = remember { Animatable(0f) }
        LaunchedEffect(triggered) {
            if (triggered) {
                delay(index * 60L)
                animatable.animateTo(1f, tween(800, easing = EaseOutBounce))
            }
        }
        animatable
    }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val count = safeData.size
        val barWidth = (size.width / count.coerceAtLeast(1) * 0.7f).coerceAtLeast(4f)
        val gap = size.width / count.coerceAtLeast(1) * 0.3f
        
        safeData.forEachIndexed { i, value ->
            val p = barProgresses.getOrNull(i)?.value ?: 0f
            val h = (value / maxVal) * size.height * p
            val x = i * (barWidth + gap) + gap / 2
            
            val b = Brush.verticalGradient(
                colors = listOf(primaryColor.copy(alpha = 0.8f), primaryColor.copy(alpha = 0.2f)),
                startY = size.height - h,
                endY = size.height
            )
            
            drawRect(b, Offset(x, size.height - h), Size(barWidth, h))
            drawRect(primaryColor.copy(alpha = 0.9f * p), Offset(x, size.height - h), Size(barWidth, h), style = Stroke(1.dp.toPx()))
        }
    }
}
