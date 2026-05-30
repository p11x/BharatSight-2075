package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.treemap.TreemapLayoutEngine

/**
 * C16. TreemapChart
 */
@Composable
fun TreemapChart(
    weights: List<Double>,
    brushes: List<Brush>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    animated: Boolean = true
) {
    val safeWeights = weights.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.TREEMAP) as List<Double> }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "TreemapAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .drawWithCache {
                onDrawBehind {
                    val rects = TreemapLayoutEngine.squarify(safeWeights, Rect(0f, 0f, size.width, size.height))
                    
                    rects.forEach { treemapRect ->
                        val rect = treemapRect.rect
                        val color = when(treemapRect.originalIndex % 3) {
                            0 -> colors.primary
                            1 -> colors.accent
                            else -> Color(0xFF39FF14)
                        }
                        
                        // Scale from center for animation
                        val center = rect.center
                        val animatedW = rect.width * currentProgress
                        val animatedH = rect.height * currentProgress
                        val animatedRect = Rect(center.x - animatedW/2, center.y - animatedH/2, center.x + animatedW/2, center.y + animatedH/2)

                        drawRect(
                            color = color.copy(alpha = 0.8f),
                            topLeft = animatedRect.topLeft,
                            size = animatedRect.size
                        )
                        
                        drawRect(
                            color = Color.Black,
                            topLeft = animatedRect.topLeft,
                            size = animatedRect.size,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
                        )
                    }
                }
            }
    ) {}
}
