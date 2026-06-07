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
import kotlin.math.*

/**
 * C26. ChoroplethGradientMap
 */
@Composable
fun ChoroplethGradientMap(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        clipRect(bottom = size.height * progress) {
            drawRect(primaryColor.copy(alpha = 0.05f), style = Stroke(1.dp.toPx()))
            drawCircle(primaryColor, radius = 5.dp.toPx(), center = center, alpha = 0.5f)
            drawPath(
                Path().apply {
                    moveTo(center.x, center.y - 50.dp.toPx())
                    lineTo(center.x + 40.dp.toPx(), center.y + 40.dp.toPx())
                    lineTo(center.x - 40.dp.toPx(), center.y + 40.dp.toPx())
                    close()
                },
                primaryColor.copy(alpha = 0.2f)
            )
        }
    }
}

/**
 * C29. LiquidFillGauge
 */
@Composable
fun LiquidFillGauge(
    modifier: Modifier = Modifier,
    percent: Float = 0f,
    fillPercent: Float = 0f,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val displayPercent = if (fillPercent != 0f) fillPercent else percent
    val infiniteTransition = rememberInfiniteTransition(label = "LiquidWave")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)),
        label = "Offset"
    )
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.6f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = EaseInOutSine), RepeatMode.Reverse), label = "Pulse"
    )

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) displayPercent.coerceIn(0f, 1f) else 0f,
        animationSpec = tween(1500, easing = EaseOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.verticalGradient(listOf(primaryColor, primaryColor.copy(alpha = 0.3f)))
        onDrawBehind {
            val radius = size.minDimension / 2 * 0.8f
            val centerPoint = center
            
            drawCircle(primaryColor.copy(alpha = 0.1f * glowPulse), radius = radius + 2.dp.toPx(), center = centerPoint, style = Stroke(width = 4.dp.toPx()))
            drawCircle(primaryColor.copy(alpha = 0.2f), radius = radius, center = centerPoint, style = Stroke(width = 2.dp.toPx()))

            val clipPath = Path().apply { addOval(Rect(centerPoint, radius)) }
            clipPath(clipPath) {
                val wavePath = Path()
                val waveHeight = 10.dp.toPx()
                val fillY = centerPoint.y + radius - (2 * radius * progress)
                
                wavePath.moveTo(-20f, size.height + 20f)
                for (x in 0..size.width.toInt() step 5) {
                    val y = fillY + sin((x.toFloat() / size.width) * 2 * PI.toFloat() + waveOffset) * waveHeight * (1f - progress.coerceIn(0f, 1f))
                    wavePath.lineTo(x.toFloat(), y)
                }
                wavePath.lineTo(size.width + 20f, size.height + 20f)
                wavePath.close()

                drawPath(wavePath, b)
            }
        }
    }) { }
}

/**
 * C38. OrbitalDonutSystem
 */
