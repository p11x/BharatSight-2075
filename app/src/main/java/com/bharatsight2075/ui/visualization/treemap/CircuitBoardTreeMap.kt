package com.bharatsight2075.ui.visualization.treemap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

data class TreeMapNode(
    val label: String,
    val value: Float,
    val color: Color = RetroDarkColors.NeonCyan
)

@Composable
fun CircuitBoardTreeMap(
    modifier: Modifier = Modifier,
    nodes: List<TreeMapNode> = emptyList(),
    gap: Float = 4f
) {
    val safeNodes = nodes.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.BAR).map { TreeMapNode("M", it as Float) }
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        val count = safeNodes.size
        val w = size.width / count
        val totalVal = safeNodes.sumOf { it.value.toDouble() }.toFloat()

        safeNodes.forEachIndexed { i, node ->
            val rectWidth = w - 8.dp.toPx()
            val rectHeight = (node.value / totalVal.coerceAtLeast(1f)) * size.height * count
            
            drawRect(
                color = node.color.copy(alpha = 0.4f),
                topLeft = Offset(i * w + 4.dp.toPx(), (size.height - rectHeight) / 2),
                size = Size(rectWidth, rectHeight)
            )
            
            drawRect(
                color = node.color,
                topLeft = Offset(i * w + 4.dp.toPx(), (size.height - rectHeight) / 2),
                size = Size(rectWidth, rectHeight),
                style = androidx.compose.ui.graphics.drawscope.Stroke(1.dp.toPx())
            )
        }
    }
}
