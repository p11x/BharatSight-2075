package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C06. HorizontalProgressBars
 * Stacked horizontal bars, gradient fill, label+value on row.
 */
@Composable
fun HorizontalProgressBars(
    items: List<Pair<String, Float>>, // Label to Progress (0..1)
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.TEAL_PURPLE,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "HProgressAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items.forEach { (label, value) ->
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
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )
                    
                    // Progress Glow
                    drawRoundRect(
                        brush = brush,
                        size = Size(barWidth, size.height),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                        alpha = 0.3f
                    )
                    
                    // Progress
                    drawRoundRect(
                        brush = brush,
                        size = Size(barWidth, size.height),
                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHorizontalProgressBars() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        HorizontalProgressBars(
            items = listOf(
                "GDP GROWTH" to 0.81f,
                "TECH ADOPTION" to 0.92f,
                "URBANIZATION" to 0.65f
            ),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}
