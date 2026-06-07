package com.bharatsight2075.ui.visualization.radial

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors

data class RadialSegment(
    val value: Float,
    val maxValue: Float,
    val color: Color
)

@Composable
fun RadialProgressCluster(
    modifier: Modifier = Modifier,
    segments: List<RadialSegment> = emptyList(),
    strokeWidth: Float = 30f
) {
    val safeSegments = segments.takeIf { it.isNotEmpty() } ?: listOf(
        RadialSegment(0.8f, 1f, Color.Cyan),
        RadialSegment(0.6f, 1f, Color.Magenta),
        RadialSegment(0.4f, 1f, Color.Yellow)
    )
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        val baseR = size.minDimension / 2 * 0.8f
        safeSegments.forEachIndexed { i, s ->
            val r = baseR - i * 12.dp.toPx()
            drawArc(
                color = s.color.copy(alpha = 0.2f),
                startAngle = -90f, sweepAngle = 360f, useCenter = false,
                topLeft = Offset(center.x - r, center.y - r), size = Size(r * 2, r * 2),
                style = Stroke(width = 8.dp.toPx())
            )
            drawArc(
                color = s.color,
                startAngle = -90f, sweepAngle = (s.value / s.maxValue) * 360f, useCenter = false,
                topLeft = Offset(center.x - r, center.y - r), size = Size(r * 2, r * 2),
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}
