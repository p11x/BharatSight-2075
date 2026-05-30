package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C05. GradientBarChart
 * Vertical bars with per-bar gradient fill, rounded top caps, value label above.
 */
@Composable
fun GradientBarChart(
    data: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.GREEN_TEAL,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "BarAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val extendedColors = SciFiTheme.extendedColors

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val barCount = data.size
        val barWidth = (width / (barCount * 1.5f))
        val spacing = (width - (barWidth * barCount)) / (barCount + 1)
        val maxVal = data.maxOrNull()?.coerceAtLeast(0.1f) ?: 1f

        data.forEachIndexed { index, value ->
            val barHeight = (value / maxVal) * height * currentProgress
            val x = spacing + (index * (barWidth + spacing))
            val y = height - barHeight
            
            // Draw Bar with Gradient
            drawRoundRect(
                brush = brush,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
            
            // Subtle glow highlight
            drawRect(
                color = Color.White.copy(alpha = 0.1f),
                topLeft = Offset(x + 2.dp.toPx(), y + 2.dp.toPx()),
                size = Size(barWidth / 4, barHeight - 4.dp.toPx())
            )
        }
    }
}

@Preview
@Composable
fun PreviewGradientBarChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        GradientBarChart(
            data = listOf(45f, 60f, 30f, 85f, 50f, 75f),
            labels = listOf("A", "B", "C", "D", "E", "F"),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
