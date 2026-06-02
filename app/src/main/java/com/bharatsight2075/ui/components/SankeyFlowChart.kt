package com.bharatsight2075.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors

@Immutable
data class SankeyNode(val name: String, val value: Float, val color: Color)
@Immutable
data class SankeyLink(val source: Int, val target: Int, val volume: Float)

@Composable
fun SankeyFlowChart(
    modifier: Modifier = Modifier.fillMaxWidth().height(300.dp),
    sources: List<SankeyNode>,
    targets: List<SankeyNode>,
    links: List<SankeyLink>
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val nodeWidth = 40f
        
        val totalSourceValue = sources.sumOf { it.value.toDouble() }.toFloat()
        val totalTargetValue = targets.sumOf { it.value.toDouble() }.toFloat()
        
        var currentSourceY = 0f
        val sourcePositions = sources.map { node ->
            val nodeHeight = (node.value / totalSourceValue) * height
            val pos = Offset(0f, currentSourceY)
            drawRect(node.color, pos, androidx.compose.ui.geometry.Size(nodeWidth, nodeHeight))
            currentSourceY += nodeHeight + 10f
            pos to nodeHeight
        }

        var currentTargetY = 0f
        val targetPositions = targets.map { node ->
            val nodeHeight = (node.value / totalTargetValue) * height
            val pos = Offset(width - nodeWidth, currentTargetY)
            drawRect(node.color, pos, androidx.compose.ui.geometry.Size(nodeWidth, nodeHeight))
            currentTargetY += nodeHeight + 10f
            pos to nodeHeight
        }

        // Draw Links
        links.forEach { link ->
            val sourcePos = sourcePositions[link.source]
            val targetPos = targetPositions[link.target]
            
            val path = Path().apply {
                moveTo(nodeWidth, sourcePos.first.y + sourcePos.second / 2)
                cubicTo(
                    width / 2, sourcePos.first.y + sourcePos.second / 2,
                    width / 2, targetPos.first.y + targetPos.second / 2,
                    width - nodeWidth, targetPos.first.y + targetPos.second / 2
                )
            }
            
            drawPath(
                path = path,
                brush = Brush.horizontalGradient(
                    colors = listOf(sources[link.source].color.copy(alpha = 0.4f), targets[link.target].color.copy(alpha = 0.4f))
                ),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = (link.volume / totalSourceValue) * height)
            )
        }
    }
}
