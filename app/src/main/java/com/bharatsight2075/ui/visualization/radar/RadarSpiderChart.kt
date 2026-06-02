package com.bharatsight2075.ui.visualization.radar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin

data class RadarDataSet(
    val values: List<Float>,
    val maxValues: List<Float>,
    val label: String,
    val color: Color
)

@Composable
fun RadarSpiderChart(
    modifier: Modifier = Modifier,
    dataSets: List<RadarDataSet>,
    labels: List<String>
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val maxRadius = size.minDimension / 2 * 0.7f
        val sides = labels.size
        
        val gridPath = Path().apply {
            repeat(5) { level ->
                val radius = maxRadius * ((level + 1) / 5f)
                val startAngle = -90f
                for (i in 0 until sides) {
                    val angle = startAngle + (i * 360f / sides)
                    val x = center.x + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = center.y + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()
            }
        }
        
        drawPath(
            path = gridPath,
            color = RetroDarkColors.TextDisabled.copy(alpha = 0.3f),
            style = Stroke(
                width = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f))
            )
        )
        
        // Draw Axis Lines & Labels
        for (i in 0 until sides) {
            val angle = -90f + (i * 360f / sides)
            val x = center.x + maxRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = center.y + maxRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
            drawLine(
                color = RetroDarkColors.TextDisabled.copy(alpha = 0.2f),
                start = center,
                end = Offset(x, y),
                strokeWidth = 1f
            )
            
            // Draw Text Label
            val labelRadius = maxRadius + 20.dp.toPx()
            val tx = center.x + labelRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val ty = center.y + labelRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
            
            val textLayoutResult = textMeasurer.measure(
                text = labels[i],
                style = TextStyle(color = RetroDarkColors.TextSecondary, fontSize = 10.sp)
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(tx - textLayoutResult.size.width / 2, ty - textLayoutResult.size.height / 2)
            )
        }
        
        dataSets.forEach { dataSet ->
            val polygonPath = Path().apply {
                dataSet.values.forEachIndexed { index, value ->
                    val angle = -90f + (index * 360f / sides)
                    val radius = maxRadius * (value / dataSet.maxValues.getOrElse(index) { 1f })
                    val x = center.x + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = center.y + radius * sin(Math.toRadians(angle.toDouble())).toFloat()
                    if (index == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()
            }
            
            drawPath(
                path = polygonPath,
                color = dataSet.color,
                style = Stroke(width = 2.dp.toPx())
            )
            
            drawPath(
                path = polygonPath,
                brush = Brush.radialGradient(
                    colors = listOf(
                        dataSet.color.copy(alpha = 0.4f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = maxRadius
                ),
                style = Fill
            )
        }
    }
}