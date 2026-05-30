package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.math.cos
import kotlin.math.sin

/**
 * C09. RadarPolygonChart
 * N-axis spider, gradient-filled polygon.
 */
@Composable
fun RadarPolygonChart(
    data: List<Float>, // Values normalized 0..1
    labels: List<String>,
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.ORANGE_PINK,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "RadarAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val extendedColors = SciFiTheme.extendedColors

    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2 * 0.8f
        val angleStep = (2 * Math.PI / data.size)
        
        // Draw Axis
        for (i in data.indices) {
            val angle = i * angleStep - Math.PI / 2
            val lineEnd = Offset(
                (center.x + radius * cos(angle)).toFloat(),
                (center.y + radius * sin(angle)).toFloat()
            )
            drawLine(
                color = extendedColors.textDisabled.copy(alpha = 0.3f),
                start = center,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )
        }
        
        // Draw Web Concentric Circles
        for (i in 1..4) {
            val r = radius * (i / 4f)
            drawCircle(
                color = extendedColors.textDisabled.copy(alpha = 0.1f),
                radius = r,
                center = center,
                style = Stroke(width = 1.dp.toPx())
            )
        }
        
        // Draw Polygon Path
        val polygonPath = Path()
        data.forEachIndexed { i, value ->
            val angle = i * angleStep - Math.PI / 2
            val r = radius * value * currentProgress
            val x = (center.x + r * cos(angle)).toFloat()
            val y = (center.y + r * sin(angle)).toFloat()
            
            if (i == 0) polygonPath.moveTo(x, y) else polygonPath.lineTo(x, y)
        }
        polygonPath.close()
        
        // Pass 1: Fill
        drawPath(path = polygonPath, brush = brush, alpha = 0.4f)
        
        // Pass 2: Glow Border
        drawPath(
            path = polygonPath,
            brush = brush,
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round),
            alpha = 0.2f
        )
        
        // Pass 3: Sharp Border
        drawPath(
            path = polygonPath,
            brush = brush,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Preview
@Composable
fun PreviewRadarPolygonChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        RadarPolygonChart(
            data = listOf(0.8f, 0.6f, 0.9f, 0.4f, 0.7f),
            labels = listOf("GDP", "HDI", "INF", "EMP", "TRD"),
            modifier = Modifier.size(300.dp)
        )
    }
}
