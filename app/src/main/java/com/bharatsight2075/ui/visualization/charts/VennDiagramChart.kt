package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C13. VennDiagramChart
 * FIXED: Radius and distance scaling to fit.
 */
@Composable
fun VennDiagramChart(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    animated: Boolean = true
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "VennAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val radius = (size.minDimension / 4f) * currentProgress
        if (radius <= 0.1f) return@Canvas
        
        val distance = radius * 0.7f // Adjusted to fit
        val vCenter = center.y + 10.dp.toPx() // Shift down slightly
        
        val center1 = Offset(center.x, vCenter - distance)
        val center2 = Offset(center.x - distance * 0.866f, vCenter + distance * 0.5f)
        val center3 = Offset(center.x + distance * 0.866f, vCenter + distance * 0.5f)
        
        drawIntoCanvas { canvas ->
            val layerBounds = Rect(Offset.Zero, size)
            canvas.saveLayer(layerBounds, Paint())
            
            drawCircle(colors.primary.copy(alpha = 0.5f), radius, center1, blendMode = BlendMode.SrcOver)
            drawCircle(colors.accent.copy(alpha = 0.5f), radius, center2, blendMode = BlendMode.Screen)
            drawCircle(Color(0xFF39FF14).copy(alpha = 0.5f), radius, center3, blendMode = BlendMode.Screen)
            
            canvas.restore()
        }
    }
}
