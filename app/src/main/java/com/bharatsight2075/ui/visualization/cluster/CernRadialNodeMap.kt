package com.bharatsight2075.ui.visualization.cluster

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun CernRadialNodeMap(
    modifier: Modifier = Modifier,
    nodeCount: Int = 50,
    connectionRadiusMultiplier: Float = 0.8f
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .drawWithCache {
                onDrawBehind {
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val maxRadius = size.minDimension / 2f * 0.9f
                    val nodes = generateNodePositions(nodeCount, center, maxRadius)

                    val connectionColor = RetroDarkColors.NeonCyan.copy(alpha = 0.2f)
                    val maxDistanceSq = (maxRadius * connectionRadiusMultiplier) * (maxRadius * connectionRadiusMultiplier)

                    for (i in nodes.indices) {
                        for (j in i + 1 until nodes.size) {
                            val dx = nodes[i].x - nodes[j].x
                            val dy = nodes[i].y - nodes[j].y
                            val distanceSq = dx * dx + dy * dy
                            if (distanceSq < maxDistanceSq) {
                                drawLine(
                                    color = connectionColor,
                                    start = nodes[i],
                                    end = nodes[j],
                                    strokeWidth = 1.dp.toPx()
                                )
                            }
                        }
                    }

                    val nodeColor = RetroDarkColors.NeonCyan
                    val nodeRadius = 4.dp.toPx()

                    for (node in nodes) {
                        drawCircle(
                            color = nodeColor,
                            center = node,
                            radius = nodeRadius
                        )
                    }
                }
            }
    ) {}
}

private fun generateNodePositions(count: Int, center: Offset, maxRadius: Float): List<Offset> {
    val positions = mutableListOf<Offset>()
    val random = Random(42)
    for (i in 0 until count) {
        val angle = (i * Math.PI * 2f / count).toFloat() + random.nextFloat() * 0.5f
        val radius = maxRadius * (0.3f + random.nextFloat() * 0.7f)
        val x = center.x + radius * cos(angle.toDouble()).toFloat()
        val y = center.y + radius * sin(angle.toDouble()).toFloat()
        positions.add(Offset(x, y))
    }
    return positions
}
