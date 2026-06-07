package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.components.ChartTooltip
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.lttb
import java.util.Locale

@Composable
fun GradientAreaChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    strokeColor: Color? = null,
    colors: Brush? = null,
    chartHeight: androidx.compose.ui.unit.Dp = 180.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val rawData = data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.AREA)
    val displayData = remember(rawData) { lttb(rawData, 50) }
    val maxVal = displayData.maxOrNull()?.coerceAtLeast(0.001f) ?: 1f
    
    val finalBrush = colors ?: Brush.verticalGradient(listOf((strokeColor ?: primaryColor).copy(alpha = 0.65f), Color.Transparent))
    val finalColor = strokeColor ?: primaryColor

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

    var touchPos by remember { mutableStateOf<Offset?>(null) }
    var tooltipValue by remember { mutableStateOf("") }
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    Box(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { touchPos = it },
                    onDrag = { change, _ -> 
                        touchPos = change.position
                        val paddingL = 32.dp.toPx()
                        val chartW = size.width - paddingL
                        val idx = (((change.position.x - paddingL) / chartW) * (displayData.size - 1))
                            .toInt().coerceIn(0, displayData.size - 1)
                        tooltipValue = String.format(Locale.getDefault(), "%.2f", displayData[idx])
                    },
                    onDragEnd = { touchPos = null },
                    onDragCancel = { touchPos = null }
                )
            }
            .drawWithCache {
                val fillBrush = finalBrush
                val path = Path()
                val fillPath = Path()
                val labelStyle = TextStyle(
                    color = Color.White.copy(0.4f),
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace
                )

                onDrawBehind {
                    path.reset()
                    fillPath.reset()
                    val count = displayData.size
                    val paddingL = 32.dp.toPx()
                    val paddingB = 24.dp.toPx()
                    val chartW = size.width - paddingL
                    val chartH = size.height - paddingB

                    displayData.forEachIndexed { index, value ->
                        val x = paddingL + (index.toFloat() / (count - 1).coerceAtLeast(1) * chartW)
                        val y = chartH - (value / maxVal * chartH)

                        if (index == 0) {
                            path.moveTo(x, y)
                            fillPath.moveTo(x, chartH)
                            fillPath.lineTo(x, y)
                        } else {
                            path.lineTo(x, y)
                            fillPath.lineTo(x, y)
                        }

                        if (index == count - 1) {
                            fillPath.lineTo(x, chartH)
                            fillPath.close()
                        }
                    }

                    clipRect(bottom = chartH * progress) {
                        drawPath(fillPath, fillBrush)

                        // Double-pass glow
                        drawPath(
                            path = path,
                            color = finalColor.copy(alpha = 0.2f * glowPulse),
                            style = Stroke(width = 2.dp.toPx() * 3.5f, cap = StrokeCap.Round)
                        )
                        drawPath(
                            path = path,
                            color = finalColor.copy(alpha = 0.9f),
                            style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Dot markers
                        displayData.forEachIndexed { index, value ->
                            val x = paddingL + (index.toFloat() / (count - 1).coerceAtLeast(1) * chartW)
                            val y = chartH - (value / maxVal * chartH)
                            drawCircle(Color.White, 3.dp.toPx(), Offset(x, y))
                            drawCircle(finalColor, 1.5.dp.toPx(), Offset(x, y))
                        }
                    }

                    // Axis Labels
                    drawText(textMeasurer, String.format(Locale.getDefault(), "%.1f", maxVal), Offset(4f, 0f), labelStyle)
                    drawText(textMeasurer, "0.0", Offset(4f, chartH - 12.dp.toPx()), labelStyle)
                    drawText(textMeasurer, "2025", Offset(paddingL, chartH + 4.dp.toPx()), labelStyle)
                    drawText(textMeasurer, "2075", Offset(size.width - 24.dp.toPx(), chartH + 4.dp.toPx()), labelStyle)

                    // Grid lines
                    repeat(4) { i ->
                        val gy = chartH * (i) / 4
                        drawLine(finalColor.copy(alpha = 0.1f), Offset(paddingL, gy), Offset(size.width, gy), strokeWidth = 1.dp.toPx())
                    }
                    drawLine(finalColor.copy(alpha = 0.3f), Offset(paddingL, 0f), Offset(paddingL, chartH), 1.dp.toPx())
                    drawLine(finalColor.copy(alpha = 0.3f), Offset(paddingL, chartH), Offset(size.width, chartH), 1.dp.toPx())

                    // Crosshair
                    touchPos?.let { pos ->
                        if (pos.x in paddingL..size.width && pos.y in 0f..chartH) {
                            drawLine(Color.White.copy(0.3f), Offset(pos.x, 0f), Offset(pos.x, chartH), 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)))
                            drawLine(Color.White.copy(0.3f), Offset(paddingL, pos.y), Offset(size.width, pos.y), 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)))
                        }
                    }
                }
            }
        ) { }

        touchPos?.let { pos ->
            Box(Modifier.offset(x = 40.dp, y = 10.dp)) {
                ChartTooltip(
                    label = "TELEMETRY",
                    value = tooltipValue,
                    onDismiss = { touchPos = null }
                )
            }
        }
    }
}
