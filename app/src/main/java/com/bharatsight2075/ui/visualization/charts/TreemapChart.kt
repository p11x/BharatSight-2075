package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.treemap.TreemapLayoutEngine

/**
 * C16. TreemapChart
 * Squarified rectangles, per-cell gradient.
 */
@Composable
fun TreemapChart(
    weights: List<Double>,
    brushes: List<Brush>,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "TreemapAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(
        modifier = modifier.drawWithCache {
            onDrawBehind {
                val rects = TreemapLayoutEngine.squarify(weights, Rect(0f, 0f, size.width, size.height))
                
                rects.forEach { treemapRect ->
                    val brush = brushes.getOrElse(treemapRect.originalIndex % brushes.size) { GradPalette.TEAL_PURPLE }
                    val rect = treemapRect.rect
                    
                    // Animate scaling from center
                    val animatedRect = Rect(
                        left = rect.left + (rect.width * (1 - currentProgress) / 2),
                        top = rect.top + (rect.height * (1 - currentProgress) / 2),
                        right = rect.right - (rect.width * (1 - currentProgress) / 2),
                        bottom = rect.bottom - (rect.height * (1 - currentProgress) / 2)
                    )
                    
                    drawRect(
                        brush = brush,
                        topLeft = Offset(animatedRect.left + 1.dp.toPx(), animatedRect.top + 1.dp.toPx()),
                        size = androidx.compose.ui.geometry.Size(animatedRect.width - 2.dp.toPx(), animatedRect.height - 2.dp.toPx()),
                        alpha = 0.8f * currentProgress
                    )
                    
                    // Border
                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(animatedRect.left, animatedRect.top),
                        size = androidx.compose.ui.geometry.Size(animatedRect.width, animatedRect.height),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
                    )
                }
            }
        }
    ) {}
}

@Preview
@Composable
fun PreviewTreemapChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        TreemapChart(
            weights = listOf(100.0, 80.0, 60.0, 40.0, 20.0, 20.0, 10.0),
            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE),
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )
    }
}
