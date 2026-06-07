package com.bharatsight2075.ui.visualization.line

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

@Composable
fun HolographicLineChart(
    modifier: Modifier = Modifier,
    points: List<Float> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = Color(0xFF00F5FF)
) {
    val safeData = points.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.LINE)
    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(2000, easing = EaseOutQuart),
        label = "HoloLineAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight).drawWithCache {
        val b = Brush.verticalGradient(listOf(primaryColor.copy(alpha = 0.5f), Color.Transparent))
        onDrawBehind {
            val path = Path()
            val count = safeData.size
            
            safeData.forEachIndexed { index, value ->
                val x = index.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                val y = size.height - (value / maxVal * size.height * progress)
                
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                
                // Vertical glow lines
                drawLine(primaryColor.copy(alpha = 0.1f * progress), Offset(x, y), Offset(x, size.height), strokeWidth = 1.dp.toPx())
            }
            
            drawPath(path, primaryColor, style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
            drawPath(path, b, style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round), alpha = 0.3f)
        }
    }) { }
}
