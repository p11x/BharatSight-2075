package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

/**
 * C03. GradientDonutChart
 * Thick arc with gradient stroke, center: large value + label.
 */
@Composable
fun GradientDonutChart(
    values: List<Float>,
    brushes: List<Brush>,
    label: String,
    modifier: Modifier = Modifier,
    totalValue: Float = values.sum(),
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "DonutAnim"
    )
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 24.dp.toPx()
            val diameter = size.minDimension - strokeWidth
            val arcSize = Size(diameter, diameter)
            val topLeft = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)
            
            var startAngle = -90f
            
            values.forEachIndexed { index, value ->
                val sweepAngle = (value / totalValue) * 360f * currentProgress
                val brush = brushes.getOrElse(index) { GradPalette.TEAL_PURPLE }
                
                // Pass 1: Glow
                drawArc(
                    brush = brush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth + 8.dp.toPx(), cap = StrokeCap.Butt),
                    alpha = 0.2f
                )
                
                // Pass 2: Sharp
                drawArc(
                    brush = brush,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                
                startAngle += sweepAngle
            }
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = SciFiTheme.typography.ChartCaption,
                color = colors.textSecondary
            )
            Text(
                text = totalValue.toInt().toString(),
                style = SciFiTheme.typography.HeroNumber.copy(fontSize = 24.sp),
                color = colors.textPrimary
            )
        }
    }
}

@Preview
@Composable
fun PreviewGradientDonutChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        GradientDonutChart(
            values = listOf(40f, 30f, 30f),
            brushes = listOf(GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.PURPLE_BLUE),
            label = "SECTOR BREAKDOWN",
            modifier = Modifier.size(200.dp)
        )
    }
}
