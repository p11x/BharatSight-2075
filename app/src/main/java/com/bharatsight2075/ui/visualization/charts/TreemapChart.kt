package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

@Composable
fun TreemapChart(
    modifier: Modifier = Modifier,
    values: List<Double> = emptyList(),
    data: List<Float> = emptyList(), // Added for compatibility
    weights: List<Double> = emptyList(), // Added for compatibility
    brushes: List<Brush> = emptyList(), // Added for compatibility
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    @Suppress("UNCHECKED_CAST")
    val safeValues = values.takeIf { it.isNotEmpty() } 
        ?: data.map { it.toDouble() }.takeIf { it.isNotEmpty() }
        ?: weights.takeIf { it.isNotEmpty() }
        ?: (ChartMockData.generateMockData(ChartType.TREEMAP) as List<Double>)
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "TreemapAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val total = safeValues.sum().coerceAtLeast(0.001)
        var currentX = 0f

        safeValues.forEachIndexed { i, valDouble ->
            val ratio = (valDouble / total).toFloat() * progress
            val w = size.width * ratio
            val h = size.height
            
            val color = primaryColor.copy(alpha = (0.2f + (i % 5) * 0.15f))
            val brush = brushes.getOrNull(i)
            
            if (brush != null) {
                drawRect(brush = brush, topLeft = Offset(currentX, 0f), size = Size(w.coerceAtLeast(4f), h))
            } else {
                drawRect(color = color, topLeft = Offset(currentX, 0f), size = Size(w.coerceAtLeast(4f), h))
            }

            drawRect(
                color = primaryColor.copy(alpha = 0.5f),
                topLeft = Offset(currentX, 0f),
                size = Size(w.coerceAtLeast(4f), h),
                style = Stroke(1.dp.toPx())
            )
            
            // Add "circuit" lines for tech look
            if (w > 20.dp.toPx()) {
                drawLine(
                    primaryColor.copy(alpha = 0.2f),
                    Offset(currentX + 5.dp.toPx(), 10.dp.toPx()),
                    Offset(currentX + 5.dp.toPx(), h - 10.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )
            }
            
            currentX += w
        }
    }
}
