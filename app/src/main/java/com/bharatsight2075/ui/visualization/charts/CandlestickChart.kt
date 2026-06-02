package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

data class CandleData(val open: Float, val high: Float, val low: Float, val close: Float)

/**
 * C10. CandlestickChart
 */
@Composable
fun CandlestickChart(
    data: List<CandleData>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.CANDLE) as List<CandleData> }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "CandleAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors
    val greenGrad = Color(0xFF00E676)
    val redGrad = Color(0xFFFF5252)

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val count = safeData.size.coerceAtLeast(1)
        val totalGap = size.width * 0.3f
        val barWidth = (size.width - totalGap) / count
        val gap = totalGap / (count + 1)
        
        val maxVal = safeData.maxOf { it.high }.coerceAtLeast(0.001f)
        val minVal = safeData.minOf { it.low }
        val range = (maxVal - minVal).coerceAtLeast(0.001f)

        safeData.forEachIndexed { i, candle ->
            val x = gap + i * (barWidth + gap) + barWidth / 2
            
            val topY = size.height - (maxOf(candle.open, candle.close) - minVal) / range * size.height * currentProgress
            val bottomY = size.height - (minOf(candle.open, candle.close) - minVal) / range * size.height * currentProgress
            val bodyH = (bottomY - topY).coerceAtLeast(2f)
            
            val wickTopY = size.height - (candle.high - minVal) / range * size.height * currentProgress
            val wickBottomY = size.height - (candle.low - minVal) / range * size.height * currentProgress
            
            val isUp = candle.close >= candle.open
            val bodyColor = if (isUp) greenGrad else redGrad
            
            // Draw Wick
            drawLine(bodyColor.copy(alpha = 0.5f), Offset(x, wickTopY), Offset(x, wickBottomY), 1.dp.toPx())
            
            // Draw Body
            drawRect(bodyColor, Offset(x - barWidth / 2, topY), Size(barWidth, bodyH))
            
            // Body Glow
            drawRect(bodyColor.copy(alpha = 0.15f), Offset(x - barWidth / 2 - 2.dp.toPx(), topY - 2.dp.toPx()), Size(barWidth + 4.dp.toPx(), bodyH + 4.dp.toPx()))
        }
    }
}
