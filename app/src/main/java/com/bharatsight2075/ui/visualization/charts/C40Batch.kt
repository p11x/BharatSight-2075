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
import kotlin.math.*

/**
 * C40. MolecularBondGraph
 */
@Composable
fun MolecularBondGraph(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 240.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1500, easing = EaseOutBack),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val centerPoint = center
        val nodeRadius = 10.dp.toPx() * progress
        val orbitDist = 60.dp.toPx() * progress
        
        repeat(6) { i ->
            val angle = i * PI.toFloat() / 3
            val pos = Offset(centerPoint.x + orbitDist * cos(angle), centerPoint.y + orbitDist * sin(angle))
            drawLine(primaryColor.copy(alpha = 0.3f * progress), centerPoint, pos, strokeWidth = 2.dp.toPx())
            drawCircle(primaryColor, nodeRadius, pos)
            drawCircle(primaryColor.copy(alpha = 0.2f), nodeRadius * 1.5f, pos, style = Stroke(1.dp.toPx()))
        }
        drawCircle(primaryColor, nodeRadius * 1.5f, centerPoint)
        drawCircle(primaryColor.copy(alpha = 0.4f), nodeRadius * 2f, centerPoint, style = Stroke(2.dp.toPx()))
    }
}

/**
 * C41. HeatSpiralChart
 */
@Composable
fun HeatSpiralChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 260.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR)
    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, easing = LinearEasing),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val spiralPath = Path()
        
        val maxI = (720 * progress).toInt()
        for (i in 0..maxI step 5) {
            val angle = Math.toRadians(i.toDouble()).toFloat()
            val r = (i / 720f) * size.minDimension / 2 * 0.9f
            val x = centerX + r * cos(angle)
            val y = centerY + r * sin(angle)
            
            val dataIndex = (i.toFloat() / 720 * (safeData.size - 1)).toInt().coerceIn(0, safeData.size - 1)
            val heat = safeData[dataIndex] / maxVal
            val color = lerp(primaryColor.copy(alpha = 0.2f), primaryColor, heat)
            
            if (i == 0) spiralPath.moveTo(x, y) else spiralPath.lineTo(x, y)
            
            drawCircle(color.copy(alpha = progress), 2.dp.toPx(), Offset(x, y))
        }
        drawPath(spiralPath, primaryColor.copy(alpha = 0.3f * progress), style = Stroke(width = 1.dp.toPx()))
    }
}

private fun lerp(start: Color, stop: Color, fraction: Float): Color {
    return Color(
        red = start.red + (stop.red - start.red) * fraction,
        green = start.green + (stop.green - start.green) * fraction,
        blue = start.blue + (stop.blue - start.blue) * fraction,
        alpha = start.alpha + (stop.alpha - start.alpha) * fraction
    )
}

/**
 * C42. GravityScatterChart
 */
@Composable
fun GravityScatterChart(
    modifier: Modifier = Modifier,
    points: List<Offset> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    @Suppress("UNCHECKED_CAST")
    val safePoints = points.takeIf { it.isNotEmpty() } ?: (ChartMockData.generateMockData(ChartType.SCATTER) as List<Offset>)
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = EaseOutBack),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        safePoints.forEach { point ->
            val x = point.x * size.width
            val targetY = point.y * size.height
            val currentY = size.height - (size.height - targetY) * progress
            
            drawCircle(primaryColor.copy(alpha = 0.6f * progress), 4.dp.toPx() * progress, Offset(x, currentY))
            drawCircle(primaryColor.copy(alpha = 0.1f * progress), 8.dp.toPx() * progress, Offset(x, currentY), style = Stroke(1.dp.toPx()))
        }
    }
}

/**
 * C43. SankeyRiverChart
 */
@Composable
fun SankeyRiverChart(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 240.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1500, easing = EaseInOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.horizontalGradient(listOf(primaryColor.copy(alpha = 0.05f), primaryColor.copy(alpha = 0.4f), primaryColor.copy(alpha = 0.05f)))
        onDrawBehind {
            val w = 40.dp.toPx() * progress
            val path = Path().apply {
                val endX = size.width * progress
                moveTo(0f, size.height * 0.2f)
                cubicTo(endX * 0.5f, size.height * 0.2f, endX * 0.5f, size.height * 0.8f, endX, size.height * 0.8f)
                lineTo(endX, size.height * 0.8f + w)
                cubicTo(endX * 0.5f, size.height * 0.8f + w, endX * 0.5f, size.height * 0.2f + w, 0f, size.height * 0.2f + w)
                close()
            }
            drawPath(path, b, alpha = progress)
            drawPath(path, primaryColor.copy(alpha = 0.6f * progress), style = Stroke(1.dp.toPx()))
        }
    }) { }
}

/**
 * C44. TerraceFillChart
 */
@Composable
fun TerraceFillChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).take(5)
    val sortedData = safeData.sorted()
    
    var triggered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { triggered = true }

    val progresses = sortedData.mapIndexed { index, _ ->
        val animatable = remember { Animatable(0f) }
        LaunchedEffect(triggered) {
            if (triggered) {
                delay(index * 100L)
                animatable.animateTo(1f, tween(1000, easing = EaseOutCubic))
            }
        }
        animatable
    }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val maxVal = sortedData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
        sortedData.forEachIndexed { i, value ->
            val p = progresses.getOrNull(i)?.value ?: 0f
            val h = (value / maxVal) * size.height * p
            val y = size.height - h
            
            drawRect(
                color = primaryColor.copy(alpha = 0.1f * (i + 1) * p),
                topLeft = Offset(0f, y),
                size = Size(size.width, h)
            )
            drawLine(
                color = primaryColor.copy(alpha = 0.8f * p),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}
