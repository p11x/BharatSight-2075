package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * C18. PolarAreaChart
 * Pizza-slice sectors of varying radius, gradient fill.
 */
@Composable
fun PolarAreaChart(
    data: List<Float>,
    brushes: List<Brush>,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "PolarAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        val maxRadius = size.minDimension / 2 * 0.9f
        val angleStep = 360f / data.size
        val maxVal = data.maxOrNull()?.coerceAtLeast(0.1f) ?: 1f
        
        var currentAngle = -90f
        
        data.forEachIndexed { index, value ->
            val radius = (value / maxVal) * maxRadius * currentProgress
            val brush = brushes.getOrElse(index) { GradPalette.TEAL_PURPLE }
            
            drawArc(
                brush = brush,
                startAngle = currentAngle,
                sweepAngle = angleStep,
                useCenter = true,
                style = androidx.compose.ui.graphics.drawscope.Fill,
                alpha = 0.6f
            )
            
            drawArc(
                brush = brush,
                startAngle = currentAngle,
                sweepAngle = angleStep,
                useCenter = true,
                style = Stroke(width = 1.dp.toPx())
            )
            
            currentAngle += angleStep
        }
    }
}

@Preview
@Composable
fun PreviewPolarAreaChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        PolarAreaChart(
            data = listOf(80f, 60f, 90f, 40f, 70f, 50f),
            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE, GradPalette.PURPLE_BLUE, GradPalette.PINK_PURPLE),
            modifier = Modifier.size(300.dp)
        )
    }
}
