package com.bharatsight2075.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BharatSightLogo(modifier: Modifier = Modifier, animated: Boolean = true, size: Dp = 64.dp) {
    var triggered by remember { mutableStateOf(!animated) }
    val anim by animateFloatAsState(
        if (triggered) 1f else 0f,
        tween(1500, easing = EaseOutCubic),
        label = "LogoAnimation"
    )
    LaunchedEffect(Unit) { if (animated) triggered = true }

    Box(modifier.size(size), contentAlignment = Center) {
        Canvas(Modifier.fillMaxSize()) {
            val cx = size.toPx() / 2
            val cy = size.toPx() / 2
            val r = size.toPx() / 2 * 0.9f
            
            // Hexagon frame
            val hexPath = Path().apply {
                for (i in 0..5) {
                    val a = (i * 60 - 30) * PI / 180
                    val x = cx + r * cos(a).toFloat()
                    val y = cy + r * sin(a).toFloat()
                    if (i == 0) moveTo(x, y) else lineTo(x, y)
                }
                close()
            }
            drawPath(hexPath, Color(0xFF00F5FF), style = Stroke(2.dp.toPx() * anim, cap = StrokeCap.Round))
            // Glow behind hex
            drawPath(hexPath, Color(0xFF00F5FF).copy(0.1f * anim), style = Stroke(8.dp.toPx() * anim))
            
            // Eye ellipse
            val eyePath = Path().apply {
                moveTo(cx - r * 0.6f, cy)
                cubicTo(cx - r * 0.3f, cy - r * 0.4f, cx + r * 0.3f, cy - r * 0.4f, cx + r * 0.6f, cy)
                cubicTo(cx + r * 0.3f, cy + r * 0.4f, cx - r * 0.3f, cy + r * 0.4f, cx - r * 0.6f, cy)
                close()
            }
            drawPath(eyePath, Color(0xFF00F5FF).copy(0.08f * anim))
            drawPath(eyePath, Color(0xFF00F5FF), style = Stroke(1.5.dp.toPx() * anim))
            
            // Chart line
            val chartPath = Path().apply {
                moveTo(cx - r * 0.3f, cy + r * 0.15f)
                lineTo(cx - r * 0.08f, cy - r * 0.15f)
                lineTo(cx + r * 0.12f, cy)
                lineTo(cx + r * 0.3f, cy - r * 0.25f)
            }
            val pm = android.graphics.PathMeasure(chartPath.asAndroidPath(), false)
            val seg = android.graphics.Path()
            pm.getSegment(0f, pm.length * anim, seg, true)
            drawPath(seg.asComposePath(), Color(0xFFFF6B35), style = Stroke(2.dp.toPx(), cap = StrokeCap.Round))
            
            // Orange tip dot
            if (anim > 0.9f) {
                drawCircle(
                    Color(0xFFFF6B35),
                    3.dp.toPx(),
                    Offset(cx + r * 0.3f, cy - r * 0.25f),
                    alpha = (anim - 0.9f) * 10f
                )
            }
            
            // Tick marks at hex vertices
            for (i in 0..5) {
                val a = (i * 60 - 30) * PI / 180
                val ox = (cx + r * cos(a)).toFloat()
                val oy = (cy + r * sin(a)).toFloat()
                val ix = (cx + (r - 6.dp.toPx()) * cos(a)).toFloat()
                val iy = (cy + (r - 6.dp.toPx()) * sin(a)).toFloat()
                drawLine(Color(0xFFFF6B35).copy(0.7f * anim), Offset(ix, iy), Offset(ox, oy), 1.5.dp.toPx())
            }
        }
    }
}
