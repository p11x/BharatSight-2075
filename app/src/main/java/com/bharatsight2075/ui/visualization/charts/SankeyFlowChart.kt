package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme

data class SankeyFlow(val from: Int, val to: Int, val volume: Float)
data class SankeyNode(val name: String, val value: Float, val color: Color)

/**
 * C14. SankeyFlowChart
 * Node to node flow bands, gradient-filled, animated flow.
 */
@Composable
fun SankeyFlowChart(
    nodes: List<SankeyNode>,
    flows: List<SankeyFlow>,
    modifier: Modifier = Modifier,
    animated: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1200, easing = EaseOutCubic),
        label = "SankeyAnim"
    )
    
    val currentProgress = if (animated) progress else 1f

    Canvas(
        modifier = modifier.drawWithCache {
            onDrawBehind {
                val nodeWidth = 12.dp.toPx()
                val columnSpacing = size.width - 2 * nodeWidth
                
                val totalVolume = nodes.sumOf { it.value.toDouble() }.toFloat()
                
                // Logic to calculate Y positions for source and destination columns
                var sourceY = 0f
                val sourceOffsets = nodes.map { node ->
                    val h = (node.value / totalVolume) * size.height
                    val offset = Offset(0f, sourceY)
                    sourceY += h + 8.dp.toPx()
                    offset to h
                }

                var destY = 0f
                val destOffsets = nodes.map { node ->
                    val h = (node.value / totalVolume) * size.height
                    val offset = Offset(size.width - nodeWidth, destY)
                    destY += h + 8.dp.toPx()
                    offset to h
                }

                // Draw Flow Bands
                flows.forEach { flow ->
                    val source = sourceOffsets[flow.from]
                    val dest = destOffsets[flow.to]
                    
                    val path = Path().apply {
                        moveTo(nodeWidth, source.first.y + source.second / 2)
                        cubicTo(
                            nodeWidth + columnSpacing / 2, source.first.y + source.second / 2,
                            nodeWidth + columnSpacing / 2, dest.first.y + dest.second / 2,
                            size.width - nodeWidth, dest.first.y + dest.second / 2
                        )
                    }
                    
                    val strokeWidth = (flow.volume / totalVolume) * size.height * currentProgress
                    
                    drawPath(
                        path = path,
                        brush = Brush.horizontalGradient(
                            listOf(nodes[flow.from].color.copy(alpha = 0.4f), nodes[flow.to].color.copy(alpha = 0.4f))
                        ),
                        style = Stroke(width = strokeWidth)
                    )
                }

                // Draw Node Rects
                nodes.forEachIndexed { i, node ->
                    drawRect(
                        color = node.color,
                        topLeft = sourceOffsets[i].first,
                        size = Size(nodeWidth, sourceOffsets[i].second)
                    )
                    drawRect(
                        color = node.color,
                        topLeft = destOffsets[i].first,
                        size = Size(nodeWidth, destOffsets[i].second)
                    )
                }
            }
        }
    ) {}
}

@Preview
@Composable
fun PreviewSankeyFlowChart() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        SankeyFlowChart(
            nodes = listOf(
                SankeyNode("FDI", 100f, Color(0xFF00F5FF)),
                SankeyNode("EXPORTS", 80f, Color(0xFFFF6B35)),
                SankeyNode("SERVICES", 120f, Color(0xFF39FF14))
            ),
            flows = listOf(
                SankeyFlow(0, 0, 50f),
                SankeyFlow(1, 1, 40f),
                SankeyFlow(2, 2, 60f)
            ),
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )
    }
}
