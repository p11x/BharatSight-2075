package com.bharatsight2075.ui.visualization.bar

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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.bharatsight2075.ui.theme.RetroDarkColors

data class BarData(
    val label: String,
    val value: Float,
    val maxValue: Float,
    val color: Color = RetroDarkColors.NeonOrange
)

@Composable
fun IsometricBarChart(
    modifier: Modifier = Modifier,
    bars: List<BarData>
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
    ) {
        val barWidth = size.width * 0.1f
        val maxBarHeight = size.height * 0.7f
        val spacing = size.width / (bars.size + 1)
        val isoAngle = 30f
        
        bars.forEachIndexed { index, bar ->
            val height = (bar.value / bar.maxValue) * maxBarHeight
            val centerX = spacing * (index + 1)
            val baseY = size.height
            
            val topLeft = Offset(centerX - barWidth / 2, baseY - height)
            val topRight = Offset(centerX + barWidth / 2, baseY - height)
            val bottomLeft = Offset(centerX - barWidth / 2, baseY)
            val bottomRight = Offset(centerX + barWidth / 2, baseY)
            
            val isoOffset = barWidth / 4
            
            val leftFace = Path().apply {
                moveTo(bottomLeft.x, bottomLeft.y)
                lineTo(centerX - barWidth / 2 - isoOffset, baseY - height)
                lineTo(centerX - barWidth / 2 - isoOffset, baseY - height)
                lineTo(bottomLeft.x, bottomLeft.y)
                close()
            }
            
            val topFace = Path().apply {
                moveTo(centerX - barWidth / 2 - isoOffset, baseY - height)
                lineTo(centerX + barWidth / 2 - isoOffset, baseY - height)
                lineTo(topRight.x, baseY - height)
                lineTo(topLeft.x, baseY - height)
                close()
            }
            
            val rightFace = Path().apply {
                moveTo(topRight.x, baseY - height)
                lineTo(centerX + barWidth / 2 - isoOffset, baseY - height)
                lineTo(bottomRight.x, baseY)
                close()
            }
            
            drawPath(
                path = topFace,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        bar.color,
                        bar.color.copy(alpha = 0.7f)
                    )
                ),
                style = Fill
            )
            
            drawPath(
                path = leftFace,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        bar.color.copy(alpha = 0.8f),
                        bar.color.copy(alpha = 0.4f)
                    )
                ),
                style = Fill
            )
            
            drawPath(
                path = rightFace,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        bar.color.copy(alpha = 0.6f),
                        bar.color.copy(alpha = 0.2f)
                    )
                ),
                style = Fill
            )
            
            drawRect(
                color = RetroDarkColors.Black,
                topLeft = Offset(centerX - barWidth / 2, baseY - 2),
                size = Size(barWidth, 4f),
                style = Fill
            )
        }
        
        val gridPath = Path().apply {
            val spacing = 40f
            var x = 0f
            while (x <= size.width) {
                moveTo(x, 0f)
                lineTo(x + size.width / 2, size.height)
                x += spacing
            }
        }
        drawPath(
            path = gridPath,
            color = RetroDarkColors.GridLine,
            style = Stroke(
                width = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f))
            )
        )
    }
}