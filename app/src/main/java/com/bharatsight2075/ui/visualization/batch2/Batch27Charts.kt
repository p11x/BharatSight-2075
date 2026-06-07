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
fun ChartDots(
    modifier: Modifier = Modifier,
    points: List<Pair<Float, Float>> = emptyList()
) {
    val safeData = points.takeIf { it.isNotEmpty() } ?: listOf(0.2f to 0.5f, 0.5f to 0.8f, 0.8f to 0.3f)
    Canvas(modifier = modifier.fillMaxWidth().height(200.dp)) {
        safeData.forEach { (x, y) ->
            drawCircle(
                brush = Brush.radialGradient(colors = listOf(RetroDarkColors.NeonOrange, Color.Transparent)),
                radius = 8.dp.toPx(),
                center = Offset(x * size.width, size.height - (y * size.height))
            )
        }
    }
}

@Composable
fun ChartBars2(
    modifier: Modifier = Modifier,
    values: List<Float> = emptyList()
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: listOf(40f, 70f, 50f, 90f)
    Canvas(modifier = modifier.fillMaxWidth().height(160.dp)) {
        val count = safeData.size
        val w = size.width / (count + 1)
        val maxVal = safeData.maxOrNull() ?: 100f
        safeData.forEachIndexed { i, v ->
            val h = (v / maxVal) * size.height
            drawRect(
                brush = Brush.verticalGradient(colors = listOf(RetroDarkColors.NeonCyan, RetroDarkColors.NeonOrange)),
                topLeft = Offset(i * w + w * 0.1f, size.height - h),
                size = Size(w * 0.8f, h),
                style = Fill
            )
        }
    }
}

@Composable
fun ChartArcs2(
    modifier: Modifier = Modifier,
    values: List<Float> = emptyList()
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: listOf(0.6f, 0.4f, 0.8f)
    Canvas(modifier = modifier.fillMaxWidth().height(180.dp)) {
        val c = center
        val r = size.minDimension * 0.4f
        safeData.forEachIndexed { i, v ->
            drawArc(
                brush = Brush.horizontalGradient(colors = listOf(RetroDarkColors.NeonMagenta, RetroDarkColors.NeonGreen)),
                startAngle = i * 120f,
                sweepAngle = v * 100f,
                useCenter = true,
                topLeft = Offset(c.x - r, c.y - r),
                size = Size(r * 2, r * 2),
                style = Fill
            )
        }
    }
}
