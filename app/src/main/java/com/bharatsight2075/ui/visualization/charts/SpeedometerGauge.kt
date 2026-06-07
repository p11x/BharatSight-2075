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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SpeedometerGauge(
    value: Float,
    modifier: Modifier = Modifier,
    max: Float = 1f,
    label: String = "INDEX",
    unit: String = "PERCENT",
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    animated: Boolean = true,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
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
    
    val fillPercent = (value / safeMax).coerceIn(0f, 1f)
    val currentProgress = if (animated) fillPercent * progress else fillPercent
    val colors = SciFiTheme.extendedColors
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        color = Color.White.copy(0.4f),
        fontSize = 8.sp,
        fontFamily = FontFamily.Monospace
    )

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize().drawWithCache {
            onDrawBehind {
                val strokeWidth = 12.dp.toPx()
                val diameter = size.minDimension - 60.dp.toPx()
                val arcSize = Size(diameter, diameter)
                val topLeft = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)
                
                val startAngle = 135f
                val totalSweep = 270f
                
                // Background Track
                drawArc(
                    color = Color.White.copy(alpha = 0.05f),
                    startAngle = startAngle,
                    sweepAngle = totalSweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                
                // Colored Zones
                // Green: 0-0.4
                drawArc(
                    color = Color(0xFF00E676).copy(alpha = 0.3f),
                    startAngle = startAngle,
                    sweepAngle = totalSweep * 0.4f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                // Yellow: 0.4-0.7
                drawArc(
                    color = Color(0xFFFFD600).copy(alpha = 0.3f),
                    startAngle = startAngle + totalSweep * 0.4f,
                    sweepAngle = totalSweep * 0.3f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                // Red: 0.7-1.0
                drawArc(
                    color = Color(0xFFFF5252).copy(alpha = 0.3f),
                    startAngle = startAngle + totalSweep * 0.7f,
                    sweepAngle = totalSweep * 0.3f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                
                // Needle
                val angle = startAngle + (totalSweep * currentProgress)
                val angleRad = Math.toRadians(angle.toDouble())
                val needleLen = diameter / 2 - 4.dp.toPx()
                val needleEnd = Offset(
                    center.x + needleLen * cos(angleRad).toFloat(),
                    center.y + needleLen * sin(angleRad).toFloat()
                )
                
                // Glow behind needle
                drawLine(Color.White.copy(alpha = 0.2f * glowPulse), center, needleEnd, 8.dp.toPx(), cap = StrokeCap.Round)
                drawLine(Color.White, center, needleEnd, 2.5.dp.toPx(), cap = StrokeCap.Round)
                
                // Orange Tip Dot
                drawCircle(Color(0xFFFF6B35), 4.dp.toPx(), needleEnd)
                drawCircle(Color.White, 6.dp.toPx(), center)
                drawCircle(primaryColor, 3.dp.toPx(), center)

                // Min/Max Labels
                val minResult = textMeasurer.measure("0", labelStyle)
                val maxResult = textMeasurer.measure(max.toInt().toString(), labelStyle)
                
                val minAngle = Math.toRadians(startAngle.toDouble())
                val maxAngle = Math.toRadians((startAngle + totalSweep).toDouble())
                val labelR = diameter / 2 + 15.dp.toPx()
                
                val minPos = Offset(
                    center.x + labelR * cos(minAngle).toFloat() - minResult.size.width / 2,
                    center.y + labelR * sin(minAngle).toFloat() - minResult.size.height / 2
                )
                val maxPos = Offset(
                    center.x + labelR * cos(maxAngle).toFloat() - maxResult.size.width / 2,
                    center.y + labelR * sin(maxAngle).toFloat() - maxResult.size.height / 2
                )
                
                drawText(minResult, topLeft = minPos)
                drawText(maxResult, topLeft = maxPos)
            }
        }) { }
        
        Column(
            modifier = Modifier.padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = String.format(java.util.Locale.getDefault(), "%.1f", value),
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = unit.uppercase(),
                style = SciFiTheme.typography.ChartCaption,
                color = colors.textSecondary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                style = SciFiTheme.typography.BodyMono.copy(fontSize = 10.sp, color = primaryColor.copy(0.6f))
            )
        }
    }
}
