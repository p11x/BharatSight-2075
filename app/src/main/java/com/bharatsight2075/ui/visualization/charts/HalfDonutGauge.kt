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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * C04. HalfDonutGauge
 * 180° semicircle gauge, gradient track, needle indicator.
 */
@Composable
fun HalfDonutGauge(
    value: Float,
    max: Float,
    label: String,
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.GREEN_TEAL,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = (value / max).coerceIn(0f, 1f),
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "GaugeAnim"
    )
    
    val currentProgress = if (animated) progress else (value / max)
    val colors = SciFiTheme.extendedColors

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 16.dp.toPx()
            val diameter = size.width - strokeWidth
            val arcSize = Size(diameter, diameter)
            val topLeft = Offset(strokeWidth / 2, size.height - diameter / 2 - strokeWidth / 2)
            
            // Track Background
            drawArc(
                color = colors.textDisabled.copy(alpha = 0.2f),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            
            // Progress Arc Glow
            drawArc(
                brush = brush,
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
                brush = brush,
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
            val needleEnd = Offset(
                center.x + needleLen * cos(angleRad).toFloat(),
                (size.height - strokeWidth / 2) + needleLen * sin(angleRad).toFloat()
            )
            
            drawLine(
                color = Color.White,
                start = Offset(center.x, size.height - strokeWidth / 2),
                end = needleEnd,
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
            
            drawCircle(
                color = Color.White,
                radius = 4.dp.toPx(),
                center = Offset(center.x, size.height - strokeWidth / 2)
            )
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

@Preview
@Composable
fun PreviewHalfDonutGauge() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        HalfDonutGauge(
            value = 0.644f,
            max = 1.0f,
            label = "HDI INDEX",
            modifier = Modifier.size(200.dp, 120.dp)
        )
    }
}
