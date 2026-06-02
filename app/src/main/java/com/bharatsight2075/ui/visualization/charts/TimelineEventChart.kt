package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

data class TimelineEvent(val year: String, val label: String)

/**
 * C23. TimelineEventChart
 */
@Composable
fun TimelineEventChart(
    events: List<TimelineEvent>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 140.dp,
    animated: Boolean = true
) {
    val safeEvents = events.takeIf { it.isNotEmpty() } ?: listOf(
        TimelineEvent("2030", "Solar Grid"),
        TimelineEvent("2045", "Hyperloop"),
        TimelineEvent("2060", "AI Gov"),
        TimelineEvent("2075", "GDP $37T")
    )

    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "TimelineAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val primary = SciFiTheme.extendedColors.primary
    val density = LocalDensity.current

    BoxWithConstraints(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val wPx = with(density) { maxWidth.toPx() }
        val lineYPx = with(density) { (maxHeight / 2).toPx() }
        
        Canvas(Modifier.fillMaxSize()) {
            // Main Line
            drawLine(primary.copy(0.4f), Offset(0f, lineYPx), Offset(wPx * currentProgress, lineYPx), 2.dp.toPx())
            
            safeEvents.forEachIndexed { i, _ ->
                val x = (i.toFloat() / (safeEvents.size - 1).coerceAtLeast(1)) * wPx * currentProgress
                // Dot
                drawCircle(primary.copy(0.2f), 14.dp.toPx(), Offset(x, lineYPx))
                drawCircle(primary, 6.dp.toPx(), Offset(x, lineYPx))
                
                // Tick line
                val dir = if (i % 2 == 0) -1 else 1
                drawLine(primary.copy(0.6f), Offset(x, lineYPx), Offset(x, lineYPx + dir * 40.dp.toPx()), 1.dp.toPx())
            }
        }
        
        safeEvents.forEachIndexed { i, event ->
            val xFraction = i.toFloat() / (safeEvents.size - 1).coerceAtLeast(1)
            val xDp = (xFraction * maxWidth.value).dp
            val topOffset = if (i % 2 == 0) 4.dp else (maxHeight / 2 + 44.dp)
            
            Box(
                Modifier
                    .offset(x = xDp * currentProgress - 36.dp, y = topOffset)
                    .width(72.dp)
                    .graphicsLayer { alpha = currentProgress },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = event.year,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        color = primary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = event.label,
                        fontSize = 8.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
