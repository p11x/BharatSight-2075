package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType

@Composable
fun HeatmapGridChart(
    modifier: Modifier = Modifier,
    data: List<List<Float>> = emptyList(),
    matrix: List<List<Float>> = emptyList(),
    rowLabels: List<String> = emptyList(),
    colLabels: List<String> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 220.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    @Suppress("UNCHECKED_CAST")
    val finalMatrix = matrix.takeIf { it.isNotEmpty() } ?: data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockData(ChartType.HEATMAP) as List<List<Float>>
    
    var triggered by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1200, easing = EaseOutCubic),
        label = "chartProgress"
    )
    val glowPulse by rememberInfiniteTransition(label = "glow").animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "gp"
    )
    LaunchedEffect(Unit) { triggered = true }

    var hoveredCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        color = Color.White.copy(0.4f),
        fontSize = 7.sp,
        fontFamily = FontFamily.Monospace
    )

    val coldColor = Color(0xFF001F3F)
    val hotColor = primaryColor

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(chartHeight)
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                // Basic hit detection (needs padding info)
            }
        }
    ) {
        val rows = finalMatrix.size
        val cols = finalMatrix.firstOrNull()?.size ?: 1
        
        val paddingL = 40.dp.toPx()
        val paddingT = 20.dp.toPx()
        val gridW = size.width - paddingL
        val gridH = size.height - paddingT
        
        val cellW = (gridW / cols).coerceAtLeast(1f)
        val cellH = (gridH / rows).coerceAtLeast(1f)
        
        // Column Labels (Top)
        repeat(cols) { c ->
            val label = colLabels.getOrNull(c) ?: "C$c"
            val result = textMeasurer.measure(label, labelStyle)
            drawText(result, topLeft = Offset(paddingL + c * cellW + (cellW - result.size.width) / 2, 4.dp.toPx()))
        }

        finalMatrix.forEachIndexed { r, row ->
            // Row Labels (Left)
            val rLabel = rowLabels.getOrNull(r) ?: "R$r"
            val rResult = textMeasurer.measure(rLabel, labelStyle)
            drawText(rResult, topLeft = Offset(4.dp.toPx(), paddingT + r * cellH + (cellH - rResult.size.height) / 2))

            row.forEachIndexed { c, value ->
                val alpha = value * progress
                val color = lerp(coldColor, hotColor, value).copy(alpha = alpha)
                val x = paddingL + c * cellW
                val y = paddingT + r * cellH
                
                drawRect(
                    color = color,
                    topLeft = Offset(x, y),
                    size = Size(cellW - 1.dp.toPx(), cellH - 1.dp.toPx())
                )
                
                // Black cell border
                drawRect(
                    color = Color.Black.copy(0.3f),
                    topLeft = Offset(x, y),
                    size = Size(cellW - 1.dp.toPx(), cellH - 1.dp.toPx()),
                    style = Stroke(0.5.dp.toPx())
                )
                
                if (value > 0.8f) {
                    drawRect(
                        color = Color.White.copy(alpha = 0.15f * glowPulse * progress),
                        topLeft = Offset(x, y),
                        size = Size(cellW - 1.dp.toPx(), 2.dp.toPx())
                    )
                }
            }
        }
    }
}
