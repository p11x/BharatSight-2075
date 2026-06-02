package com.bharatsight2075.ui.components.three_d

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun GDP3DLandscapeView(
    modifier: Modifier = Modifier,
    sectorData: List<List<Float>> = List(10) { List(10) { 0.2f + Random.nextFloat() * 0.8f } }
) {
    var scale by remember { mutableStateOf(1f) }
    var rotationX by remember { mutableStateOf(45f) }
    var rotationY by remember { mutableStateOf(45f) }

    val infiniteTransition = rememberInfiniteTransition(label = "LandscapeGlow")
    val glowIntensity by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Glow"
    )

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    scale *= zoom
                    rotationY += pan.x / 5f
                    rotationX -= pan.y / 5f
                }
            }
    ) {
        val rows = sectorData.size
        val cols = sectorData[0].size
        val cellWidth = size.width / (cols + 2)
        val cellHeight = size.height / (rows + 2)

        fun project(x: Float, y: Float, z: Float): Offset {
            val radX = Math.toRadians(rotationX.toDouble())
            val radY = Math.toRadians(rotationY.toDouble())

            // Isometric projection logic
            val xRot = x * cos(radY) - z * sin(radY)
            val zRot = x * sin(radY) + z * cos(radY)
            val yRot = y * cos(radX) - zRot * sin(radX)
            
            val finalX = (xRot.toFloat() * scale) + size.width / 2
            val finalY = (yRot.toFloat() * scale) + size.height / 2
            return Offset(finalX, finalY)
        }

        // Draw the wireframe grid
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val gdp = sectorData[r][c]
                val elevation = gdp * 300f

                val p1 = project((c - cols / 2f) * cellWidth, -elevation, (r - rows / 2f) * cellHeight)
                
                // Draw connections to neighbors
                if (c < cols - 1) {
                    val gdpNext = sectorData[r][c + 1]
                    val p2 = project((c + 1 - cols / 2f) * cellWidth, -gdpNext * 300f, (r - rows / 2f) * cellHeight)
                    drawLine(
                        color = if (gdp > 0.7f) RetroDarkColors.NeonCyan else RetroDarkColors.NeonOrange,
                        start = p1,
                        end = p2,
                        strokeWidth = 2f,
                        alpha = glowIntensity
                    )
                }
                
                if (r < rows - 1) {
                    val gdpNext = sectorData[r + 1][c]
                    val p3 = project((c - cols / 2f) * cellWidth, -gdpNext * 300f, (r + 1 - rows / 2f) * cellHeight)
                    drawLine(
                        color = if (gdp > 0.7f) RetroDarkColors.NeonCyan else RetroDarkColors.NeonOrange,
                        start = p1,
                        end = p3,
                        strokeWidth = 2f,
                        alpha = glowIntensity
                    )
                }

                // Draw peaks as pulsing nodes
                if (gdp > 0.8f) {
                    drawCircle(
                        color = RetroDarkColors.NeonGreen,
                        radius = 4f * scale,
                        center = p1,
                        alpha = glowIntensity
                    )
                }
            }
        }
    }
}
