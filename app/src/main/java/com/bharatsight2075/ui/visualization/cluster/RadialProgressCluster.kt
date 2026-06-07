package com.bharatsight2075.ui.visualization.cluster

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun RadialProgressCluster(
    modifier: Modifier = Modifier,
    label: String = "M",
    value: Float = 0.7f,
    color: Color = RetroDarkColors.NeonCyan
) {
    Canvas(modifier = modifier.fillMaxWidth().height(180.dp)) {
        val c = center
        val r = size.minDimension / 2 * 0.8f
        drawCircle(color = color.copy(alpha = 0.2f), radius = r, style = Stroke(12.dp.toPx()))
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * value,
            useCenter = false,
            topLeft = Offset(c.x - r, c.y - r),
            size = Size(r * 2, r * 2),
            style = Stroke(12.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}
