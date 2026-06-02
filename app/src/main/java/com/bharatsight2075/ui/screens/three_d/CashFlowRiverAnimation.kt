package com.bharatsight2075.ui.screens.three_d

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.sin

data class Particle(
    var x: Float,
    var y: Float,
    val speed: Float,
    val amplitude: Float,
    val offset: Float
)

@Composable
fun CashFlowRiverAnimation(modifier: Modifier = Modifier) {
    val particles = remember {
        List(100) {
            Particle(
                x = (0..1000).random().toFloat(),
                y = 0f,
                speed = (2..5).random().toFloat(),
                amplitude = (20..100).random().toFloat(),
                offset = (0..360).random().toFloat()
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "RiverFlow")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "Time"
    )

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Main River Path Glow
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        RetroDarkColors.NeonCyan.copy(alpha = 0.05f),
                        RetroDarkColors.NeonCyan.copy(alpha = 0.2f),
                        RetroDarkColors.NeonCyan.copy(alpha = 0.05f)
                    )
                ),
                size = size
            )

            particles.forEach { p ->
                val progress = (time * p.speed + p.offset) % 1000f
                val yPos = (progress / 1000f) * height
                val xPos = (width / 2) + sin(Math.toRadians((yPos + time * 10).toDouble())).toFloat() * p.amplitude

                drawCircle(
                    color = RetroDarkColors.NeonCyan,
                    radius = 2f,
                    center = Offset(xPos, yPos),
                    alpha = 0.6f
                )
                
                // Connecting lines to simulate "flow"
                if (progress > 50) {
                   drawLine(
                       color = RetroDarkColors.NeonCyan.copy(alpha = 0.2f),
                       start = Offset(xPos, yPos),
                       end = Offset(xPos, yPos - 40f),
                       strokeWidth = 1f
                   )
                }
            }
        }
    }
}
