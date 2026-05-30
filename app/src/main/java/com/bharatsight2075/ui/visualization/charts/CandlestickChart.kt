package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

data class CandleData(val open: Float, val high: Float, val low: Float, val close: Float)

/**
 * C10. CandlestickChart
 * OHLC candles, green gradient up / red gradient down.
 */
@Composable
fun CandlestickChart(
    data: List<CandleData>,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "CandleAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val candleCount = data.size
        val candleWidth = (width / (candleCount * 1.5f))
        val spacing = (width - (candleWidth * candleCount)) / (candleCount + 1)
        
        val globalMax = data.maxOf { it.high }.coerceAtLeast(0.1f)
        val globalMin = data.minOf { it.low }
        val range = (globalMax - globalMin).coerceAtLeast(0.1f)

        data.forEachIndexed { index, candle ->
            val isUp = candle.close >= candle.open
            val color = if (isUp) Color(0xFF00E676) else Color(0xFFFF5252)
            
            val x = spacing + (index * (candleWidth + spacing))
            
            fun normalizeY(valY: Float): Float {
                return height - ((valY - globalMin) / range) * height * currentProgress
            }

            val highY = normalizeY(candle.high)
            val lowY = normalizeY(candle.low)
            val openY = normalizeY(candle.open)
            val closeY = normalizeY(candle.close)
            
            // Draw Wick
            drawLine(
                color = color.copy(alpha = 0.5f),
                start = Offset(x + candleWidth / 2, highY),
                end = Offset(x + candleWidth / 2, lowY),
                strokeWidth = 1.dp.toPx()
            )
            
            // Draw Body
            val top = minOf(openY, closeY)
            val bodyHeight = Math.abs(openY - closeY).coerceAtLeast(1.dp.toPx())
            
            drawRect(
                brush = Brush.verticalGradient(
                    listOf(color, color.copy(alpha = 0.6f))
                ),
                topLeft = Offset(x, top),
                size = Size(candleWidth, bodyHeight)
            )
            
            // Body Glow
            drawRect(
                color = color.copy(alpha = 0.15f),
                topLeft = Offset(x - 2.dp.toPx(), top - 2.dp.toPx()),
                size = Size(candleWidth + 4.dp.toPx(), bodyHeight + 4.dp.toPx())
            )
        }
    }
}

@Preview
@Composable
fun PreviewCandlestickChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        CandlestickChart(
            data = listOf(
                CandleData(50f, 70f, 40f, 60f),
                CandleData(60f, 80f, 55f, 75f),
                CandleData(75f, 78f, 60f, 65f),
                CandleData(65f, 90f, 60f, 85f),
                CandleData(85f, 88f, 70f, 72f),
                CandleData(72f, 75f, 50f, 55f)
            ),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
