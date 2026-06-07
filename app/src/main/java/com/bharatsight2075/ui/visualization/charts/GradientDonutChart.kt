package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GradientDonutChart(
    modifier: Modifier = Modifier,
    values: List<Float> = emptyList(),
    brushes: List<Brush> = emptyList(),
    label: String = "",
    unit: String = "SHARE",
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).take(4)
    val total = safeData.sum().coerceAtLeast(0.001f)
    
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

    val baseColors = listOf(
        primaryColor,
        Color(0xFF00E676),
        Color(0xFFFFD600),
        Color(0xFFFF6B35),
        Color(0xFF7C4DFF),
        Color(0xFF00B0FF)
    )

    val textMeasurer = rememberTextMeasurer()
    val percStyle = TextStyle(
        color = Color.White.copy(0.7f),
        fontSize = 8.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
    )

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2 * 0.75f
            val strokeWidth = 28.dp.toPx()
            val arcSize = Size(radius * 2, radius * 2)
            val topLeft = Offset(center.x - radius, center.y - radius)
            
            var startAngle = -90f
            safeData.forEachIndexed { i, value ->
                val targetSweep = (value / total) * 360f
                val sweepAngle = targetSweep * progress
                val color = baseColors[i % baseColors.size]
                
                // Segment Gradient
                val segmentBrush = Brush.sweepGradient(
                    0.0f to color.copy(alpha = 0.6f),
                    1.0f to color,
                    center = center
                )
                
                // Double-pass glow
                drawArc(
                    color = color.copy(alpha = 0.15f * glowPulse * progress),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth + 6.dp.toPx())
                )
                
                drawArc(
                    brush = segmentBrush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth)
                )

                // Divider
                drawArc(
                    color = Color.Black.copy(0.5f),
                    startAngle = startAngle - 0.5f,
                    sweepAngle = 1f,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth + 2.dp.toPx())
                )

                // Percentage Label inside segment
                if (progress > 0.8f && targetSweep > 20f) {
                    val midAngle = Math.toRadians((startAngle + targetSweep / 2).toDouble())
                    val labelR = radius
                    val labelPos = Offset(
                        center.x + labelR * cos(midAngle).toFloat(),
                        center.y + labelR * sin(midAngle).toFloat()
                    )
                    val percText = "${(value / total * 100).toInt()}%"
                    val result = textMeasurer.measure(percText, percStyle)
                    drawText(result, topLeft = Offset(labelPos.x - result.size.width / 2, labelPos.y - result.size.height / 2))
                }
                
                startAngle += targetSweep
            }
            
            // Outer thin border
            drawCircle(
                color = Color.White.copy(0.1f),
                radius = radius + strokeWidth / 2 + 1.dp.toPx(),
                center = center,
                style = Stroke(1.dp.toPx())
            )
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label.uppercase(),
                style = SciFiTheme.typography.ChartCaption.copy(fontSize = 10.sp),
                color = Color.White.copy(0.5f)
            )
            Text(
                text = "100", // dummy total
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = primaryColor
            )
            Text(
                text = unit,
                style = SciFiTheme.typography.ChartCaption.copy(fontSize = 8.sp),
                color = Color.White.copy(0.3f)
            )
        }
    }
}
