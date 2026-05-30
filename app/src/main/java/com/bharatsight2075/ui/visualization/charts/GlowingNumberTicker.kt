package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import java.util.Locale

/**
 * C24. GlowingNumberTicker
 */
@Composable
fun GlowingNumberTicker(
    value: Float,
    unit: String,
    delta: String,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 80.dp,
    animated: Boolean = true
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(2000, easing = FastOutSlowInEasing),
        label = "TickerAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentVal = if (animated) value * progress else value
    val colors = SciFiTheme.extendedColors
    val primary = colors.primary

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .drawBehind {
                val radius = (size.width / 2).coerceAtLeast(0.01f)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(primary.copy(alpha = 0.08f), Color.Transparent),
                        center = center,
                        radius = radius
                    )
                )
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = String.format(Locale.getDefault(), "%.2f", currentVal),
                        style = SciFiTheme.typography.HeroNumber.copy(fontSize = 36.sp),
                        color = primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = unit,
                        style = SciFiTheme.typography.ChartCaption,
                        color = primary.copy(alpha = 0.4f),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(colors.positive.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                    .border(1.dp, colors.positive.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = delta,
                    style = SciFiTheme.typography.ChartCaption,
                    color = colors.positive,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
