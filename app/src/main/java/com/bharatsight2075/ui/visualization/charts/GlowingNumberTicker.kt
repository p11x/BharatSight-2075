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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import java.text.NumberFormat
import java.util.*

/**
 * C24. GlowingNumberTicker
 * large animated counting-up number with glow.
 */
@Composable
fun GlowingNumberTicker(
    value: Float,
    unit: String,
    delta: String,
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.TEAL_PURPLE,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(2000, easing = FastOutSlowInEasing),
        label = "TickerAnim"
    )
    
    val currentVal = if (animated) value * progress else value
    val extendedColors = SciFiTheme.extendedColors
    val primary = extendedColors.primary
    val positive = extendedColors.positive

    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(primary.copy(alpha = 0.08f), Color.Transparent),
                        center = center,
                        radius = 120.dp.toPx()
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
                Text(
                    text = "ESTIMATED 2075 GDP",
                    style = SciFiTheme.typography.MetricLabel,
                    color = primary.copy(alpha = 0.6f)
                )
                
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = String.format("%.2f", currentVal),
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
                    .background(positive.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                    .border(1.dp, positive.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = delta,
                    style = SciFiTheme.typography.ChartCaption,
                    color = positive,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGlowingNumberTicker() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        GlowingNumberTicker(
            value = 37.0f,
            unit = "USD TRILLION",
            delta = "▲ 8.1% YoY",
            modifier = Modifier.padding(16.dp)
        )
    }
}
