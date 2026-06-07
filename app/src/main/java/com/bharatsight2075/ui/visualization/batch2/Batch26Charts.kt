package com.bharatsight2075.ui.visualization.batch2

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChartRing(
    modifier: Modifier = Modifier,
    values: List<Float> = emptyList()
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: listOf(0.8f, 0.6f, 0.4f)
    Canvas(modifier = modifier.fillMaxWidth().height(180.dp)) {
        val c = center
        val baseR = size.minDimension / 2 * 0.7f
        safeData.forEachIndexed { i, v ->
            val r = baseR - i * 15.dp.toPx()
            drawArc(
                color = RetroDarkColors.NeonMagenta.copy(alpha = 0.3f),
                startAngle = -90f, sweepAngle = 360f, useCenter = false,
                topLeft = Offset(c.x - r, c.y - r), size = Size(r * 2, r * 2),
                style = Stroke(10.dp.toPx())
            )
            drawArc(
                color = RetroDarkColors.NeonMagenta,
                startAngle = -90f, sweepAngle = v * 360f, useCenter = false,
                topLeft = Offset(c.x - r, c.y - r), size = Size(r * 2, r * 2),
                style = Stroke(10.dp.toPx())
            )
        }
    }
}

@Composable
fun ChartWave(
    modifier: Modifier = Modifier,
    points: List<Float> = emptyList()
) {
    val safeData = points.takeIf { it.isNotEmpty() } ?: listOf(0f, 0.5f, -0.3f, 0.8f, 0.2f)
    Canvas(modifier = modifier.fillMaxWidth().height(120.dp)) {
        val count = safeData.size
        val p = Path().apply {
            safeData.forEachIndexed { i, v ->
                val x = i.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                val y = size.height / 2 - (v * size.height / 2)
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }
        drawPath(path = p, color = RetroDarkColors.NeonOrange, style = Stroke(2.dp.toPx()))
    }
}

@Composable
fun ChartSpiral(
    modifier: Modifier = Modifier,
    points: List<Pair<Float, Float>> = emptyList()
) {
    val safeData = points.takeIf { it.isNotEmpty() } ?: List(20) { it * 18f to it / 20f }
    Canvas(modifier = modifier.fillMaxWidth().height(260.dp)) {
        val c = center
        safeData.forEach { (a, r) ->
            val angle = Math.toRadians(a.toDouble())
            val radius = r * size.minDimension / 2 * 0.9f
            drawCircle(
                color = RetroDarkColors.NeonGreen,
                radius = 3.dp.toPx(),
                center = Offset(c.x + (radius * cos(angle)).toFloat(), c.y + (radius * sin(angle)).toFloat())
            )
        }
    }
}
