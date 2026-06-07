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

/**
 * Data model for a single candlestick
 */
data class CandleData(val high: Float, val low: Float, val open: Float, val close: Float)

@Composable
fun CandlestickChart(
    data: List<CandleData> = emptyList(),
    ohlcData: List<List<Float>> = emptyList(),
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    bullColor: Color = Color(0xFF00E676),
    bearColor: Color = Color(0xFFFF5252)
) {
    @Suppress("UNCHECKED_CAST")
    val rawData = data.takeIf { it.isNotEmpty() } 
        ?: ohlcData.map { CandleData(it.getOrElse(0){0f}, it.getOrElse(1){0f}, it.getOrElse(2){0f}, it.getOrElse(3){0f}) }.takeIf { it.isNotEmpty() }
        ?: (ChartMockData.generateMockData(ChartType.CANDLE) as List<CandleData>)
    
    val maxVal = rawData.maxOfOrNull { it.high }?.coerceAtLeast(0.001f) ?: 1f
    val minVal = rawData.minOfOrNull { it.low } ?: 0f
    val range = (maxVal - minVal).coerceAtLeast(0.001f)

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

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        onDrawBehind {
            val paddingR = 48.dp.toPx()
            val paddingB = 24.dp.toPx()
            val chartW = size.width - paddingR
            val chartH = size.height - paddingB
            
            val count = rawData.size
            val candleWidth = (chartW / count.coerceAtLeast(1) * 0.7f).coerceAtLeast(4f)
            val gap = chartW / count.coerceAtLeast(1) * 0.3f

            rawData.forEachIndexed { index, candle ->
                val x = index * (candleWidth + gap) + gap / 2
                val isBull = candle.close >= candle.open
                val color = if (isBull) bullColor else bearColor
                
                val highY = chartH - ((candle.high - minVal) / range * chartH)
                val lowY = chartH - ((candle.low - minVal) / range * chartH)
                val openY = chartH - ((candle.open - minVal) / range * chartH)
                val closeY = chartH - ((candle.close - minVal) / range * chartH)
                
                // Body Animation
                val bodyTop = minOf(openY, closeY)
                val bodyBottom = maxOf(openY, closeY)
                val targetBodyHeight = (bodyBottom - bodyTop).coerceAtLeast(2f)
                val currentBodyHeight = targetBodyHeight * progress
                val currentTopY = bodyTop + (targetBodyHeight - currentBodyHeight) / 2
                
                // Wick
                drawLine(color.copy(alpha = 0.6f * progress), Offset(x + candleWidth / 2, highY), Offset(x + candleWidth / 2, lowY), strokeWidth = 1.dp.toPx())
                
                // Candle Gradient Body
                val brush = Brush.verticalGradient(listOf(color, color.copy(alpha = 0.6f)))
                drawRect(
                    brush = brush,
                    topLeft = Offset(x, currentTopY),
                    size = Size(candleWidth, currentBodyHeight)
                )
                
                // Double-pass glow
                drawRect(
                    color = color.copy(alpha = 0.2f * glowPulse * progress),
                    topLeft = Offset(x, currentTopY),
                    size = Size(candleWidth, currentBodyHeight),
                    style = Stroke(width = 1.5.dp.toPx() * 3f)
                )
                
                // Volume bars below (dummy)
                val volH = (Random(index).nextFloat() * 0.2f * chartH) * progress
                drawRect(
                    color = color.copy(0.2f),
                    topLeft = Offset(x, chartH - volH),
                    size = Size(candleWidth, volH)
                )

                // Date Labels (Bottom)
                if (index % 4 == 0) {
                    val dateResult = textMeasurer.measure("D${index+1}", labelStyle)
                    drawText(dateResult, topLeft = Offset(x, chartH + 4.dp.toPx()))
                }
            }

            // Price Scale (Right)
            val maxResult = textMeasurer.measure(String.format(Locale.getDefault(), "%.0f", maxVal), labelStyle)
            val minResult = textMeasurer.measure(String.format(Locale.getDefault(), "%.0f", minVal), labelStyle)
            drawText(maxResult, topLeft = Offset(chartW + 4.dp.toPx(), 0f))
            drawText(minResult, topLeft = Offset(chartW + 4.dp.toPx(), chartH - 12.dp.toPx()))
            
            // Grid lines
            drawLine(Color.White.copy(0.1f), Offset(0f, chartH), Offset(chartW, chartH), 1.dp.toPx())
            drawLine(Color.White.copy(0.1f), Offset(chartW, 0f), Offset(chartW, chartH), 1.dp.toPx())
        }
    }) { }
}

private fun Random(seed: Int) = kotlin.random.Random(seed)
