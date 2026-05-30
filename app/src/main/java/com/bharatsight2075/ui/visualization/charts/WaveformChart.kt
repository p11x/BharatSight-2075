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
import kotlin.math.sin

/**
 * C11. WaveformChart
 * Sinusoidal smooth wave, animated phase shift.
 */
@Composable
fun WaveformChart(
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.CYAN_WHITE,
    strokeColor: Color = Color(0xFF00F5FF),
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

    Canvas(modifier = modifier) {
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
        
        path.moveTo(points[0].x, points[0].y)
        points.drop(1).forEach { path.lineTo(it.x, it.y) }
        
        fillPath.addPath(path)
        fillPath.lineTo(width, height)
        fillPath.lineTo(0f, height)
        fillPath.close()
        
        // Area Fill
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                listOf(strokeColor.copy(alpha = 0.3f), Color.Transparent)
            )
        )
        
        // Glow Stroke
        drawPath(
            path = path,
            color = strokeColor.copy(alpha = 0.2f),
            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
        )
        
        // Sharp Stroke
        drawPath(
            path = path,
            color = strokeColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview
@Composable
fun PreviewWaveformChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        WaveformChart(
            modifier = Modifier.fillMaxWidth().height(150.dp)
        )
    }
}
