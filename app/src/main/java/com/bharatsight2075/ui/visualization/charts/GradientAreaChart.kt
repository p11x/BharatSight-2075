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
 * C01. GradientAreaChart
 * Filled area under line, multi-stop vertical gradient fill, glow stroke, dot markers at peaks.
 */
@Composable
fun GradientAreaChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.TEAL_PURPLE,
    strokeColor: Color = Color(0xFF00F5FF),
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "ChartAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        if (data.isEmpty()) return@Canvas
        
        val width = size.width
        val height = size.height
        val spacing = width / (data.size - 1)
        val maxVal = data.maxOrNull()?.coerceAtLeast(0.1f) ?: 1f
        
        val points = data.mapIndexed { index, value ->
            Offset(index * spacing, height - (value / maxVal) * height * currentProgress)
        }
        
        val strokePath = Path().apply {
            moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }
        
        val fillPath = Path().apply {
            addPath(strokePath)
            lineTo(points.last().x, height)
            lineTo(points.first().x, height)
            close()
        }
        
        // 1. Area Fill
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(strokeColor.copy(alpha = 0.6f), Color.Transparent)
            )
        )
        
        // 2. Glow Stroke (Pass 1)
        drawPath(
            path = strokePath,
            color = strokeColor.copy(alpha = 0.2f),
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        
        // 3. Sharp Stroke (Pass 2)
        drawPath(
            path = strokePath,
            color = strokeColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        
        // 4. Dot markers at peaks
        points.forEach { point ->
            drawCircle(
                color = strokeColor,
                radius = 3.dp.toPx(),
                center = point
            )
            drawCircle(
                color = strokeColor.copy(alpha = 0.3f),
                radius = 6.dp.toPx(),
                center = point
            )
        }
    }
}

@Preview
@Composable
fun PreviewGradientAreaChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        GradientAreaChart(
            data = listOf(10f, 25f, 15f, 40f, 30f, 50f, 45f, 70f, 60f, 85f, 80f, 100f),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
