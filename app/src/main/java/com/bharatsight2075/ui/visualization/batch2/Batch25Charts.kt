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
fun ChartLine(
    modifier: Modifier = Modifier,
    points: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { val p = Path().apply { points.forEachIndexed { i, v -> val x = i * size.width / points.size; val y = size.height * (1 - v); if (i == 0) moveTo(x, y) else lineTo(x, y) } }; drawPath(path = p, color = RetroDarkColors.NeonOrange) } }

@Composable
fun ChartFill(
    modifier: Modifier = Modifier,
    values: List<Float>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(16 / 9f)) { val p = Path().apply { values.forEachIndexed { i, v -> val x = i * size.width / values.size; val y = size.height * (1 - v); if (i == 0) moveTo(x, y) else lineTo(x, y) } }; drawPath(path = p, brush = Brush.verticalGradient(colors = listOf(RetroDarkColors.NeonCyan.copy(alpha = 0.5f), Color.Transparent)), style = Fill) } }

@Composable
fun ChartGrid(
    modifier: Modifier = Modifier,
    grid: List<List<Float>>
) { Canvas(modifier = modifier.fillMaxWidth().aspectRatio(1f)) { grid.forEachIndexed { y, row -> row.forEachIndexed { x, v -> drawRect(color = RetroDarkColors.NeonGreen.copy(alpha = v), topLeft = Offset(x * size.width / row.size, y * size.height / grid.size), size = Size(size.width / row.size, size.height / grid.size), style = Fill) } } } }