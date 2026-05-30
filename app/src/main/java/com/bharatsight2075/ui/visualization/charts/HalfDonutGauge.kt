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
import com.bharatsight2075.ui.visualization.GradientFills
import kotlin.math.cos
import kotlin.math.sin

/**
 * C04. HalfDonutGauge
 */
@Composable
fun HalfDonutGauge(
    value: Float,
    max: Float,
    label: String,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 140.dp,
    animated: Boolean = true
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) (value / max).coerceIn(0f, 1f) else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "GaugeAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else (value / max)
    val colors = SciFiTheme.extendedColors
    val primary = colors.primary

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.BottomCenter) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 16.dp.toPx()
            val diameter = size.width - 32.dp.toPx()
            val arcSize = Size(diameter, diameter)
            val topLeft = Offset((size.width - diameter) / 2, size.height - diameter / 2)
            
            // Track Background
            drawArc(
                color = colors.textDisabled.copy(alpha = 0.1f),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            
            val arcBrush = Brush.sweepGradient(listOf(primary.copy(0.4f), primary))

            // Progress Arc Glow
            drawArc(
                brush = arcBrush,
                startAngle = 180f,
                sweepAngle = 180f * currentProgress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth + 6.dp.toPx(), cap = StrokeCap.Round),
                alpha = 0.2f
            )
            
            // Progress Arc Sharp
            drawArc(
                brush = arcBrush,
                startAngle = 180f,
                sweepAngle = 180f * currentProgress,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            
            // Needle
            val angle = 180f + (180f * currentProgress)
            val angleRad = Math.toRadians(angle.toDouble())
            val needleLen = diameter / 2 - 8.dp.toPx()
            val pivot = Offset(center.x, size.height)
            val needleEnd = Offset(
                pivot.x + needleLen * cos(angleRad).toFloat(),
                pivot.y + needleLen * sin(angleRad).toFloat()
            )
            
            drawLine(Color.White, pivot, needleEnd, 2.dp.toPx(), cap = StrokeCap.Round)
            drawCircle(Color.White, 4.dp.toPx(), pivot)
        }
        
        Column(
            modifier = Modifier.padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                style = SciFiTheme.typography.BodyMono.copy(fontSize = 18.sp),
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
