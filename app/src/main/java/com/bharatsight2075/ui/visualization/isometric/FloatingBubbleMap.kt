package com.bharatsight2075.ui.visualization.isometric

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.visualization.isometric.IsometricUtils.projectToIsometric

data class IsometricBubblePoint(
    val x: Float, // 0..1 relative to map width
    val z: Float, // 0..1 relative to map depth
    val altitude: Float, // 0..1 relative to max height
    val radius: Float,
    val color: Color
)

@Composable
fun FloatingBubbleMap(
    modifier: Modifier = Modifier,
    points: List<IsometricBubblePoint> = emptyList()
) {
    val safeData = points.takeIf { it.isNotEmpty() } ?: listOf(
        IsometricBubblePoint(0.2f, 0.2f, 0.5f, 10f, Color.Cyan),
        IsometricBubblePoint(0.5f, 0.5f, 0.8f, 15f, Color.Magenta),
        IsometricBubblePoint(0.8f, 0.8f, 0.3f, 12f, Color.Yellow)
    )
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .drawWithCache {
                onDrawBehind {
                    val mapWidth = size.width * 0.7f
                    val mapDepth = size.width * 0.7f
                    val offsetX = size.width * 0.15f
                    val offsetZ = 0f
                    val groundY = size.height * 0.8f
                    val maxAltitude = size.height * 0.5f

                    safeData.forEach { point ->
                        val px = offsetX + point.x * mapWidth
                        val pz = offsetZ + point.z * mapDepth
                        val h = point.altitude * maxAltitude
                        
                        val floorPos = projectToIsometric(px, groundY, pz)
                        val skyPos = projectToIsometric(px, groundY - h, pz)

                        drawLine(
                            color = point.color.copy(alpha = 0.3f),
                            start = floorPos, end = skyPos, strokeWidth = 1.dp.toPx()
                        )

                        drawCircle(
                            brush = Brush.radialGradient(listOf(point.color, Color.Transparent), center = skyPos, radius = point.radius.dp.toPx() * 2),
                            radius = point.radius.dp.toPx() * 2, center = skyPos
                        )
                        drawCircle(color = Color.White, radius = 2.dp.toPx(), center = skyPos)
                    }
                }
            }
    ) {}
}
