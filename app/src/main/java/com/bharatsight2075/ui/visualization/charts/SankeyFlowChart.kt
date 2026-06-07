package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.SciFiTheme

data class SankeyLink(
    val fromIndex: Int, 
    val toIndex: Int, 
    val weight: Float, 
    val color: Color? = null,
    val source: Int = 0, // Compatibility
    val target: Int = 0, // Compatibility
    val volume: Float = 0f // Compatibility
)

data class SankeyNode(val name: String, val value: Float, val color: Color)

@Composable
fun SankeyFlowChart(
    modifier: Modifier = Modifier,
    links: List<SankeyLink> = emptyList(),
    data: List<Float> = emptyList(),
    sources: List<SankeyNode> = emptyList(), 
    targets: List<SankeyNode> = emptyList(), 
    nodes: List<Any>? = null, // Compatibility
    flows: List<Any>? = null, // Compatibility
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val finalLinks = links.takeIf { it.isNotEmpty() } 
        ?: data.chunked(3).map { SankeyLink(it.getOrElse(0){0f}.toInt(), it.getOrElse(1){0f}.toInt(), it.getOrElse(2){0f}) }.takeIf { it.isNotEmpty() }
        ?: listOf(
            SankeyLink(0, 0, 40f), SankeyLink(0, 1, 20f), SankeyLink(1, 1, 30f), SankeyLink(2, 0, 10f)
        )
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(1500, easing = EaseInOutCubic),
        label = "SankeyAnim"
    )
    LaunchedEffect(Unit) { triggered = true }

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val nodeW = 15.dp.toPx()
        val nodeH = 40.dp.toPx()
        val spacing = 30.dp.toPx()
        
        // Left Nodes
        val leftCount = if (sources.isNotEmpty()) sources.size else 3
        repeat(leftCount) { i ->
            val y = i * (nodeH + spacing) + spacing
            val color = sources.getOrNull(i)?.color ?: primaryColor
            drawRect(color, Offset(0f, y), Size(nodeW, nodeH))
        }
        
        // Right Nodes
        val rightCount = if (targets.isNotEmpty()) targets.size else 2
        repeat(rightCount) { i ->
            val y = i * (nodeH * 1.5f + spacing) + spacing * 2
            val color = targets.getOrNull(i)?.color ?: primaryColor
            drawRect(color, Offset(size.width - nodeW, y), Size(nodeW, nodeH))
        }
        
        // Bezier Links
        finalLinks.forEach { link ->
            val fromIdx = if (link.fromIndex != 0 || link.weight != 0f) link.fromIndex else link.source
            val toIdx = if (link.toIndex != 0 || link.weight != 0f) link.toIndex else link.target
            val wVal = if (link.weight != 0f) link.weight else link.volume
            
            val startY = fromIdx * (nodeH + spacing) + spacing + nodeH/2
            val endY = toIdx * (nodeH * 1.5f + spacing) + spacing * 2 + nodeH/2
            val w = wVal.dp.toPx() * progress
            
            val path = Path().apply {
                val fromX = nodeW
                val toX = size.width - nodeW
                val midX = (fromX + toX) / 2
                
                moveTo(fromX, startY - w/2)
                cubicTo(midX, startY - w/2, midX, endY - w/2, toX, endY - w/2)
                lineTo(toX, endY + w/2)
                cubicTo(midX, endY + w/2, midX, startY + w/2, fromX, startY + w/2)
                close()
            }
            
            val color = link.color ?: primaryColor.copy(alpha = 0.3f)
            drawPath(path, color)
            drawPath(path, color.copy(alpha = 0.8f), style = Stroke(1.dp.toPx()))
        }
    }
}
