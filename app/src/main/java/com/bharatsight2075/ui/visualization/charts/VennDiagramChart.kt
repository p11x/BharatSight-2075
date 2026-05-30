package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C13. VennDiagramChart
 * 3 overlapping gradient circles with blend mode Screen.
 */
@Composable
fun VennDiagramChart(
    modifier: Modifier = Modifier,
    brushes: List<Brush> = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL),
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "VennAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(
        modifier = modifier.drawWithCache {
            onDrawBehind {
                val radius = (size.minDimension / 3.5f) * currentProgress
                val distance = radius * 0.8f
                
                val centers = listOf(
                    Offset(center.x, center.y - distance),
                    Offset(center.x - distance * 0.866f, center.y + distance * 0.5f),
                    Offset(center.x + distance * 0.866f, center.y + distance * 0.5f)
                )
                
                drawIntoCanvas { canvas ->
                    canvas.saveLayer(androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height), Paint())
                    
                    centers.forEachIndexed { index, circleCenter ->
                        drawCircle(
                            brush = brushes.getOrElse(index) { GradPalette.TEAL_PURPLE },
                            radius = radius,
                            center = circleCenter,
                            blendMode = BlendMode.Screen,
                            alpha = 0.6f
                        )
                    }
                    
                    canvas.restore()
                }
            }
        }
    ) {}
}

@Preview
@Composable
fun PreviewVennDiagramChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        VennDiagramChart(modifier = Modifier.size(300.dp))
    }
}
