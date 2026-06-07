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
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .drawWithCache {
                onDrawBehind {
                    val center = center
                    val maxRadius = size.minDimension / 2 * 0.9f
                    
                    repeat(10) { layer ->
                        val radius = maxRadius * ((layer + 1) / 10f)
                        val segmentCount = 12 + layer * 4
                        val sweep = 360f / segmentCount
                        
                        for (i in 0 until segmentCount) {
                            if (i % 2 == 0) {
                                drawArc(
                                    color = color.copy(alpha = 0.1f + (layer / 20f)),
                                    startAngle = i * sweep,
                                    sweepAngle = sweep * 0.8f,
                                    useCenter = false,
                                    topLeft = Offset(center.x - radius, center.y - radius),
                                    size = Size(radius * 2, radius * 2),
                                    style = Stroke(width = 3.dp.toPx())
                                )
                            }
                        }
                    }
                    
                    repeat(8) { spoke ->
                        val angle = Math.toRadians((spoke * 45.0))
                        val startX = center.x + (maxRadius * 0.1f) * cos(angle).toFloat()
                        val startY = center.y + (maxRadius * 0.1f) * sin(angle).toFloat()
                        val endX = center.x + maxRadius * cos(angle).toFloat()
                        val endY = center.y + maxRadius * sin(angle).toFloat()
                        
                        drawLine(
                            color = Color.White.copy(alpha = 0.2f),
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }
            }
    ) {}
}
