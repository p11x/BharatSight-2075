package com.bharatsight2075.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.IndiaMapImageOverlay
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavController) {
    var phase by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        delay(300); phase = 1
        delay(600); phase = 2
        delay(500); phase = 3
    }

    val monoFamily = FontFamily.Monospace
    val onSurfaceColor = SciFiTheme.colors.onSurface
    
    Box(Modifier.fillMaxSize().background(Color(0xFF050510))) {
        GridBackgroundSurface {
            // Content
        }

        // WELCOME SCREEN — replace map layer
        val mapAlpha by animateFloatAsState(
            if (phase >= 1) 1f else 0f,
            tween(1200),
            label = "wMapAlpha"
        )
        val mapScale by animateFloatAsState(
            if(phase>=1) 1f else 0.88f,
            spring(dampingRatio=0.65f, stiffness=100f), label="wMapScale")

        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.72f)
                .align(Alignment.TopCenter)
                .graphicsLayer { alpha = mapAlpha; scaleX = mapScale; scaleY = mapScale }
        ) {
            IndiaMapImageOverlay(
                modifier     = Modifier.fillMaxSize(),
                glowColor    = Color(0xFF00F5FF),
                animated     = true
            )
        }

        // CONTENT OVERLAY at bottom
        Column(
            Modifier.align(BottomCenter).fillMaxWidth().padding(32.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val textAlpha by animateFloatAsState(if (phase >= 2) 1f else 0f, tween(600), label = "TextAlpha")
            val textOffset by animateDpAsState(if (phase >= 2) 0.dp else 40.dp, tween(600, easing = EaseOutCubic), label = "TextOffset")

            Column(
                Modifier.graphicsLayer { alpha = textAlpha }.offset(y = textOffset),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Glowing badge
                Box(
                    Modifier
                        .border(1.dp, Color(0xFF00F5FF).copy(0.5f), RoundedCornerShape(20.dp))
                        .background(Color(0xFF00F5FF).copy(0.08f))
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Text(text = "INDIA'S ECONOMIC INTELLIGENCE HUB", fontSize = 9.sp, fontFamily = monoFamily, fontWeight = SemiBold, color = Color(0xFF00F5FF), letterSpacing = 0.15.sp)
                }
                Text(
                    text = "BharatSight 2075",
                    fontSize = 32.sp,
                    fontFamily = monoFamily,
                    fontWeight = ExtraBold,
                    style = TextStyle(brush = Brush.horizontalGradient(listOf(Color(0xFF00F5FF), Color(0xFFFF6B35)))),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Explore India's economy through advanced AI-powered analytics, real-time market data, and 50-year economic forecasts.",
                    fontSize = 13.sp,
                    color = onSurfaceColor.copy(0.65f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                // 3 feature pills
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {
                    listOf("📊 30 Sectors", "🤖 AI Powered", "🗺 District Maps").forEach { feature ->
                        Box(
                            Modifier
                                .weight(1f)
                                .border(1.dp, Color(0xFF00F5FF).copy(0.25f), RoundedCornerShape(8.dp))
                                .background(Color(0xFF00F5FF).copy(0.05f))
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = feature, fontSize = 10.sp, fontFamily = monoFamily, color = onSurfaceColor.copy(0.7f), textAlign = TextAlign.Center)
                        }
                    }
                }
            }

            // GET STARTED BUTTON
            val btnAlpha by animateFloatAsState(if (phase >= 3) 1f else 0f, tween(500), label = "BtnAlpha")
            val btnScale by animateFloatAsState(if (phase >= 3) 1f else 0.8f, spring(dampingRatio = 0.5f), label = "BtnScale")
            val infiniteTransition = rememberInfiniteTransition(label = "pulse")
            val pulseAnim by infiniteTransition.animateFloat(1f, 1.04f, infiniteRepeatable(tween(1200, easing = EaseInOutSine), RepeatMode.Reverse), label = "p")
            
            Box(
                Modifier
                    .graphicsLayer { alpha = btnAlpha; scaleX = btnScale * pulseAnim; scaleY = btnScale * pulseAnim }
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.horizontalGradient(listOf(Color(0xFF00F5FF), Color(0xFF0080FF), Color(0xFF7C4DFF))))
                    .drawBehind { drawRoundRect(Color(0xFF00F5FF).copy(0.3f), cornerRadius = CornerRadius(16.dp.toPx()), style = Stroke(2.dp.toPx())) }
                    .clickable {
                        navController.navigate(Routes.HOME) { popUpTo(Routes.WELCOME) { inclusive = true } }
                    }
                    .padding(18.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "GET STARTED", fontSize = 15.sp, fontFamily = monoFamily, fontWeight = ExtraBold, color = Color.White, letterSpacing = 0.12.sp)
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, Modifier.size(20.dp), tint = Color.White)
                }
            }

            Text(text = "By signing in you agree to our Terms of Service", fontSize = 9.sp, fontFamily = monoFamily, color = onSurfaceColor.copy(0.25f))
        }
    }
}
