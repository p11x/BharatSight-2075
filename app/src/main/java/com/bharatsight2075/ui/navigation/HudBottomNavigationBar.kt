package com.bharatsight2075.ui.navigation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HudBottomNavigationBar(
    onNavigate: (String) -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition()
    val ledAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(0f, 20f)
                lineTo(size.width * 0.15f, 20f)
                lineTo(size.width * 0.2f, 0f)
                lineTo(size.width * 0.8f, 0f)
                lineTo(size.width * 0.85f, 20f)
                lineTo(size.width, 20f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            
            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF11131A),
                        Color(0xFF0A0A0A)
                    )
                ),
                style = Fill
            )
            
            drawPath(
                path = path,
                color = Color(0xFF00E5FF).copy(alpha = 0.5f),
                style = Stroke(width = 1.dp.toPx())
            )
        }
        
        // Menu Button (Left)
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .size(48.dp)
                .clickable { onMenuClick() },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(24.dp)) {
                repeat(3) { i ->
                    drawLine(
                        color = Color(0xFF00E5FF),
                        start = Offset(0f, i * 8.dp.toPx()),
                        end = Offset(size.width, i * 8.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }
        }
        
        // Main Predict Button (Center)
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-10).dp)
                .size(64.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFF3B30),
                            Color(0xFFFF3B30).copy(alpha = 0.3f)
                        )
                    ),
                    shape = androidx.compose.ui.graphics.RectangleShape
                )
                .clickable { onNavigate("forecaster") },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "PREDICT",
                fontSize = 8.sp,
                color = Color.White
            )
        }
        
        // Settings/Right Button
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(48.dp)
                .clickable { onNavigate("settings") },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(24.dp)) {
                drawCircle(
                    color = Color(0xFF808080),
                    radius = 4.dp.toPx(),
                    style = Stroke(width = 2.dp.toPx())
                )
                repeat(8) { i ->
                    val angle = i * 45f
                    val start = Offset(
                        center.x + 6.dp.toPx() * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat(),
                        center.y + 6.dp.toPx() * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
                    )
                    val end = Offset(
                        center.x + 10.dp.toPx() * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat(),
                        center.y + 10.dp.toPx() * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
                    )
                    drawLine(color = Color(0xFF808080), start = start, end = end, strokeWidth = 2.dp.toPx())
                }
            }
        }
    }
}