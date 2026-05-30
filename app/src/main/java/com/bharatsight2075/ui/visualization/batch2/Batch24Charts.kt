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
fun ChartDot(
    modifier: Modifier = Modifier,
    points: List<Pair<Float, Float>>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { points.forEach { (x, y) -> drawCircle(color = RetroDarkColors.NeonOrange, radius = 3f, center = Offset(x * size.width, y * size.height)) } } }

@Composable
fun ChartBar(
    modifier: Modifier = Modifier,
    values: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { val w = size.width / (values.size + 1); values.forEachIndexed { i, v -> drawRect(brush = Brush.verticalGradient(colors = listOf(RetroDarkColors.NeonCyan, RetroDarkColors.NeonMagenta)), topLeft = Offset(i * w, size.height * (1 - v)), size = Size(w * 0.8f, size.height * v), style = Fill) } } }

@Composable
fun ChartArc(
    modifier: Modifier = Modifier,
    values: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) { val c = Offset(size.width / 2, size.height / 2); values.forEachIndexed { i, v -> drawArc(color = RetroDarkColors.NeonGreen, startAngle = i * 45f, sweepAngle = v * 90f, useCenter = true, topLeft = Offset(c.x - 30, c.y - 30), size = Size(60f, 60f), style = Fill) } } }