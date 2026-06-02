package com.bharatsight2075.ui.visualization.charts

import android.graphics.SweepGradient
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

/**
 * C03. GradientDonutChart
 */
@Composable
fun GradientDonutChart(
    values: List<Float>,
    brushes: List<Brush>,
    label: String,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    animated: Boolean = true
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: listOf(40f, 30f, 30f)
    val total = safeData.sum()
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "DonutAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Box(modifier = modifier.fillMaxWidth().height(chartHeight), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 24.dp.toPx()
            val diameter = size.minDimension - strokeWidth - 16.dp.toPx()
            val arcSize = Size(diameter, diameter)
            val topLeft = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)
            
            var startAngle = -90f
            
            safeData.forEachIndexed { index, value ->
                val sweepAngle = (value / total) * 360f * currentProgress
                val color1 = Color(0xFF00F5FF) // Fallback
                val color2 = Color(0xFF7B2FBE)
                
                val shader = SweepGradient(
                    center.x, center.y,
                    intArrayOf(color1.toArgb(), color2.toArgb(), color1.toArgb()),
                    null
                )
                val shaderBrush = ShaderBrush(shader)
                
                // Pass 1: Glow
                drawArc(
                    brush = shaderBrush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth + 8.dp.toPx(), cap = StrokeCap.Butt),
                    alpha = 0.2f
                )
                
                // Pass 2: Sharp
                drawArc(
                    brush = shaderBrush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                
                startAngle += sweepAngle
            }
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = SciFiTheme.typography.ChartCaption,
                color = colors.textSecondary
            )
            Text(
                text = total.toInt().toString(),
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 24.sp),
                color = colors.textPrimary
            )
        }
    }
}
