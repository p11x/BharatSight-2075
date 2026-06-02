package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.GradientFills

/**
 * C05. GradientBarChart
 */
@Composable
fun GradientBarChart(
    data: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 160.dp,
    animated: Boolean = true
) {
    val safeData = data.takeIf { it.isNotEmpty() } 
        ?: remember { ChartMockData.generateMockData(ChartType.BAR).filterIsInstance<Float>() }
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "BarAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors
    val primary = colors.primary

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .drawWithCache {
                onDrawBehind {
                    val count = safeData.size.coerceAtLeast(1)
                    val totalGap = size.width * 0.3f
                    val barWidth = (size.width - totalGap) / count
                    val gap = totalGap / (count + 1)
                    val maxVal = safeData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
                    
                    safeData.forEachIndexed { i, v ->
                        val barHeight = (v / maxVal) * size.height * currentProgress
                        val x = gap + i * (barWidth + gap)
                        val y = size.height - barHeight
                        
                        val barBrush = GradientFills.barFill(primary, primary.copy(0.3f), y, size.height)
                        
                        // Use Path for top rounded corners only
                        val path = Path().apply {
                            moveTo(x, size.height)
                            lineTo(x, y + 8.dp.toPx())
                            quadraticTo(x, y, x + 8.dp.toPx(), y)
                            lineTo(x + barWidth - 8.dp.toPx(), y)
                            quadraticTo(x + barWidth, y, x + barWidth, y + 8.dp.toPx())
                            lineTo(x + barWidth, size.height)
                            close()
                        }
                        
                        drawPath(path, barBrush)
                        
                        // Subtle glow
                        drawPath(path, primary.copy(alpha = 0.1f), alpha = 0.5f)
                    }
                }
            }
    ) {}
}
