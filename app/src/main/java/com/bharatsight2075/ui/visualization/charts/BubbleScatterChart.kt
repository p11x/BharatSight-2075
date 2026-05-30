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

data class BubbleData(val x: Float, val y: Float, val radius: Float, val label: String = "")

/**
 * C08. BubbleScatterChart
 * Circles at (x,y) positions, radius=value, gradient fill.
 */
@Composable
fun BubbleScatterChart(
    data: List<BubbleData>,
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.TEAL_PURPLE,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "BubbleAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        
        val minX = data.minOf { it.x }; val maxX = data.maxOf { it.x }
        val minY = data.minOf { it.y }; val maxY = data.maxOf { it.y }
        
        data.forEach { bubble ->
            val normX = (bubble.x - minX) / (maxX - minX).coerceAtLeast(0.1f)
            val normY = (bubble.y - minY) / (maxY - minY).coerceAtLeast(0.1f)
            
            val center = Offset(normX * width, height - (normY * height))
            val radius = bubble.radius * 20.dp.toPx() * currentProgress

            // Pass 1: Outer Glow
            drawCircle(
                brush = brush,
                radius = radius + 4.dp.toPx(),
                center = center,
                alpha = 0.2f
            )
            
            // Pass 2: Main Bubble
            drawCircle(
                brush = brush,
                radius = radius,
                center = center,
                alpha = 0.7f
            )
            
            // Pass 3: Core highlight
            drawCircle(
                color = Color.White.copy(alpha = 0.3f),
                radius = radius * 0.4f,
                center = center.copy(x = center.x - radius * 0.2f, y = center.y - radius * 0.2f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBubbleScatterChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        BubbleScatterChart(
            data = listOf(
                BubbleData(10f, 20f, 0.5f),
                BubbleData(30f, 40f, 0.8f),
                BubbleData(50f, 10f, 1.2f),
                BubbleData(70f, 80f, 0.4f),
                BubbleData(90f, 50f, 0.9f)
            ),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
