package com.bharatsight2075.ui.visualization.charts

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import kotlin.math.*

data class RadarDataSet(val label: String, val values: List<Float>, val color: Color)

@Composable
fun RadarPolygonChart(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(),
    values: List<Float> = emptyList(),
    labels: List<String> = emptyList(),
    dataSets: List<RadarDataSet> = emptyList(),
    chartHeight: androidx.compose.ui.unit.Dp = 200.dp,
    primaryColor: Color = SciFiTheme.extendedColors.primary
) {
    val safeData = values.takeIf { it.isNotEmpty() } ?: data.takeIf { it.isNotEmpty() } ?: ChartMockData.generateMockFor(ChartType.RADAR)
    val axisCount = if (labels.isNotEmpty()) labels.size else safeData.size
    
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

    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        color = Color.White.copy(0.4f),
        fontSize = 7.sp,
        fontFamily = FontFamily.Monospace
    )

    Canvas(modifier = modifier.fillMaxWidth().height(chartHeight)) {
        val radius = size.minDimension / 2 * 0.7f
        
        // Background Web Rings (20%, 40%, 60%, 80%, 100%)
        repeat(5) { rIdx ->
            val r = radius * (rIdx + 1) / 5
            val path = Path()
            for (i in 0 until axisCount) {
                val angle = -PI / 2 + i * (2 * PI / axisCount)
                val x = center.x + r * cos(angle).toFloat()
                val y = center.y + r * sin(angle).toFloat()
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
            drawPath(path, Color.White.copy(alpha = 0.05f), style = Stroke(1.dp.toPx()))
        }
        
        // Axes and Labels
        for (i in 0 until axisCount) {
            val angle = -PI / 2 + i * (2 * PI / axisCount)
            val x = center.x + radius * cos(angle).toFloat()
            val y = center.y + radius * sin(angle).toFloat()
            drawLine(primaryColor.copy(alpha = 0.15f), center, Offset(x, y), strokeWidth = 1.dp.toPx())
            
            // Axis Label at vertex tip
            val labelText = labels.getOrNull(i) ?: "AXIS ${i+1}"
            val result = textMeasurer.measure(labelText, labelStyle)
            val labelR = radius + 12.dp.toPx()
            val lx = center.x + labelR * cos(angle).toFloat() - result.size.width / 2
            val ly = center.y + labelR * sin(angle).toFloat() - result.size.height / 2
            drawText(result, topLeft = Offset(lx, ly))
        }
        
        // Data Polygon
        val dataPath = Path()
        safeData.take(axisCount).forEachIndexed { i, value ->
            val r = radius * value * progress
            val angle = -PI / 2 + i * (2 * PI / axisCount)
            val x = center.x + r * cos(angle).toFloat()
            val y = center.y + r * sin(angle).toFloat()
            if (i == 0) dataPath.moveTo(x, y) else dataPath.lineTo(x, y)
            
            drawCircle(primaryColor, 3.dp.toPx() * progress, Offset(x, y))
        }
        dataPath.close()
        
        // Fill
        drawPath(dataPath, primaryColor.copy(alpha = 0.15f))
        
        // Border and Glow
        drawPath(
            path = dataPath,
            color = primaryColor.copy(alpha = 0.2f * glowPulse),
            style = Stroke(width = 2.dp.toPx() * 3.5f)
        )
        drawPath(
            path = dataPath,
            color = primaryColor.copy(alpha = 0.8f),
            style = Stroke(width = 1.5.dp.toPx())
        )
    }
}
