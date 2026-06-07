package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C06. HorizontalProgressBars
 */
@Composable
fun HorizontalProgressBars(
    modifier: Modifier = Modifier,
    items: List<Pair<String, Float>> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 140.dp,
    animated: Boolean = true,
    primaryColor: Color = SciFiTheme.extendedColors.primary // Added primaryColor
) {
    val safeItems = items.takeIf { it.isNotEmpty() } ?: listOf("METRIC A" to 0.7f, "METRIC B" to 0.4f, "METRIC C" to 0.9f)
    
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

    Column(modifier = modifier.fillMaxWidth().height(chartHeight), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        safeItems.forEach { (label, value) ->
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(label, style = SciFiTheme.typography.ChartCaption, color = colors.textSecondary)
                    Text("${(value * 100).toInt()}%", style = SciFiTheme.typography.ChartCaption, color = primaryColor)
                }
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(primaryColor.copy(alpha = 0.1f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(value * currentProgress)
                            .fillMaxHeight()
                            .drawBehind {
                                // Subtle glow
                                drawRect(
                                    color = primaryColor.copy(alpha = 0.2f * glowPulse),
                                    size = size.copy(height = size.height + 4.dp.toPx()),
                                    topLeft = Offset(0f, -2.dp.toPx())
                                )
                            }
                            .background(
                                Brush.horizontalGradient(listOf(primaryColor.copy(alpha = 0.5f), primaryColor))
                            )
                    )
                }
            }
        }
    }
}
