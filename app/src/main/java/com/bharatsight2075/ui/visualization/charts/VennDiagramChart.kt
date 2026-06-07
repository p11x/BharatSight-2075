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

/**
 * C13. VennDiagramChart
 * FIXED: Radius and distance scaling to fit.
 */
@Composable
fun VennDiagramChart(
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 280.dp,
    animated: Boolean = true,
    primaryColor: Color = SciFiTheme.extendedColors.primary // Added primaryColor
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = EaseOutCubic),
        label = "chartProgress"
    )
    val glowPulse by rememberInfiniteTransition(label = "glow").animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "gp"
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
        
        drawCircle(primaryColor.copy(alpha = 0.3f * currentProgress), radius, center1, style = Fill)
        drawCircle(Color(0xFFFF6B35).copy(alpha = 0.3f * currentProgress), radius, center2, style = Fill)
        drawCircle(Color(0xFF7C4DFF).copy(alpha = 0.3f * currentProgress), radius, center3, style = Fill)
        
        // Double-pass glow
        drawCircle(primaryColor.copy(alpha = 0.2f * glowPulse * currentProgress), radius, center1, style = Stroke(4.dp.toPx()))
        drawCircle(primaryColor.copy(alpha = 0.9f * currentProgress), radius, center1, style = Stroke(1.dp.toPx()))
        
        drawCircle(Color(0xFFFF6B35).copy(alpha = 0.2f * glowPulse * currentProgress), radius, center2, style = Stroke(4.dp.toPx()))
        drawCircle(Color(0xFFFF6B35).copy(alpha = 0.9f * currentProgress), radius, center2, style = Stroke(1.dp.toPx()))
        
        drawCircle(Color(0xFF7C4DFF).copy(alpha = 0.2f * glowPulse * currentProgress), radius, center3, style = Stroke(4.dp.toPx()))
        drawCircle(Color(0xFF7C4DFF).copy(alpha = 0.9f * currentProgress), radius, center3, style = Stroke(1.dp.toPx()))
    }
}
