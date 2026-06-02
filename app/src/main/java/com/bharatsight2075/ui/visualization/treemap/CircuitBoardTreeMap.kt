package com.bharatsight2075.ui.visualization.treemap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.bharatsight2075.ui.theme.RetroDarkColors

data class TreeMapNode(
    val label: String,
    val value: Float,
    val color: Color = RetroDarkColors.NeonCyan
)

@Composable
fun CircuitBoardTreeMap(
    modifier: Modifier = Modifier,
    nodes: List<TreeMapNode>,
    gap: Float = 4f
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
    ) {
        val pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 5f))
        
        nodes.forEachIndexed { index, node ->
            val rectWidth = (size.width / nodes.size) * 0.8f
            val rectHeight = size.height * 0.6f
            val topLeft = Offset(
                x = (index * (size.width / nodes.size)) + (size.width / nodes.size - rectWidth) / 2,
                y = (size.height - rectHeight) / 2
            )
            
            val rectPath = Path().apply {
                addRect(androidx.compose.ui.geometry.Rect(topLeft, Size(rectWidth, rectHeight)))
            }
            
            drawPath(
                path = rectPath,
                color = node.color,
                style = Stroke(
                    width = 2f,
                    pathEffect = pathEffect
                )
            )
        }
    }
}