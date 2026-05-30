package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.random.Random

data class Particle(
    val start: Offset,
    val end: Offset,
    val control: Offset,
    val startTime: Long,
    val duration: Long,
    val color: Color,
    val size: Float
)

/**
 * C21. ParticleFlowCanvas
 * Animated particles moving along bezier paths.
 */
@Composable
fun ParticleFlowCanvas(
    dataSize: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ParticleAnim")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "Time"
    )

    val colors = listOf(Color(0xFF00F5FF), Color(0xFFFF6B35), Color(0xFF39FF14), Color(0xFFFFD600))
    val particleCount = minOf(dataSize * 4, 80)
    
    val particles = remember(dataSize) {
        List(particleCount) {
            val startX = Random.nextFloat() * 1000f
            val startY = Random.nextFloat() * 1000f
            val endX = if (Random.nextBoolean()) 0f else 1000f
            val endY = Random.nextFloat() * 1000f
            
            Particle(
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                control = Offset(500f, 500f), // Gravity towards center
                startTime = Random.nextLong(2000),
                duration = 1500 + Random.nextLong(1500),
                color = colors.random(),
                size = 2f + Random.nextFloat() * 6f
            )
        }
    }

    Canvas(
        modifier = modifier.fillMaxSize().drawWithCache {
            onDrawBehind {
                val canvasWidth = size.width
                val canvasHeight = size.height
                
                particles.forEach { p ->
                    val t = ((time * 3000 + p.startTime) % p.duration) / p.duration.toFloat()
                    
                    // Bezier Quadratic formula: (1-t)^2*P0 + 2*(1-t)*t*P1 + t^2*P2
                    val x = (1-t)*(1-t)*(p.start.x / 1000f * canvasWidth) + 
                            2*(1-t)*t*(p.control.x / 1000f * canvasWidth) + 
                            t*t*(p.end.x / 1000f * canvasWidth)
                            
                    val y = (1-t)*(1-t)*(p.start.y / 1000f * canvasHeight) + 
                            2*(1-t)*t*(p.control.y / 1000f * canvasHeight) + 
                            t*t*(p.end.y / 1000f * canvasHeight)
                    
                    drawCircle(
                        color = p.color,
                        radius = p.size.dp.toPx() * (1 - t), // Shrink as they near end
                        center = Offset(x, y),
                        alpha = (1 - t) * 0.8f
                    )
                    
                    // Tail
                    drawCircle(
                        color = p.color,
                        radius = (p.size * 0.5f).dp.toPx(),
                        center = Offset(x, y),
                        alpha = 0.2f
                    )
                }
            }
        }
    ) {}
}

@Preview
@Composable
fun PreviewParticleFlowCanvas() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        ParticleFlowCanvas(
            dataSize = 15,
            modifier = Modifier.size(300.dp).background(Color.Black)
        )
    }
}
