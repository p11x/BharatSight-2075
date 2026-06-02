package com.bharatsight2075.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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

@Composable
fun IndiaMapCanvas(
    modifier: Modifier = Modifier,
    onStateDrillDown: (String) -> Unit
) {
    val primaryColor = SciFiTheme.extendedColors.primary
    val accentColor = SciFiTheme.extendedColors.accent
    
    var selectedState by remember { mutableStateOf<String?>(null) }
    var tapOffset by remember { mutableStateOf(Offset.Zero) }

    // Simplified High-Fidelity India Path
    val indiaPath = remember {
        Path().apply {
            moveTo(0.5f, 0.05f) // North
            lineTo(0.6f, 0.15f); lineTo(0.65f, 0.25f); lineTo(0.75f, 0.28f) // North East
            lineTo(0.9f, 0.35f); lineTo(0.95f, 0.45f); lineTo(0.85f, 0.5f) // East
            lineTo(0.75f, 0.65f); lineTo(0.6f, 0.85f); lineTo(0.5f, 0.95f) // South Tip
            lineTo(0.4f, 0.85f); lineTo(0.25f, 0.65f); lineTo(0.15f, 0.55f) // West
            lineTo(0.05f, 0.45f); lineTo(0.15f, 0.35f); lineTo(0.35f, 0.25f) // North West
            close()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "MapAnims")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing), RepeatMode.Reverse), label = "Pulse"
    )

    Box(modifier = modifier.fillMaxWidth().aspectRatio(1f)) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        selectedState = "STATE_SELECTED"
                        tapOffset = offset
                    }
                }
        ) {
            val scaleX = size.width
            val scaleY = size.height
            
            val scaledPath = Path().apply {
                addPath(indiaPath)
                val matrix = Matrix()
                matrix.scale(scaleX, scaleY)
                transform(matrix)
            }

            // Fill
            drawPath(
                path = scaledPath,
                brush = Brush.radialGradient(
                    colors = listOf(primaryColor.copy(alpha = 0.15f), Color.Transparent),
                    center = center,
                    radius = size.minDimension
                )
            )

            // Outline
            drawPath(
                path = scaledPath,
                color = primaryColor.copy(alpha = 0.6f),
                style = Stroke(width = 2.dp.toPx())
            )

            // State Dots (Pulse)
            val hubs = listOf(Offset(0.5f, 0.2f), Offset(0.35f, 0.55f), Offset(0.55f, 0.75f), Offset(0.75f, 0.45f))
            hubs.forEach { hub ->
                val centerHub = Offset(hub.x * scaleX, hub.y * scaleY)
                drawCircle(primaryColor.copy(alpha = 0.2f * pulse), radius = 15.dp.toPx() * pulse, center = centerHub)
                drawCircle(primaryColor, radius = 4.dp.toPx(), center = centerHub)
            }
        }

        if (selectedState != null) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(tapOffset.x.roundToInt(), tapOffset.y.roundToInt()),
                onDismissRequest = { selectedState = null }
            ) {
                Surface(
                    color = Color(0xFF0E0E1A),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, primaryColor)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Economic Hub Alpha", color = primaryColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("GSDP: ₹38.8L Cr", color = Color.White, fontSize = 10.sp)
                        Text("Growth: 8.2%", color = Color(0xFF00E676), fontSize = 10.sp)
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = { onStateDrillDown("Maharashtra") },
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                        ) {
                            Text("DETAILS →", fontSize = 9.sp, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}
