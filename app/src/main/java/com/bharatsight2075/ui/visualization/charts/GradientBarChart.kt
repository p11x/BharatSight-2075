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
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import java.util.Locale

@Composable
fun GradientBarChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    labels: List<String> = emptyList(),
    colors: Brush? = null,
    chartHeight: androidx.compose.ui.unit.Dp = 160.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).take(8)
    val maxVal = safeData.maxOfOrNull { kotlin.math.abs(it) }?.coerceAtLeast(0.001f) ?: 1f
    val barBrush = colors ?: Brush.verticalGradient(listOf(primaryColor, primaryColor.copy(alpha = 0.4f)))
    val barColor = primaryColor // Fallback for glow/stroke


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

    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        color = Color.White.copy(0.4f),
        fontSize = 8.sp,
        fontFamily = FontFamily.Monospace
    )
    val valueStyle = TextStyle(
        color = primaryColor.copy(0.9f),
        fontSize = 9.sp,
        fontFamily = FontFamily.Monospace
    )

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = barBrush
        onDrawBehind {
            val count = if (labels.isNotEmpty()) labels.size else safeData.size
            val paddingB = 24.dp.toPx()
            val chartH = size.height - paddingB
            val barWidthFull = size.width / count.coerceAtLeast(1)
            val barWidth = barWidthFull * 0.7f
            val gap = barWidthFull * 0.3f
            
            safeData.take(count).forEachIndexed { i, value ->
                val h = (value / maxVal) * chartH * progress
                val x = i * barWidthFull + gap / 2
                
                // Rounded top caps only
                val rect = RoundRect(
                    left = x,
                    top = chartH - h,
                    right = x + barWidth,
                    bottom = chartH,
                    topLeftCornerRadius = CornerRadius(barWidth / 2),
                    topRightCornerRadius = CornerRadius(barWidth / 2)
                )
                val path = Path().apply { addRoundRect(rect) }
                
                drawPath(path, b)
                
                // Double-pass glow
                drawPath(
                    path = path,
                    color = barColor.copy(alpha = 0.2f * glowPulse),
                    style = Stroke(width = 1.dp.toPx() * 3.5f)
                )
                drawPath(
                    path = path,
                    color = barColor.copy(alpha = 0.9f),
                    style = Stroke(width = 1.dp.toPx())
                )
                
                // Value Label above
                val valText = String.format(Locale.getDefault(), "%.1f", value)
                val valResult = textMeasurer.measure(valText, valueStyle)
                drawText(valResult, topLeft = Offset(x + (barWidth - valResult.size.width) / 2, chartH - h - 14.dp.toPx()))
                
                // Category Label below
                val catText = labels.getOrNull(i) ?: "Q${i+1}"
                val catResult = textMeasurer.measure(catText, labelStyle)
                drawText(catResult, topLeft = Offset(x + (barWidth - catResult.size.width) / 2, chartH + 4.dp.toPx()))
            }
            
            // Base line
            drawLine(Color.White.copy(0.1f), Offset(0f, chartH), Offset(size.width, chartH), 1.dp.toPx())
        }
    }) { }
}
