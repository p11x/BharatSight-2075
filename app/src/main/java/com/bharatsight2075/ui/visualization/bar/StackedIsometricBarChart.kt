package com.bharatsight2075.ui.visualization.bar

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.Dimensions
import com.bharatsight2075.ui.theme.SciFiTheme

data class StackedBarData(
    val label: String,
    val segments: List<Pair<Float, Color>> // Value to Color
)

@Composable
fun StackedIsometricBarChart(
    modifier: Modifier = Modifier,
    data: List<StackedBarData>
) {
    val primaryColor = SciFiTheme.extendedColors.primary
    val maxVal = 100f
    
    Box(modifier = modifier.fillMaxWidth().height(250.dp).padding(top = 40.dp)) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            data.forEachIndexed { index, bar ->
                val totalValue = bar.segments.sumOf { it.first.toDouble() }.toFloat()
                
                var startAnimate by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { startAnimate = true }

                val animatedHeightFactor by animateFloatAsState(
                    targetValue = if (startAnimate) 1f else 0f,
                    animationSpec = spring(
                        dampingRatio = 0.6f,
                        stiffness = 200f,
                        visibilityThreshold = 0.001f
                    ),
                    label = "BarHeight_$index"
                )

                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.BottomCenter) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val barWidth = size.width * 0.6f
                            val isoOffset = barWidth * 0.4f
                            val maxHeight = size.height * 0.85f
                            val currentBarHeight = (totalValue / maxVal) * maxHeight * animatedHeightFactor
                            
                            val x = (size.width - barWidth) / 2f
                            var currentSegmentBottomY = size.height
                            
                            bar.segments.forEach { (value, color) ->
                                val segmentHeight = (value / totalValue) * currentBarHeight
                                val y = currentSegmentBottomY - segmentHeight
                                
                                // 1. Front Face
                                drawRect(
                                    color = color,
                                    topLeft = Offset(x, y),
                                    size = Size(barWidth, segmentHeight)
                                )
                                
                                // 2. Top Face (Isometric) with highlight
                                val highlightColor = lerp(color, Color.White, 0.3f)
                                val topPath = Path().apply {
                                    moveTo(x, y)
                                    lineTo(x + isoOffset, y - isoOffset)
                                    lineTo(x + barWidth + isoOffset, y - isoOffset)
                                    lineTo(x + barWidth, y)
                                    close()
                                }
                                drawPath(topPath, highlightColor)
                                
                                // 3. Side Face (Isometric)
                                val sidePath = Path().apply {
                                    moveTo(x + barWidth, y)
                                    lineTo(x + barWidth + isoOffset, y - isoOffset)
                                    lineTo(x + barWidth + isoOffset, y - isoOffset + segmentHeight)
                                    lineTo(x + barWidth, y + segmentHeight)
                                    close()
                                }
                                drawPath(sidePath, color.copy(alpha = 0.7f))
                                
                                currentSegmentBottomY = y
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimensions.PaddingMedium))
                    
                    Text(
                        text = bar.label,
                        style = SciFiTheme.typography.ChartCaption,
                        color = SciFiTheme.extendedColors.textSecondary,
                        modifier = Modifier.alpha(if (animatedHeightFactor > 0.8f) 1f else 0f)
                    )
                }
            }
        }
    }
}
