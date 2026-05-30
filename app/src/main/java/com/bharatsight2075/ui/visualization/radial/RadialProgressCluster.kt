package com.bharatsight2075.ui.visualization.radial

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin

data class RadialSegment(
    val value: Float,
    val maxValue: Float,
    val color: Color
)

@Composable
fun RadialProgressCluster(
    modifier: Modifier = Modifier,
    segments: List<RadialSegment>,
    strokeWidth: Float = 20f
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = (size.minDimension - strokeWidth * 2) / 2
        
        segments.forEachIndexed { index, segment ->
            val sweepAngle = 360f * (segment.value / segment.maxValue)
            val startAngle = 0f
            
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(
                        segment.color.copy(alpha = 0.8f),
                        segment.color.copy(alpha = 0.3f)
                    )
                ),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth / (index + 1))
            )
        }
        
        drawCircle(
            color = RetroDarkColors.BlackElevated,
            radius = radius - strokeWidth * 1.5f,
            center = center,
            style = Fill
        )
    }
}