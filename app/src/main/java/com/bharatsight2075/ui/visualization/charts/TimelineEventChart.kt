package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

data class TimelineEvent(val year: Int, val title: String, val isTop: Boolean = true)

/**
 * C23. TimelineEventChart
 * Horizontal timeline with event markers.
 */
@Composable
fun TimelineEventChart(
    events: List<TimelineEvent>,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "TimelineAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val centerY = size.height / 2
            
            // Timeline main line
            drawLine(
                color = colors.primary.copy(alpha = 0.4f),
                start = Offset(0f, centerY),
                end = Offset(width * currentProgress, centerY),
                strokeWidth = 2.dp.toPx()
            )
            
            val minYear = events.minOf { it.year }
            val maxYear = events.maxOf { it.year }
            val yearRange = (maxYear - minYear).coerceAtLeast(1)
            
            events.forEach { event ->
                val x = ((event.year - minYear).toFloat() / yearRange) * width * currentProgress
                
                // Event Marker
                drawCircle(
                    color = colors.primary,
                    radius = 5.dp.toPx(),
                    center = Offset(x, centerY)
                )
                
                drawCircle(
                    color = colors.primary.copy(alpha = 0.3f),
                    radius = 10.dp.toPx(),
                    center = Offset(x, centerY)
                )
                
                // Connecting line to label
                val lineEnd = if (event.isTop) centerY - 40.dp.toPx() else centerY + 40.dp.toPx()
                drawLine(
                    color = colors.accent.copy(alpha = 0.5f),
                    start = Offset(x, centerY),
                    end = Offset(x, lineEnd),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTimelineEventChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        TimelineEventChart(
            events = listOf(
                TimelineEvent(2030, "SOLAR GRID", true),
                TimelineEvent(2045, "HYPERLOOP", false),
                TimelineEvent(2060, "AI GOV", true),
                TimelineEvent(2075, "GDP $37T", false)
            ),
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
    }
}
