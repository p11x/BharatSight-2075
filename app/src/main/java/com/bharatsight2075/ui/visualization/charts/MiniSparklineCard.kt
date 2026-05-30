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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C25. MiniSparklineCard
 * Compact area sparkline for dashboard summaries.
 */
@Composable
fun MiniSparklineCard(
    label: String,
    value: String,
    data: List<Float>,
    modifier: Modifier = Modifier,
    brush: Brush = GradPalette.TEAL_PURPLE,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "SparkAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = SciFiTheme.typography.ChartCaption,
                color = colors.textSecondary
            )
            Text(
                text = value,
                style = SciFiTheme.typography.BodyMono.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold),
                color = colors.textPrimary
            )
        }
        
        Canvas(modifier = Modifier.size(80.dp, 40.dp)) {
            if (data.size < 2) return@Canvas
            
            val width = size.width
            val height = size.height
            val spacing = width / (data.size - 1)
            val maxVal = data.maxOrNull()?.coerceAtLeast(0.1f) ?: 1f
            
            val path = Path()
            data.forEachIndexed { i, v ->
                val x = i * spacing
                val y = height - (v / maxVal) * height * currentProgress
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            
            val fillPath = Path().apply {
                addPath(path)
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            
            drawPath(path = fillPath, brush = brush, alpha = 0.2f)
            drawPath(path = path, brush = brush, style = Stroke(width = 2.dp.toPx()))
        }
    }
}

@Preview
@Composable
fun PreviewMiniSparklineCard() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        MiniSparklineCard(
            label = "NIFTY 50",
            value = "24,567",
            data = listOf(0.4f, 0.6f, 0.5f, 0.8f, 0.7f, 0.9f)
        )
    }
}
