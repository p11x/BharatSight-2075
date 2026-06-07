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
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun ChartRadial(
    modifier: Modifier = Modifier,
    values: List<Float> = emptyList()
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: listOf(0.7f, 0.5f, 0.9f, 0.4f, 0.8f)
    Canvas(modifier = modifier.fillMaxWidth().height(200.dp)) {
        val c = center
        val baseR = size.minDimension / 2 * 0.8f
        safeData.forEachIndexed { i, v ->
            val angle = i * 360f / safeData.size
            val radius = baseR * v
            drawArc(
                brush = Brush.horizontalGradient(colors = listOf(RetroDarkColors.NeonCyan, RetroDarkColors.NeonOrange)),
                startAngle = angle, sweepAngle = 20f, useCenter = true,
                topLeft = Offset(c.x - radius, c.y - radius), size = Size(radius * 2, radius * 2),
                style = Fill
            )
        }
    }
}

@Composable
fun ChartStream(
    modifier: Modifier = Modifier,
    points: List<Float> = emptyList()
) {
    val safeData = points.takeIf { it.isNotEmpty() } ?: listOf(0.3f, 0.6f, 0.4f, 0.8f, 0.5f)
    Canvas(modifier = modifier.fillMaxWidth().height(180.dp)) {
        val count = safeData.size
        val p = Path().apply {
            safeData.forEachIndexed { i, v ->
                val x = i.toFloat() / (count - 1).coerceAtLeast(1) * size.width
                val y = size.height * (1 - v)
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(path = p, brush = Brush.verticalGradient(colors = listOf(RetroDarkColors.NeonCyan.copy(alpha = 0.4f), Color.Transparent)), style = Fill)
    }
}

@Composable
fun ChartLattice(
    modifier: Modifier = Modifier,
    lattice: List<List<Boolean>> = emptyList()
) {
    val safeData = lattice.takeIf { it.isNotEmpty() } ?: List(10) { List(10) { (it + it) % 3 == 0 } }
    Canvas(modifier = modifier.fillMaxWidth().height(200.dp)) {
        val rows = safeData.size
        val cols = safeData[0].size
        val cellW = size.width / cols
        val cellH = size.height / rows
        safeData.forEachIndexed { y, row ->
            row.forEachIndexed { x, on ->
                if (on) {
                    drawCircle(
                        color = RetroDarkColors.NeonOrange,
                        radius = 2.dp.toPx(),
                        center = Offset(x * cellW + cellW / 2, y * cellH + cellH / 2)
                    )
                }
            }
        }
    }
}
