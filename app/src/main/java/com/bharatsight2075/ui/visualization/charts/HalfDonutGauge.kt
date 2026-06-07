package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import java.util.Locale

@Composable
fun HalfDonutGauge(
    modifier: Modifier = Modifier,
    percent: Float = 0f,
    value: Float = 0f,
    max: Float = 1f,
    label: String = "PERCENT",
    unit: String = "INDEX",
    target: Float? = 85f,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val displayValue = if (value != 0f) value else percent * max
    val safeMax = max.coerceAtLeast(0.001f)
    
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

    val fillPercent = (displayValue / safeMax).coerceIn(0f, 1f)

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.BottomCenter) {
        Canvas(modifier = Modifier.fillMaxSize().drawWithCache {
            val b = Brush.horizontalGradient(listOf(primaryColor.copy(alpha = 0.4f), primaryColor))
            onDrawBehind {
                val radius = size.minDimension * 0.9f
                val strokeWidth = 24.dp.toPx()
                val arcSize = Size(radius, radius)
                val topLeft = Offset(center.x - radius / 2, size.height - radius / 2 - 16.dp.toPx())
                
                // Background arc
                drawArc(
                    color = Color.White.copy(alpha = 0.05f),
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                
                // Active arc with Gradient
                clipRect(bottom = size.height) {
                    drawArc(
                        brush = b,
                        startAngle = 180f,
                        sweepAngle = 180f * fillPercent * progress,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }
                
                // Double-pass glow
                drawArc(
                    color = primaryColor.copy(alpha = 0.2f * glowPulse * progress),
                    startAngle = 180f,
                    sweepAngle = 180f * fillPercent * progress,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth + 4.dp.toPx(), cap = StrokeCap.Round)
                )

                // Target Reference Line
                target?.let { t ->
                    val tPercent = (t / safeMax).coerceIn(0f, 1f)
                    val tAngle = Math.toRadians((180f + 180f * tPercent).toDouble())
                    val innerR = radius / 2 - strokeWidth / 2 - 4.dp.toPx()
                    val outerR = radius / 2 + strokeWidth / 2 + 4.dp.toPx()
                    
                    val p1 = Offset(
                        center.x + innerR * kotlin.math.cos(tAngle).toFloat(),
                        (topLeft.y + radius/2) + innerR * kotlin.math.sin(tAngle).toFloat()
                    )
                    val p2 = Offset(
                        center.x + outerR * kotlin.math.cos(tAngle).toFloat(),
                        (topLeft.y + radius/2) + outerR * kotlin.math.sin(tAngle).toFloat()
                    )
                    drawLine(Color.White.copy(0.6f), p1, p2, 2.dp.toPx())
                }
                
                // End Marker
                val currentAngle = Math.toRadians((180f + 180f * fillPercent * progress).toDouble())
                val markerPos = Offset(
                    center.x + (radius/2) * kotlin.math.cos(currentAngle).toFloat(),
                    (topLeft.y + radius/2) + (radius/2) * kotlin.math.sin(currentAngle).toFloat()
                )
                drawCircle(Color.White, 6.dp.toPx() * progress, markerPos)
                drawCircle(primaryColor, 3.dp.toPx() * progress, markerPos)
            }
        }) { }
        
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = String.format(Locale.getDefault(), "%.1f", displayValue),
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = unit.uppercase(),
                style = SciFiTheme.typography.ChartCaption.copy(fontSize = 10.sp),
                color = primaryColor.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                style = SciFiTheme.typography.BodyMono.copy(fontSize = 9.sp, color = Color.White.copy(0.4f))
            )
        }
    }
}
