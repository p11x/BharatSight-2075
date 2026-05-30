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
 * C07. StackedAreaChart
 * Multiple filled areas stacked, each semi-transparent.
 */
@Composable
fun StackedAreaChart(
    data: List<List<Float>>,
    brushes: List<Brush>,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "StackedAreaAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val pointCount = data.firstOrNull()?.size ?: 0
        if (pointCount < 2) return@Canvas
        
        val spacing = width / (pointCount - 1)
        
        // Calculate stacked values
        val stackedData = MutableList(data.size) { FloatArray(pointCount) }
        for (i in 0 until pointCount) {
            var currentStack = 0f
            for (j in data.indices) {
                currentStack += data[j][i]
                stackedData[j][i] = currentStack
            }
        }
        
        val globalMax = stackedData.last().maxOrNull()?.coerceAtLeast(0.1f) ?: 1f

        // Draw from top layer down to correctly overlap
        for (j in data.size - 1 downTo 0) {
            val fillPath = Path()
            val strokePath = Path()
            val brush = brushes.getOrElse(j) { GradPalette.TEAL_PURPLE }
            
            for (i in 0 until pointCount) {
                val x = i * spacing
                val y = height - (stackedData[j][i] / globalMax) * height * currentProgress
                
                if (i == 0) {
                    fillPath.moveTo(x, y)
                    strokePath.moveTo(x, y)
                } else {
                    fillPath.lineTo(x, y)
                    strokePath.lineTo(x, y)
                }
            }
            
            // Close fill path to bottom
            fillPath.lineTo(width, height)
            fillPath.lineTo(0f, height)
            fillPath.close()
            
            // Draw Area
            drawPath(path = fillPath, brush = brush, alpha = 0.5f)
            
            // Draw Glow Stroke
            drawPath(
                path = strokePath,
                brush = brush,
                style = Stroke(width = 4.dp.toPx()),
                alpha = 0.3f
            )
            
            // Draw Sharp Stroke
            drawPath(
                path = strokePath,
                brush = brush,
                style = Stroke(width = 1.5.dp.toPx())
            )
        }
    }
}

@Preview
@Composable
fun PreviewStackedAreaChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        StackedAreaChart(
            data = listOf(
                listOf(30f, 40f, 35f, 50f, 45f),
                listOf(20f, 25f, 30f, 20f, 35f),
                listOf(15f, 10f, 20f, 15f, 10f)
            ),
            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
