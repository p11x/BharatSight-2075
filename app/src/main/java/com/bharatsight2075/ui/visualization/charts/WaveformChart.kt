package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.GradientFills
import kotlin.math.sin

/**
 * C11. WaveformChart
 */
@Composable
fun WaveformChart(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 120.dp,
    brush: Brush = Brush.verticalGradient(listOf(Color.Cyan, Color.Transparent)),
    animated: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "WaveAnim")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "Phase"
    )

    val primary = SciFiTheme.extendedColors.primary

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val width = size.width
        val height = size.height
        val centerY = height / 2
        val amplitude = height / 3
        
        val path = Path()
        val fillPath = Path()
        
        val points = (0..width.toInt() step 5).map { x ->
            val angle = (x / width) * 2 * Math.PI.toFloat() + phase
            Offset(x.toFloat(), centerY + sin(angle.toDouble()).toFloat() * amplitude)
        }
        
        if (points.isNotEmpty()) {
            path.moveTo(points[0].x, points[0].y)
            points.drop(1).forEach { path.lineTo(it.x, it.y) }
            
            fillPath.addPath(path)
            fillPath.lineTo(width, height)
            fillPath.lineTo(0f, height)
            fillPath.close()
            
            // Area Fill
            drawPath(fillPath, GradientFills.areaFill(primary, height))
            
            // Strokes
            drawPath(path, primary.copy(alpha = 0.2f), style = Stroke(8.dp.toPx()))
            drawPath(path, primary, style = Stroke(2.dp.toPx()))
        }
    }
}
