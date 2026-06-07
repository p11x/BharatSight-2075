package com.bharatsight2075.ui.maps

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.North
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme

fun pointInPolygon(point: Offset, polygon: List<Offset>): Boolean {
    var isInside = false
    var j = polygon.size - 1
    for (i in polygon.indices) {
        if (polygon[i].y > point.y != polygon[j].y > point.y &&
            point.x < (polygon[j].x - polygon[i].x) * (point.y - polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x
        ) {
            isInside = !isInside
        }
        j = i
    }
    return isInside
}

fun lerpColor(start: Color, end: Color, fraction: Float): Color {
    return Color(
        red = start.red + (end.red - start.red) * fraction,
        green = start.green + (end.green - start.green) * fraction,
        blue = start.blue + (end.blue - start.blue) * fraction,
        alpha = start.alpha + (end.alpha - start.alpha) * fraction
    )
}

@Composable
fun CompassRose(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.Center) {
        val color = SciFiTheme.colors.primary
        Canvas(Modifier.fillMaxSize()) {
            val r = size.minDimension / 2
            drawCircle(color.copy(0.1f), r)
            drawCircle(color.copy(0.3f), r, style = Stroke(1.dp.toPx()))
            
            // North indicator
            val nPath = Path().apply {
                moveTo(center.x, center.y - r * 0.8f)
                lineTo(center.x - r * 0.2f, center.y)
                lineTo(center.x + r * 0.2f, center.y)
                close()
            }
            drawPath(nPath, color)
        }
        Text("N", Modifier.align(Alignment.TopCenter).padding(top = 2.dp), fontSize = 8.sp, color = Color.White)
    }
}

@Composable
fun MapScaleBar(scale: Float, modifier: Modifier = Modifier) {
    val width = 60.dp
    Column(modifier, horizontalAlignment = Alignment.Start) {
        Text("500 km", fontSize = 8.sp, color = Color.White.copy(0.6f), fontFamily = FontFamily.Monospace)
        Box(Modifier.width(width).height(4.dp).border(1.dp, Color.White.copy(0.3f))) {
            Box(Modifier.fillMaxHeight().fillMaxWidth(0.5f).background(Color.White.copy(0.3f)))
        }
    }
}

@Composable
fun MapLegend(style: String, modifier: Modifier = Modifier) {
    // Simplified legend
}

@Composable
fun StateInfoCard(state: String?, style: String, onClose: () -> Unit) {
    if (state == null) return
    Box(
        Modifier
            .fillMaxWidth(0.9f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0E0E1A))
            .border(1.dp, Color(0xFF00F5FF).copy(0.4f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(state.uppercase(), fontSize = 16.sp, fontWeight = Bold, color = Color.White, fontFamily = FontFamily.Monospace)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onClose, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Outlined.Close, null, tint = Color.White.copy(0.6f))
                }
            }
            Text("Metric: $style", fontSize = 10.sp, color = Color(0xFF00F5FF), fontFamily = FontFamily.Monospace)
            Spacer(Modifier.height(8.dp))
            Text("Real-time economic telemetry active for this region.", fontSize = 11.sp, color = Color.White.copy(0.7f))
        }
    }
}
