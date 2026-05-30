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

/**
 * C19. WaterfallBarChart
 * Running total bars, connector lines.
 */
@Composable
fun WaterfallBarChart(
    data: List<Float>, // Deltas (can be negative)
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "WaterfallAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val barCount = data.size
        val barWidth = (width / (barCount * 1.5f))
        val spacing = (width - (barWidth * barCount)) / (barCount + 1)
        
        // Calculate running totals
        val runningTotals = FloatArray(barCount + 1)
        for (i in 0 until barCount) {
            runningTotals[i + 1] = runningTotals[i] + data[i]
        }
        
        val maxVal = runningTotals.maxOf { Math.abs(it) }.coerceAtLeast(0.1f)
        val centerY = height / 2 // Simplified baseline
        
        runningTotals.drop(1).forEachIndexed { index, total ->
            val prevTotal = runningTotals[index]
            val x = spacing + (index * (barWidth + spacing))
            
            val yPrev = centerY - (prevTotal / maxVal) * (height / 2)
            val yCurr = centerY - (total / maxVal) * (height / 2)
            
            val isPositive = data[index] >= 0
            val color = if (isPositive) Color(0xFF00E676) else Color(0xFFFF5252)
            
            // Draw Connector
            if (index > 0) {
                drawLine(
                    color = Color.White.copy(alpha = 0.3f),
                    start = Offset(x - spacing, yPrev),
                    end = Offset(x, yPrev),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                )
            }
            
            // Draw Bar
            val barHeight = Math.abs(yPrev - yCurr) * currentProgress
            val barTop = if (isPositive) yCurr else yPrev
            
            drawRect(
                brush = Brush.verticalGradient(listOf(color, color.copy(alpha = 0.6f))),
                topLeft = Offset(x, barTop),
                size = Size(barWidth, barHeight)
            )
        }
    }
}

@Preview
@Composable
fun PreviewWaterfallBarChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        WaterfallBarChart(
            data = listOf(40f, 20f, -30f, 50f, -10f, 25f),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
