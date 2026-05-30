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
 * C02. MultiLineChart
 * Up to 5 overlapping lines, each with its own gradient color + legend pills below.
 */
@Composable
fun MultiLineChart(
    data: List<List<Float>>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "MultiLineAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        if (data.isEmpty()) return@Canvas
        
        val width = size.width
        val height = size.height
        val maxPoints = data.maxOf { it.size }
        val spacing = width / (maxPoints - 1)
        val globalMax = data.flatten().maxOrNull()?.coerceAtLeast(0.1f) ?: 1f
        
        data.forEachIndexed { lineIndex, pointsData ->
            val strokeColor = colors.getOrElse(lineIndex) { Color.Cyan }
            
            val path = Path().apply {
                pointsData.forEachIndexed { i, value ->
                    val x = i * spacing
                    val y = height - (value / globalMax) * height * currentProgress
                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
            }
            
            // Pass 1: Glow
            drawPath(
                path = path,
                color = strokeColor.copy(alpha = 0.2f),
                style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
            
            // Pass 2: Sharp
            drawPath(
                path = path,
                color = strokeColor,
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}

@Preview
@Composable
fun PreviewMultiLineChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        MultiLineChart(
            data = listOf(
                listOf(20f, 40f, 30f, 60f, 50f, 80f),
                listOf(10f, 20f, 25f, 45f, 40f, 60f),
                listOf(50f, 30f, 40f, 20f, 35f, 15f)
            ),
            colors = listOf(Color(0xFF00F5FF), Color(0xFFFF6B35), Color(0xFF39FF14)),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
