package com.bharatsight2075.ui.visualization.isometric

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.isometric.IsometricUtils.drawIsometricCube

@Composable
fun IsometricBarGrid(
    modifier: Modifier = Modifier,
    matrix: List<List<Float>> = emptyList(),
    baseColor: Color = RetroDarkColors.NeonCyan
) {
    val rawMatrix = matrix.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.HEATMAP) as List<List<Float>>
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .drawWithCache {
                onDrawBehind {
                    val rows = rawMatrix.size
                    val cols = rawMatrix.firstOrNull()?.size ?: 0
                    if (rows == 0 || cols == 0) return@onDrawBehind

                    val cubeSize = (size.width / (cols + rows) * 0.8f).coerceAtLeast(10f)
                    val gap = cubeSize * 0.2f
                    
                    val groundY = size.height * 0.8f
                    val startX = size.width / 2

                    for (r in 0 until rows) {
                        for (c in 0 until cols) {
                            val value = rawMatrix[r][c]
                            val pillarHeight = value * (size.height * 0.5f)
                            
                            val posX = startX + (c - r) * (cubeSize + gap) * 0.866f
                            val posZ = (c + r) * (cubeSize + gap) * 0.5f
                            
                            drawIsometricCube(
                                x = posX,
                                y = groundY,
                                z = posZ,
                                width = cubeSize,
                                height = pillarHeight,
                                depth = cubeSize,
                                color = baseColor.copy(alpha = 0.4f + (value * 0.6f))
                            )
                        }
                    }
                }
            }
    ) {}
}
