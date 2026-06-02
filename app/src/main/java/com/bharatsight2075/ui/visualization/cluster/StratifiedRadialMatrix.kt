package com.bharatsight2075.ui.visualization.cluster

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StratifiedRadialMatrix(
    modifier: Modifier = Modifier,
    color: Color = RetroDarkColors.NeonCyan
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(32.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    onDrawBehind {
                        val center = Offset(size.width / 2, size.height / 2)
                        val maxRadius = size.minDimension / 2
                        
                        // 1. Draw Concentric Arcs (10 layers)
                        repeat(10) { layer ->
                            val radius = maxRadius * ((layer + 1) / 10f)
                            val segmentCount = 20 + layer * 4
                            val sweep = 360f / segmentCount
                            
                            for (i in 0 until segmentCount) {
                                // Draw only every other segment for the machine-cog look
                                if (i % 2 == 0) {
                                    drawArc(
                                        color = color.copy(alpha = 0.1f + (layer / 15f)),
                                        startAngle = i * sweep,
                                        sweepAngle = sweep - 2f, // 2-degree gap
                                        useCenter = false,
                                        topLeft = Offset(center.x - radius, center.y - radius),
                                        size = Size(radius * 2, radius * 2),
                                        style = Stroke(width = 2.dp.toPx())
                                    )
                                }
                            }
                        }
                        
                        // 2. Draw Structural Spokes (White Glowing Lines)
                        repeat(8) { spoke ->
                            val angle = Math.toRadians((spoke * 45.0))
                            val startX = center.x + (maxRadius * 0.2f) * cos(angle).toFloat()
                            val startY = center.y + (maxRadius * 0.2f) * sin(angle).toFloat()
                            val endX = center.x + maxRadius * cos(angle).toFloat()
                            val endY = center.y + maxRadius * sin(angle).toFloat()
                            
                            drawLine(
                                color = Color.White.copy(alpha = 0.3f),
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = 1f
                            )
                        }
                        
                        // 3. Core Hub
                        drawCircle(
                            color = color,
                            radius = 4.dp.toPx(),
                            center = center,
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }
                }
        ) {}
    }
}
