package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C15. HeatmapGridChart
 * N×M grid cells, color intensity = value.
 */
@Composable
fun HeatmapGridChart(
    data: List<List<Float>>, // Normalized 0..1 values
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.YELLOW_ORANGE,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "HeatmapAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(modifier = modifier) {
        val rows = data.size
        val cols = data.firstOrNull()?.size ?: 0
        if (cols == 0) return@Canvas
        
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows
        
        data.forEachIndexed { r, row ->
            row.forEachIndexed { c, value ->
                val x = c * cellWidth
                val y = r * cellHeight
                
                // Draw Cell with alpha intensity
                drawRect(
                    brush = brush,
                    topLeft = Offset(x + 1.dp.toPx(), y + 1.dp.toPx()),
                    size = Size(cellWidth - 2.dp.toPx(), cellHeight - 2.dp.toPx()),
                    alpha = value * currentProgress
                )
                
                // Add a very subtle inner border
                drawRect(
                    color = Color.White.copy(alpha = 0.05f),
                    topLeft = Offset(x, y),
                    size = Size(cellWidth, cellHeight),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 0.5.dp.toPx())
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHeatmapGridChart() {
    val mockData = List(10) { List(10) { (0..100).random() / 100f } }
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        HeatmapGridChart(
            data = mockData,
            modifier = Modifier.size(300.dp)
        )
    }
}
