package com.bharatsight2075.ui.visualization.batch2

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
import androidx.compose.ui.graphics.drawscope.Fill
import com.bharatsight2075.ui.theme.RetroDarkColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ChartRing(
    modifier: Modifier = Modifier,
    values: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) { val c = Offset(size.width / 2, size.height / 2); val r = size.minDimension / 2 * 0.7f; values.forEachIndexed { i, v -> drawArc(color = RetroDarkColors.NeonMagenta, startAngle = i * 30f, sweepAngle = 30f, useCenter = false, topLeft = Offset(c.x - r, c.y - r), size = Size(r * 2, r * 2), style = Fill) } } }

@Composable
fun ChartWave(
    modifier: Modifier = Modifier,
    points: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { val p = Path().apply { points.forEachIndexed { i, v -> val x = i * size.width / points.size; val y = size.height / 2 + v * size.height / 4; if (i == 0) moveTo(x, y) else lineTo(x, y) } }; drawPath(path = p, brush = Brush.horizontalGradient(colors = listOf(RetroDarkColors.NeonOrange, RetroDarkColors.NeonCyan)), style = Fill) } }

@Composable
fun ChartSpiral(
    modifier: Modifier = Modifier,
    points: List<Pair<Float, Float>>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) { val c = Offset(size.width / 2, size.height / 2); points.forEach { (a, r) -> drawCircle(color = RetroDarkColors.NeonGreen, radius = 3f, center = Offset(c.x + r * c.x * cos(Math.toRadians(a.toDouble())).toFloat(), c.y + r * c.y * sin(Math.toRadians(a.toDouble())).toFloat())) } } }