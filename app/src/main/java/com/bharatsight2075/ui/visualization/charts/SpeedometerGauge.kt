package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * C20. SpeedometerGauge
 */
@Composable
fun SpeedometerGauge(
    value: Float,
    max: Float,
    label: String,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) (value / max).coerceIn(0f, 1f) else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "SpeedoAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else (value / max)
    val colors = SciFiTheme.extendedColors

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 12.dp.toPx()
            val diameter = size.minDimension - 40.dp.toPx()
            val arcSize = Size(diameter, diameter)
            val topLeft = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)
            
            val startAngle = 135f
            val totalSweep = 270f
            
            // Background
            drawArc(
                color = colors.textDisabled.copy(alpha = 0.1f),
                startAngle = startAngle,
                sweepAngle = totalSweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )
            
            // Color Zones
            drawArc(
                color = Color(0xFF00E676).copy(alpha = 0.4f),
                startAngle = startAngle,
                sweepAngle = totalSweep * 0.6f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                color = Color(0xFFFFD600).copy(alpha = 0.4f),
                startAngle = startAngle + totalSweep * 0.6f,
                sweepAngle = totalSweep * 0.25f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )
            drawArc(
                color = Color(0xFFFF5252).copy(alpha = 0.4f),
                startAngle = startAngle + totalSweep * 0.85f,
                sweepAngle = totalSweep * 0.15f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )
            
            // Needle
            val angle = startAngle + (totalSweep * currentProgress)
            val angleRad = Math.toRadians(angle.toDouble())
            val needleLen = diameter / 2 - 4.dp.toPx()
            val needleEnd = Offset(
                center.x + needleLen * cos(angleRad).toFloat(),
                center.y + needleLen * sin(angleRad).toFloat()
            )
            
            drawLine(Color.White, center, needleEnd, 3.dp.toPx(), cap = StrokeCap.Round)
            drawCircle(Color.White, 6.dp.toPx(), center)
        }
        
        Column(
            modifier = Modifier.padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 24.sp),
                color = colors.textPrimary
            )
            Text(
                text = label,
                style = SciFiTheme.typography.ChartCaption,
                color = colors.textSecondary
            )
        }
    }
}
