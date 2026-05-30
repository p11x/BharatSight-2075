package com.bharatsight2075.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlin.math.roundToInt

data class StatePolygon(
    val name: String,
    val points: List<Offset>,
    val gsdp: String,
    val growth: String,
    val topSector: String
)

@Composable
fun IndiaMapCanvas(
    modifier: Modifier = Modifier,
    onStateDrillDown: (String) -> Unit
) {
    val primaryColor = SciFiTheme.extendedColors.primary
    val accentColor = SciFiTheme.extendedColors.accent
    
    var selectedState by remember { mutableStateOf<StatePolygon?>(null) }
    var tapOffset by remember { mutableStateOf(Offset.Zero) }

    val statePolygons = remember {
        listOf(
            StatePolygon("Maharashtra", listOf(Offset(0.35f, 0.55f), Offset(0.45f, 0.52f), Offset(0.52f, 0.60f), Offset(0.40f, 0.68f)), "₹38.8 Lakh Cr", "8.2%", "Services"),
            StatePolygon("Tamil Nadu", listOf(Offset(0.48f, 0.78f), Offset(0.55f, 0.75f), Offset(0.58f, 0.88f), Offset(0.50f, 0.92f)), "₹28.3 Lakh Cr", "8.1%", "Manufacturing"),
            StatePolygon("Karnataka", listOf(Offset(0.42f, 0.68f), Offset(0.48f, 0.65f), Offset(0.52f, 0.75f), Offset(0.45f, 0.80f)), "₹25.4 Lakh Cr", "9.4%", "IT/BT"),
            StatePolygon("Gujarat", listOf(Offset(0.28f, 0.48f), Offset(0.38f, 0.45f), Offset(0.40f, 0.55f), Offset(0.30f, 0.58f)), "₹25.6 Lakh Cr", "10.1%", "Industry"),
            StatePolygon("Delhi", listOf(Offset(0.44f, 0.32f), Offset(0.48f, 0.30f), Offset(0.50f, 0.35f), Offset(0.46f, 0.37f)), "₹11.2 Lakh Cr", "7.5%", "Services")
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "MapAnims")
    val arcProgress by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing)), label = "Arc"
    )

    Box(modifier = modifier.fillMaxWidth().aspectRatio(0.83f)) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val normalizedX = offset.x / size.width
                        val normalizedY = offset.y / size.height
                        val tapped = statePolygons.find { poly ->
                            containsPoint(poly.points, Offset(normalizedX, normalizedY))
                        }
                        selectedState = tapped
                        tapOffset = offset
                    }
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw States
            statePolygons.forEach { poly ->
                val path = Path().apply {
                    val start = poly.points.first()
                    moveTo(start.x * canvasWidth, start.y * canvasHeight)
                    poly.points.drop(1).forEach { p ->
                        lineTo(poly.points.last().x * canvasWidth, poly.points.last().y * canvasHeight) // simplified for logic
                    lineTo(p.x * canvasWidth, p.y * canvasHeight)
                    }
                    close()
                }

                val isSelected = selectedState?.name == poly.name
                val fillAlpha = if (isSelected) 0.35f else 0.08f
                
                // Glow effect on select
                if (isSelected) {
                    drawPath(path, primaryColor.copy(alpha = 0.05f))
                }

                drawPath(path, primaryColor.copy(alpha = fillAlpha))
                drawPath(path, primaryColor.copy(alpha = 0.6f), style = Stroke(width = 1.dp.toPx()))
            }

            // Trade Arcs
            val tradeHubs = listOf(Offset(0.4f, 0.6f), Offset(0.5f, 0.8f), Offset(0.45f, 0.7f))
            tradeHubs.forEachIndexed { i, hub ->
                val start = Offset(hub.x * canvasWidth, hub.y * canvasHeight)
                val end = if (i % 2 == 0) Offset(0f, hub.y * canvasHeight) else Offset(canvasWidth, hub.y * canvasHeight)
                
                val path = Path().apply {
                    moveTo(start.x, start.y)
                    quadraticTo(
                        (start.x + end.x) / 2f,
                        (start.y + end.y) / 2f - 100f,
                        end.x, end.y
                    )
                }
                
                val pathMeasure = android.graphics.PathMeasure(path.asAndroidPath(), false)
                val length = pathMeasure.length
                val partialPath = android.graphics.Path()
                pathMeasure.getSegment(0f, length * arcProgress, partialPath, true)
                
                drawPath(
                    path = partialPath.asComposePath(),
                    color = accentColor,
                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }

        // State Info Popup
        selectedState?.let { poly ->
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(tapOffset.x.roundToInt(), tapOffset.y.roundToInt()),
                onDismissRequest = { selectedState = null }
            ) {
                Card(
                    modifier = Modifier.width(200.dp),
                    colors = CardDefaults.cardColors(containerColor = SciFiTheme.extendedColors.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, primaryColor),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(poly.name, style = SciFiTheme.typography.BodyMono, fontWeight = FontWeight.Bold, color = primaryColor)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("GSDP: ${poly.gsdp}", style = SciFiTheme.typography.ChartCaption, color = Color.White)
                        Text("Growth: ${poly.growth}", style = SciFiTheme.typography.ChartCaption, color = SciFiTheme.extendedColors.positive)
                        Text("Sector: ${poly.topSector}", style = SciFiTheme.typography.ChartCaption, color = accentColor)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onStateDrillDown(poly.name) },
                            modifier = Modifier.fillMaxWidth().height(32.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                        ) {
                            Text("DRILL DOWN →", fontSize = 10.sp, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

private fun containsPoint(polygon: List<Offset>, point: Offset): Boolean {
    // Simple bounding box check then ray-casting
    val minX = polygon.minOf { it.x }; val maxX = polygon.maxOf { it.x }
    val minY = polygon.minOf { it.y }; val maxY = polygon.maxOf { it.y }
    if (point.x < minX || point.x > maxX || point.y < minY || point.y > maxY) return false
    
    var intersections = 0
    for (i in polygon.indices) {
        val p1 = polygon[i]
        val p2 = polygon[(i + 1) % polygon.size]
        if (point.y > minOf(p1.y, p2.y) && point.y <= maxOf(p1.y, p2.y) && point.x <= maxOf(p1.x, p2.x)) {
            val intersectX = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x
            if (p1.x == p2.x || point.x <= intersectX) intersections++
        }
    }
    return intersections % 2 != 0
}
