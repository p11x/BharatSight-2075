package com.bharatsight2075.ui.visualization.charts

import android.graphics.PathMeasure
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
import com.bharatsight2075.ui.visualization.GradientFills
import kotlin.math.*

/**
 * C26. ChoroplethGradientMap
 * India state map with color intensity per metric.
 */
@Composable
fun ChoroplethGradientMap(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val indiaBounds = Rect(Offset.Zero, size)
        drawRect(primaryColor.copy(alpha = 0.05f), style = Stroke(1.dp.toPx()))
        // Representative map geometry
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

/**
 * C29. LiquidFillGauge
 */
@Composable
fun LiquidFillGauge(
    percent: Float,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "LiquidWave")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)),
        label = "Offset"
    )

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) percent.coerceIn(0f, 1f) else 0f,
        animationSpec = tween(1500, easing = EaseOutCubic),
        label = "FillProgress"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val radius = size.minDimension / 2 * 0.8f
        val centerPoint = center
        
        drawCircle(primaryColor.copy(alpha = 0.1f), radius = radius, center = centerPoint, style = Stroke(width = 2.dp.toPx()))

        val clipPath = Path().apply { addOval(Rect(centerPoint, radius)) }
        clipPath(clipPath) {
            val wavePath = Path()
            val waveHeight = 10.dp.toPx()
            val fillY = centerPoint.y + radius - (2 * radius * progress)
            
            wavePath.moveTo(-20f, size.height + 20f)
            for (x in 0..size.width.toInt() step 5) {
                val y = fillY + sin((x.toFloat() / size.width) * 2 * PI.toFloat() + waveOffset) * waveHeight
                wavePath.lineTo(x.toFloat(), y)
            }
            wavePath.lineTo(size.width + 20f, size.height + 20f)
            wavePath.close()

            drawPath(wavePath, Brush.verticalGradient(listOf(primaryColor, primaryColor.copy(alpha = 0.3f)), startY = fillY - waveHeight, endY = size.height))
        }
    }
}

/**
 * C38. OrbitalDonutSystem
 */
@Composable
fun OrbitalDonutSystem(
    rings: List<RingData>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "OrbitalAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    val rotation by rememberInfiniteTransition(label = "Orbit").animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing)), label = "Orbit"
    )

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val baseRadius = size.minDimension / 2 * 0.4f
        val strokeWidth = 8.dp.toPx()
        
        rings.forEachIndexed { index, ring ->
            val r = baseRadius + (index * (strokeWidth + 12.dp.toPx()))
            rotate(rotation * (if(index % 2 == 0) 1 else -1), center) {
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
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(2000, easing = LinearEasing),
        label = "SpiralAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRotations = 3f
        val growthFactor = 15f
        
        val spiralPath = Path()
        for (i in 0..(360 * maxRotations * progress).toInt() step 5) {
            val angle = Math.toRadians(i.toDouble()).toFloat()
            val r = growthFactor * angle
            val x = centerX + r * cos(angle)
            val y = centerY + r * sin(angle)
            if (i == 0) spiralPath.moveTo(x, y) else spiralPath.lineTo(x, y)
        }
        drawPath(spiralPath, primaryColor.copy(alpha = 0.3f), style = Stroke(2.dp.toPx()))
    }
}

/**
 * C37. GradientConeChart
 * 3D-look cone/funnel chart with gradient layers.
 */
@Composable
fun GradientConeChart(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val w = size.width * 0.6f
        val h = size.height * 0.8f
        val top = center.y - h/2
        
        val path = Path().apply {
            moveTo(center.x - w/2, top)
            lineTo(center.x + w/2, top)
            lineTo(center.x, top + h)
            close()
        }
        drawPath(path, Brush.verticalGradient(listOf(primaryColor, Color.Transparent)))
        drawPath(path, primaryColor.copy(alpha = 0.6f), style = Stroke(1.dp.toPx()))
    }
}

/**
 * C35. HexagonalGridMap
 */
@Composable
fun HexagonalGridMap(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val hexSize = 20.dp.toPx()
        for (q in -2..2) {
            for (r in -2..2) {
                val x = center.x + hexSize * 1.5f * q
                val y = center.y + hexSize * sqrt(3f) * (r + q/2f)
                drawCircle(primaryColor.copy(alpha = 0.1f), hexSize, Offset(x, y), style = Stroke(1.dp.toPx()))
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
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val rotation by rememberInfiniteTransition(label = "Radar").animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)), label = "Scan"
    )

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val r = size.minDimension / 2 * 0.9f
        drawCircle(primaryColor.copy(alpha = 0.1f), r, center, style = Stroke(1.dp.toPx()))
        rotate(rotation, center) {
            drawArc(
                brush = Brush.sweepGradient(listOf(primaryColor, Color.Transparent)),
                startAngle = 0f, sweepAngle = 90f, useCenter = true, alpha = 0.4f
            )
            drawLine(primaryColor, center, Offset(center.x + r, center.y), strokeWidth = 2.dp.toPx())
        }
    }
}
