package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C06. HorizontalProgressBars
 */
@Composable
fun HorizontalProgressBars(
    items: List<Pair<String, Float>>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 120.dp,
    animated: Boolean = true
) {
    val safeItems = items.takeIf { it.isNotEmpty() } ?: listOf("METRIC A" to 0.7f, "METRIC B" to 0.4f, "METRIC C" to 0.9f)
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "HProgressAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Column(modifier = modifier.fillMaxWidth().height(chartHeight), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        safeItems.forEach { (label, value) ->
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(label, style = SciFiTheme.typography.ChartCaption, color = colors.textSecondary)
                    Text("${(value * 100).toInt()}%", style = SciFiTheme.typography.BodyMono, color = colors.textPrimary)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Canvas(modifier = Modifier.fillMaxWidth().height(8.dp)) {
                    val barWidth = size.width * value * currentProgress
                    
                    // Track
                    drawRoundRect(
                        color = colors.textDisabled.copy(alpha = 0.2f),
                        size = size,
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                    
                    // Progress
                    drawRoundRect(
                        brush = Brush.horizontalGradient(listOf(colors.primary, colors.accent)),
                        size = Size(barWidth, size.height),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                }
            }
        }
    }
}
