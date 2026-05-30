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

@Composable
fun ChartDots(
    modifier: Modifier = Modifier,
    points: List<Pair<Float, Float>>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { points.forEach { (x, y) -> drawCircle(brush = Brush.radialGradient(colors = listOf(RetroDarkColors.NeonOrange, Color.Transparent)), radius = 5f, center = Offset(x * size.width, y * size.height)) } } }

@Composable
fun ChartBars2(
    modifier: Modifier = Modifier,
    values: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { val w = size.width / (values.size + 1); values.forEachIndexed { i, v -> drawRect(brush = Brush.verticalGradient(colors = listOf(RetroDarkColors.NeonCyan, RetroDarkColors.NeonOrange)), topLeft = Offset(i * w, size.height * (1 - v)), size = Size(w * 0.8f, size.height * v), style = Fill) } } }

@Composable
fun ChartArcs2(
    modifier: Modifier = Modifier,
    values: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) { val c = Offset(size.width / 2, size.height / 2); values.forEachIndexed { i, v -> drawArc(brush = Brush.horizontalGradient(colors = listOf(RetroDarkColors.NeonMagenta, RetroDarkColors.NeonGreen)), startAngle = i * 60f, sweepAngle = v * 60f, useCenter = true, topLeft = Offset(c.x - 40, c.y - 40), size = Size(80f, 80f), style = Fill) } } }