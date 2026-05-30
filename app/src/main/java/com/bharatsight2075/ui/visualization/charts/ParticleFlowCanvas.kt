package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.random.Random

data class Particle(
    val start: Offset,
    val end: Offset,
    val control: Offset,
    val startTime: Long,
    val duration: Long,
    val color: Color
)

/**
 * C21. ParticleFlowCanvas
 */
@Composable
fun ParticleFlowCanvas(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 240.dp,
    particleCount: Int = 40
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Particles")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)),
        label = "Time"
    )

    val colors = listOf(SciFiTheme.extendedColors.primary, SciFiTheme.extendedColors.accent, Color(0xFF39FF14))
    
    val particles = remember(particleCount) {
        List(particleCount) {
            val start = Offset(0f, Random.nextFloat())
            val end = Offset(1000f, Random.nextFloat())
            Particle(
                start = start,
                end = end,
                control = Offset(500f, Random.nextFloat() * 1000f),
                startTime = Random.nextLong(2000),
                duration = 1500 + Random.nextLong(1000),
                color = colors.random()
            )
        }
    }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        particles.forEach { p ->
            val t = ((time * 2000 + p.startTime) % p.duration) / p.duration.toFloat()
            
            // Bezier
            val x = (1-t)*(1-t)*(p.start.x / 1000f * size.width) + 2*(1-t)*t*(p.control.x / 1000f * size.width) + t*t*(p.end.x / 1000f * size.width)
            val y = (1-t)*(1-t)*(p.start.y * size.height) + 2*(1-t)*t*(p.control.y * size.height) + t*t*(p.end.y * size.height)
            
            drawCircle(p.color, 3.dp.toPx() * (1-t), Offset(x, y), alpha = (1-t) * 0.8f)
            drawCircle(p.color.copy(alpha = 0.2f), 8.dp.toPx() * (1-t), Offset(x, y))
        }
    }
}
