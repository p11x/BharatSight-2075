package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

data class TimelineEvent(val title: String, val year: String, val value: Float = 0f)

/**
 * C23. TimelineEventChart
 */
@Composable
fun TimelineEventChart(
    modifier: Modifier = Modifier,
    events: List<TimelineEvent> = emptyList(),
    data: List<Float> = emptyList(), // Compatibility
    chartHeight: androidx.compose.ui.unit.Dp = 140.dp,
    animated: Boolean = true,
    primaryColor: Color = SciFiTheme.extendedColors.primary // Added primaryColor
) {
    @Suppress("UNCHECKED_CAST")
    val safeEvents = events.takeIf { it.isNotEmpty() } 
        ?: data.mapIndexed { i, v -> TimelineEvent("Event ${i+1}", "${2025+i}", v) }.takeIf { it.isNotEmpty() }
        ?: (ChartMockData.generateMockData(ChartType.TIMELINE) as List<TimelineEvent>)

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "TimelineAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val density = LocalDensity.current

    BoxWithConstraints(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val wPx = with(density) { maxWidth.toPx() }
        val lineYPx = with(density) { (maxHeight / 2).toPx() }
        
        Canvas(Modifier.fillMaxSize()) {
            // Main Line
            drawLine(
                color = primaryColor.copy(alpha = 0.3f),
                start = Offset(0f, lineYPx),
                end = Offset(wPx * currentProgress, lineYPx),
                strokeWidth = 2.dp.toPx()
            )
            
            safeEvents.forEachIndexed { i, event ->
                val x = (i + 0.5f) / safeEvents.size * wPx
                if (x > wPx * currentProgress) return@forEachIndexed
                
                // Event point
                drawCircle(primaryColor, 6.dp.toPx(), Offset(x, lineYPx))
                drawCircle(Color.White, 2.dp.toPx(), Offset(x, lineYPx))
                
                // Connecting line to text (simulated)
                val h = if (i % 2 == 0) -30.dp.toPx() else 30.dp.toPx()
                drawLine(primaryColor.copy(alpha = 0.5f), Offset(x, lineYPx), Offset(x, lineYPx + h), strokeWidth = 1.dp.toPx())
            }
        }
    }
}
