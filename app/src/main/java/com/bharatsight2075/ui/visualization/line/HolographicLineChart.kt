package com.bharatsight2075.ui.visualization.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.bharatsight2075.ui.theme.RetroDarkColors

data class DataPoint(val x: Float, val y: Float)

@Composable
fun HolographicLineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
    lineColor: Color = RetroDarkColors.NeonOrange,
    glowColor: Color? = null
) {
    val glow = glowColor ?: lineColor
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clipToBounds()
    ) {
        val path = Path().apply {
            if (dataPoints.isNotEmpty()) {
                moveTo(dataPoints[0].x, dataPoints[0].y)
                for (i in 1 until dataPoints.size) {
                    cubicTo(
                        dataPoints[i - 1].x, dataPoints[i - 1].y,
                        (dataPoints[i - 1].x + dataPoints[i].x) / 2,
                        (dataPoints[i - 1].y + dataPoints[i].y) / 2,
                        dataPoints[i].x, dataPoints[i].y
                    )
                }
                lineTo(dataPoints.last().x, size.height)
                lineTo(dataPoints.first().x, size.height)
                close()
            }
        }
        
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    glow.copy(alpha = 0.4f),
                    Color.Transparent
                )
            ),
            style = Fill
        )
        
        drawPath(
            path = Path().apply {
                if (dataPoints.isNotEmpty()) {
                    moveTo(dataPoints[0].x, dataPoints[0].y)
                    for (i in 1 until dataPoints.size) {
                        cubicTo(
                            dataPoints[i - 1].x, dataPoints[i - 1].y,
                            (dataPoints[i - 1].x + dataPoints[i].x) / 2,
                            (dataPoints[i - 1].y + dataPoints[i].y) / 2,
                            dataPoints[i].x, dataPoints[i].y
                        )
                    }
                }
            },
            color = glow,
            style = Stroke(
                width = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 4f))
            )
        )
        
        for (point in dataPoints) {
            val offset = Offset(point.x, point.y)
            drawCircle(
                color = lineColor,
                radius = 6f,
                center = offset,
                style = Fill
            )
            drawCircle(
                color = glow.copy(alpha = 0.6f),
                radius = 12f,
                center = offset,
                style = Stroke(width = 1f)
            )
        }
    }
}