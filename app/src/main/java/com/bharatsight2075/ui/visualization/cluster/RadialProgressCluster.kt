package com.bharatsight2075.ui.visualization.cluster

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun RadialProgressCluster(
    modifier: Modifier = Modifier,
    label: String,
    value: Float,
    color: Color = RetroDarkColors.NeonCyan
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = RetroDarkColors.GridLine,
                    style = Stroke(width = 4.dp.toPx())
                )
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = 360f * value,
                    useCenter = false,
                    style = Stroke(width = 4.dp.toPx())
                )
            }
            Text(
                text = "${(value * 100).toInt()}%",
                color = color,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = RetroDarkColors.TextSecondary,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
