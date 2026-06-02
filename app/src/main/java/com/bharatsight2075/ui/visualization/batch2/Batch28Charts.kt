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
fun ChartRadial(
    modifier: Modifier = Modifier,
    values: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) { val c = Offset(size.width / 2, size.height / 2); val r = size.minDimension / 2 * 0.7f; values.forEachIndexed { i, v -> val a = i * 360f / values.size; drawArc(brush = Brush.horizontalGradient(colors = listOf(RetroDarkColors.NeonCyan, RetroDarkColors.NeonOrange)), startAngle = a, sweepAngle = 15f, useCenter = true, topLeft = Offset(c.x - r * v, c.y - r * v), size = Size(r * 2 * v, r * 2 * v), style = Fill) } } }

@Composable
fun ChartStream(
    modifier: Modifier = Modifier,
    points: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { val p = Path().apply { points.forEachIndexed { i, v -> val x = i * size.width / points.size; val y = size.height * (1 - v); if (i == 0) moveTo(x, y) else lineTo(x, y) } }; drawPath(path = p, brush = Brush.verticalGradient(colors = listOf(RetroDarkColors.NeonCyan.copy(alpha = 0.4f), Color.Transparent)), style = Fill) } }

@Composable
fun ChartLattice(
    modifier: Modifier = Modifier,
    lattice: List<List<Boolean>>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) { lattice.forEachIndexed { y, row -> row.forEachIndexed { x, on -> if (on) drawCircle(color = RetroDarkColors.NeonOrange, radius = 2f, center = Offset(x * size.width / row.size, y * size.height / lattice.size)) } } } }