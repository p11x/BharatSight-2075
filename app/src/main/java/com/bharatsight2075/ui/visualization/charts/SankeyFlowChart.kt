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

data class SankeyFlowData(val fromY: Float, val toY: Float, val width: Float, val brush: Brush)

/**
 * C14. SankeyFlowChart
 * FIXED: Vertical distribution of bands.
 */
@Composable
fun SankeyFlowChart(
    nodes: List<Any>, 
    flows: List<Any>,
    modifier: Modifier = Modifier,
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    animated: Boolean = true
) {
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "SankeyAnim"
    )
    LaunchedEffect(Unit) { triggered = true }
    
    val currentProgress = if (animated) progress else 1f
    val colors = SciFiTheme.extendedColors

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight)
            .drawWithCache {
                val nodeWidth = 12.dp.toPx()
                val toX = size.width - nodeWidth
                val ctrlX = size.width / 2
                
                // Distribute flows vertically
                val flowItems = listOf(
                    SankeyFlowData(size.height * 0.25f, size.height * 0.5f, 32.dp.toPx(), Brush.linearGradient(listOf(colors.primary, colors.accent))),
                    SankeyFlowData(size.height * 0.5f, size.height * 0.25f, 24.dp.toPx(), Brush.linearGradient(listOf(colors.accent, colors.primary))),
                    SankeyFlowData(size.height * 0.75f, size.height * 0.75f, 18.dp.toPx(), Brush.linearGradient(listOf(colors.primary, colors.accent)))
                )

                onDrawBehind {
                    flowItems.forEach { flow ->
                        val flowWidth = flow.width * currentProgress
                        if (flowWidth < 1f) return@forEach
                        
                        val path = Path().apply {
                            moveTo(nodeWidth, flow.fromY - flowWidth / 2)
                            cubicTo(
                                ctrlX, flow.fromY - flowWidth / 2,
                                ctrlX, flow.toY - flowWidth / 2,
                                toX, flow.toY - flowWidth / 2
                            )
                            lineTo(toX, flow.toY + flowWidth / 2)
                            cubicTo(
                                ctrlX, flow.toY + flowWidth / 2,
                                ctrlX, flow.fromY + flowWidth / 2,
                                nodeWidth, flow.fromY + flowWidth / 2
                            )
                            close()
                        }
                        drawPath(path, flow.brush, alpha = 0.4f)
                        
                        // Border Glow
                        drawPath(path, flow.brush, style = androidx.compose.ui.graphics.drawscope.Stroke(1.dp.toPx()), alpha = 0.6f)
                    }

                    // Nodes
                    drawRoundRect(colors.primary, Offset(0f, size.height * 0.15f), Size(nodeWidth, size.height * 0.7f), CornerRadius(4f))
                    drawRoundRect(colors.accent, Offset(toX, size.height * 0.15f), Size(nodeWidth, size.height * 0.7f), CornerRadius(4f))
                }
            }
    ) {}
}
