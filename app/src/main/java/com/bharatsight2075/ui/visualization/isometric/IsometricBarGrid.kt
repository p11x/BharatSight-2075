package com.bharatsight2075.ui.visualization.isometric

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.visualization.isometric.IsometricUtils.drawIsometricCube
import com.bharatsight2075.ui.visualization.isometric.IsometricUtils.projectToIsometric

@Composable
fun IsometricBarGrid(
    modifier: Modifier = Modifier,
    matrix: List<List<Float>>,
    baseColor: Color = RetroDarkColors.NeonCyan
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(top = 40.dp)
            .clipToBounds()
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    onDrawBehind {
                        val rows = matrix.size
                        val cols = matrix.firstOrNull()?.size ?: 0
                        if (rows == 0 || cols == 0) return@onDrawBehind

                        // Origin centering (in 3D space before projection)
                        val cubeSize = 30f
                        val gap = 10f
                        val totalWidth = cols * (cubeSize + gap)
                        val totalDepth = rows * (cubeSize + gap)
                        
                        val startX = (size.width / 2) - (totalWidth / 2) // Rough screen center
                        val startZ = 0f
                        val groundY = size.height * 0.7f

                        // 1. Draw Ground Platform
                        drawIsometricCube(
                            x = startX - 20f,
                            y = groundY + 10f,
                            z = startZ - 20f,
                            width = totalWidth + 40f,
                            height = 10f,
                            depth = totalDepth + 40f,
                            color = RetroDarkColors.GridLine
                        )

                        // 2. Draw Pillars with Z-Sorting (Back to Front)
                        // In our projection, back is small row/col indices
                        for (r in 0 until rows) {
                            for (c in 0 until cols) {
                                val value = matrix[r][c]
                                val pillarHeight = value * 150f
                                
                                val posX = startX + c * (cubeSize + gap)
                                val posZ = startZ + r * (cubeSize + gap)
                                
                                drawIsometricCube(
                                    x = posX,
                                    y = groundY,
                                    z = posZ,
                                    width = cubeSize,
                                    height = pillarHeight,
                                    depth = cubeSize,
                                    color = baseColor.copy(alpha = 0.6f + (value * 0.4f))
                                )
                            }
                        }
                    }
                }
        ) {}
    }
}
