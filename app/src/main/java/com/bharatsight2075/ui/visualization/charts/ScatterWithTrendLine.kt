package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C17. ScatterWithTrendLine
 * Scatter dots + animated linear regression line.
 */
@Composable
fun ScatterWithTrendLine(
    data: List<Offset>,
    modifier: Modifier = Modifier,
    dotColor: Color = Color(0xFF00F5FF),
    lineColor: Color = Color(0xFFFF6B35),
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "ScatterAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        
        val minX = data.minOf { it.x }; val maxX = data.maxOf { it.x }
        val minY = data.minOf { it.y }; val maxY = data.maxOf { it.y }
        
        fun normalizeX(x: Float) = (x - minX) / (maxX - minX).coerceAtLeast(0.1f) * width
        fun normalizeY(y: Float) = height - (y - minY) / (maxY - minY).coerceAtLeast(0.1f) * height
        
        // Draw Dots
        data.forEach { point ->
            drawCircle(
                color = dotColor,
                radius = 4.dp.toPx() * currentProgress,
                center = Offset(normalizeX(point.x), normalizeY(point.y)),
                alpha = 0.6f
            )
        }
        
        // Simple Linear Regression for Trend Line
        val n = data.size
        val sumX = data.sumOf { it.x.toDouble() }.toFloat()
        val sumY = data.sumOf { it.y.toDouble() }.toFloat()
        val sumXY = data.sumOf { (it.x * it.y).toDouble() }.toFloat()
        val sumX2 = data.sumOf { (it.x * it.x).toDouble() }.toFloat()
        
        val slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX).coerceAtLeast(0.1f)
        val intercept = (sumY - slope * sumX) / n
        
        val startY = slope * minX + intercept
        val endY = slope * maxX + intercept
        
        val linePath = Path().apply {
            moveTo(0f, normalizeY(startY))
            lineTo(width * currentProgress, normalizeY(slope * (minX + (maxX - minX) * currentProgress) + intercept))
        }
        
        // Glow
        drawPath(
            path = linePath,
            color = lineColor.copy(alpha = 0.2f),
            style = Stroke(width = 6.dp.toPx())
        )
        
        // Sharp
        drawPath(
            path = linePath,
            color = lineColor,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Preview
@Composable
fun PreviewScatterWithTrendLine() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        ScatterWithTrendLine(
            data = listOf(
                Offset(10f, 20f), Offset(20f, 25f), Offset(30f, 40f),
                Offset(40f, 35f), Offset(50f, 50f), Offset(60f, 45f),
                Offset(70f, 70f), Offset(80f, 65f), Offset(90f, 85f)
            ),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