@Composable
fun OrbitalDonutSystem(
    rings: List<RingData>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp
) {
    val safeRings = rings.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockData(ChartType.RING_CLUSTER) as List<RingData>
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    val infiniteTransition = rememberInfiniteTransition(label = "Orbit")
    val baseRotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing)), label = "BaseRotation"
    )

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val baseRadius = size.minDimension / 2 * 0.4f
        val strokeWidth = 8.dp.toPx()
        
        safeRings.forEachIndexed { index, ring ->
            val r = baseRadius + (index * (strokeWidth + 12.dp.toPx()))
            val speedFactor = 1f + index * 0.5f
            val rotation = baseRotation * speedFactor * (if(index % 2 == 0) 1 else -1)
            
            rotate(rotation, center) {
                drawArc(
                    brush = Brush.sweepGradient(listOf(ring.color.copy(alpha = 0.1f), ring.color)),
                    startAngle = -90f,
                    sweepAngle = ring.value * 360f * progress,
                    useCenter = false,
                    topLeft = Offset(center.x - r, center.y - r),
                    size = Size(r * 2, r * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }
    }
}

/**
 * C34. SpiralTimelineChart
 */
@Composable
fun SpiralTimelineChart(
    events: List<TimelineEvent>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 260.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = events.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockData(ChartType.TIMELINE) as List<TimelineEvent>

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(2000, easing = EaseOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRotations = 3f
        val growthFactor = 15.dp.toPx() / (2 * PI.toFloat())
        
        val spiralPath = Path()
        val step = 5
        val maxAngle = 360 * maxRotations * progress
        for (i in 0..maxAngle.toInt() step step) {
            val angle = Math.toRadians(i.toDouble()).toFloat()
            val r = growthFactor * angle * 10f
            val x = centerX + r * cos(angle)
            val y = centerY + r * sin(angle)
            if (i == 0) spiralPath.moveTo(x, y) else spiralPath.lineTo(x, y)
        }
        drawPath(spiralPath, primaryColor.copy(alpha = 0.3f), style = Stroke(2.dp.toPx()))

        safeData.forEachIndexed { index, event ->
            val t = (index + 1).toFloat() / (safeData.size + 1)
            if (t > progress) return@forEachIndexed
            val angle = t * (2 * PI * maxRotations).toFloat()
            val r = growthFactor * angle * 10f
            val x = centerX + r * cos(angle)
            val y = centerY + r * sin(angle)
            drawCircle(primaryColor, 4.dp.toPx(), Offset(x, y))
        }
    }
}

/**
 * C37. GradientConeChart
 */
@Composable
fun GradientConeChart(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.verticalGradient(listOf(primaryColor, Color.Transparent))
        onDrawBehind {
            clipRect(bottom = size.height * progress) {
                val w = size.width * 0.6f
                val h = size.height * 0.8f
                val top = center.y - h/2
                
                val path = Path().apply {
                    moveTo(center.x - w/2, top)
                    lineTo(center.x + w/2, top)
                    lineTo(center.x, top + h)
                    close()
                }
                drawPath(path, b)
                drawPath(path, primaryColor.copy(alpha = 0.6f), style = Stroke(1.dp.toPx()))
            }
        }
    }) { }
}

/**
 * C35. HexagonalGridMap
 */
@Composable
fun HexagonalGridMap(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 280.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1500, easing = EaseOutCubic),
        label = "chartProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val hexSize = 20.dp.toPx()
        val horizontalSpacing = hexSize * 1.5f
        val verticalSpacing = hexSize * sqrt(3f)
        
        for (q in -4..4) {
            for (r in -3..3) {
                val x = center.x + horizontalSpacing * q
                val y = center.y + verticalSpacing * (r + q/2f)
                
                val alpha = (0.15f * progress).coerceIn(0f, 1f)
                drawCircle(primaryColor.copy(alpha = alpha), 4.dp.toPx(), Offset(x, y))
                
                val path = Path()
                for (i in 0..5) {
                    val angle = 2 * PI / 6 * i
                    val px = x + hexSize * cos(angle).toFloat()
                    val py = y + hexSize * sin(angle).toFloat()
                    if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
                }
                path.close()
                drawPath(path, primaryColor.copy(alpha = 0.05f * progress), style = Stroke(1.dp.toPx()))
            }
        }
    }
}

/**
 * C36. PulseRadarScan
 */
@Composable
fun PulseRadarScan(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 240.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Radar")
    val sweep by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)), label = "Scan"
    )
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.6f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = EaseInOutSine), RepeatMode.Reverse), label = "Pulse"
    )

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val sweepBrush = Brush.sweepGradient(listOf(primaryColor, Color.Transparent))
        onDrawBehind {
            val r = size.minDimension / 2 * 0.9f
            
            repeat(3) { i ->
                val cr = r * (i + 1) / 3
                drawCircle(primaryColor.copy(alpha = 0.1f * glowPulse), cr, center, style = Stroke(1.dp.toPx()))
            }
            
            rotate(sweep, center) {
                drawArc(
                    brush = sweepBrush,
                    startAngle = 0f, sweepAngle = -90f, useCenter = true, alpha = 0.4f
                )
                // Double-pass glow
                drawLine(
                    primaryColor.copy(alpha = 0.2f * glowPulse), 
                    center, Offset(center.x + r, center.y), 
                    strokeWidth = 6.dp.toPx()
                )
                drawLine(
                    primaryColor, center, Offset(center.x + r, center.y), 
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }) { }
}
