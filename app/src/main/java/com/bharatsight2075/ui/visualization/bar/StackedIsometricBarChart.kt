package com.bharatsight2075.ui.visualization.bar

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

data class StackedBarData(
    val label: String,
    val segments: List<Pair<Float, Color>> // Value to Color
)

@Composable
fun StackedIsometricBarChart(
    modifier: Modifier = Modifier,
    data: List<StackedBarData> = emptyList()
) {
    val rawData = data.takeIf { it.isNotEmpty() } ?: listOf(
        StackedBarData("A", listOf(40f to Color.Cyan, 30f to Color.Magenta, 30f to Color.Yellow)),
        StackedBarData("B", listOf(50f to Color.Cyan, 20f to Color.Magenta, 30f to Color.Yellow))
    )
    val maxVal = 100f
    
    Box(modifier = modifier.fillMaxWidth().height(250.dp).padding(top = 40.dp)) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            rawData.forEach { bar ->
                val totalValue = bar.segments.sumOf { it.first.toDouble() }.toFloat()
                
                var startAnimate by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { startAnimate = true }

                val animatedHeightFactor by animateFloatAsState(
                    targetValue = if (startAnimate) 1f else 0f,
                    animationSpec = tween(1200, easing = EaseOutCubic),
                    label = "BarHeight"
                )

                Box(modifier = Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.BottomCenter) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val barWidth = size.width * 0.5f
                        val isoOffset = barWidth * 0.3f
                        val maxHeight = size.height * 0.8f
                        val currentBarHeight = (totalValue / maxVal) * maxHeight * animatedHeightFactor
                        
                        val x = (size.width - barWidth) / 2f
                        var currentSegmentBottomY = size.height
                        
                        bar.segments.forEach { (value, color) ->
                            val segmentHeight = (value / totalValue) * currentBarHeight
                            val y = currentSegmentBottomY - segmentHeight
                            
                            drawRect(color = color, topLeft = Offset(x, y), size = Size(barWidth, segmentHeight))
                            
                            val topPath = Path().apply {
                                moveTo(x, y); lineTo(x + isoOffset, y - isoOffset)
                                lineTo(x + barWidth + isoOffset, y - isoOffset); lineTo(x + barWidth, y); close()
                            }
                            drawPath(topPath, color.copy(alpha = 0.9f))
                            
                            val sidePath = Path().apply {
                                moveTo(x + barWidth, y); lineTo(x + barWidth + isoOffset, y - isoOffset)
                                lineTo(x + barWidth + isoOffset, y - isoOffset + segmentHeight); lineTo(x + barWidth, y + segmentHeight); close()
                            }
                            drawPath(sidePath, color.copy(alpha = 0.7f))
                            
                            currentSegmentBottomY = y
                        }
                    }
                }
            }
        }
    }
}
