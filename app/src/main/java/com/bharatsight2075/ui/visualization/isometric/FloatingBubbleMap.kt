package com.bharatsight2075.ui.visualization.isometric

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.visualization.isometric.IsometricUtils.projectToIsometric

data class BubblePoint(
    val x: Float, // 0..1 relative to map width
    val z: Float, // 0..1 relative to map depth
    val altitude: Float, // 0..1 relative to max height
    val radius: Float,
    val color: Color
)

@Composable
fun FloatingBubbleMap(
    modifier: Modifier = Modifier,
    points: List<BubblePoint>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(16.dp)
            .clipToBounds()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    onDrawBehind {
                        val mapWidth = size.width * 0.8f
                        val mapDepth = size.width * 0.8f
                        val offsetX = (size.width - mapWidth) / 4
                        val offsetZ = 0f
                        val groundY = size.height * 0.8f
                        val maxAltitude = 150f

                        // 1. Draw Isometric Floor Grid (Generic Landmass Reference)
                        val floorPath = Path().apply {
                            val p1 = projectToIsometric(offsetX, groundY, offsetZ)
                            val p2 = projectToIsometric(offsetX + mapWidth, groundY, offsetZ)
                            val p3 = projectToIsometric(offsetX + mapWidth, groundY, offsetZ + mapDepth)
                            val p4 = projectToIsometric(offsetX, groundY, offsetZ + mapDepth)
                            moveTo(p1.x, p1.y)
                            lineTo(p2.x, p2.y)
                            lineTo(p3.x, p3.y)
                            lineTo(p4.x, p4.y)
                            close()
                        }
                        drawPath(floorPath, RetroDarkColors.GridLine, style = Stroke(width = 1.dp.toPx()))

                        // 2. Draw Bubbles and Tether Lines
                        points.forEach { point ->
                            val px = offsetX + point.x * mapWidth
                            val pz = offsetZ + point.z * mapDepth
                            val h = point.altitude * maxAltitude
                            
                            val floorPos = projectToIsometric(px, groundY, pz)
                            val skyPos = projectToIsometric(px, groundY - h, pz)

                            // Tether Line (Structural)
                            drawLine(
                                color = point.color.copy(alpha = 0.3f),
                                start = floorPos,
                                end = skyPos,
                                strokeWidth = 1.dp.toPx()
                            )

                            // Floor shadow/indicator
                            drawCircle(
                                color = point.color.copy(alpha = 0.2f),
                                radius = 4.dp.toPx(),
                                center = floorPos
                            )

                            // Glowing Sphere
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(point.color, Color.Transparent),
                                    center = skyPos,
                                    radius = point.radius.dp.toPx()
                                ),
                                radius = point.radius.dp.toPx(),
                                center = skyPos
                            )
                            
                            // Core point
                            drawCircle(
                                color = Color.White,
                                radius = 2.dp.toPx(),
                                center = skyPos
                            )
                        }
                    }
                }
        ) {}
    }
}
