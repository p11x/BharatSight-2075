package com.bharatsight2075.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharatsight2075.ui.components.IndiaMapImageOverlay
import com.bharatsight2075.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onComplete: (Boolean) -> Unit) {
    // Animation phases: 0=idle 1=grid 2=map+logo 3=title 4=tagline+bar 5=exit
    var phase by remember { mutableIntStateOf(0) }
    
    val splashVm: SplashViewModel = hiltViewModel()
    val isLoggedIn by splashVm.isLoggedIn.collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(Unit) {
        delay(200); phase = 1
        delay(500); phase = 2
        delay(900); phase = 3
        delay(600); phase = 4
        delay(800); phase = 5
        delay(300); onComplete(isLoggedIn)
    }

    val gridAlpha    by animateFloatAsState(if(phase>=1) 0.06f else 0f, tween(600), label="grid")
    val titleAlpha   by animateFloatAsState(if(phase>=3) 1f else 0f, tween(500), label="ta")
    val titleOffsetY by animateDpAsState(if(phase>=3) 0.dp else 20.dp, spring(stiffness=200f), label="toy")
    val barProgress  by animateFloatAsState(if(phase>=4) 1f else 0f, tween(1200, easing=EaseInOutCubic), label="bar")
    val exitAlpha    by animateFloatAsState(if(phase>=5) 0f else 1f, tween(300), label="exit")

    // Infinite animations
    val scanLine by rememberInfiniteTransition(label = "sl").animateFloat(
        initialValue = -0.05f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "s"
    )

    Box(Modifier.fillMaxSize().alpha(exitAlpha).background(Color(0xFF040410))) {

        // LAYER 1: Grid
        Canvas(Modifier.fillMaxSize()) {
            val step = 36.dp.toPx()
            var x = 0f; while(x <= size.width)  { drawLine(Color(0xFF00F5FF).copy(gridAlpha), Offset(x,0f), Offset(x,size.height), 0.5f); x+=step }
            var y = 0f; while(y <= size.height) { drawLine(Color(0xFF00F5FF).copy(gridAlpha), Offset(0f,y), Offset(size.width,y), 0.5f); y+=step }
            // Moving scan line
            val sy = scanLine * size.height
            drawLine(Color(0xFF00F5FF).copy(0.12f * gridAlpha * 15f), Offset(0f,sy), Offset(size.width,sy), 1.5f)
        }

        // LAYER 2: India map image with animated reveal
        val mapAlpha by animateFloatAsState(
            if(phase>=2) 1f else 0f, tween(1000), label="mapAlpha")
        val mapScale by animateFloatAsState(
            if(phase>=2) 1f else 0.9f,
            spring(dampingRatio=0.7f, stiffness=120f), label="mapScale")

        if (phase >= 2) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.68f)
                    .align(Alignment.TopCenter)
                    .graphicsLayer { alpha = mapAlpha; scaleX = mapScale; scaleY = mapScale }
            ) {
                IndiaMapImageOverlay(
                    modifier        = Modifier.fillMaxSize(),
                    glowColor       = Color(0xFF00F5FF),
                    animated        = true
                )
            }
        }

        // LAYER 3: Bottom content
        Column(Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(bottom=60.dp),
            horizontalAlignment=Alignment.CenterHorizontally, verticalArrangement=Arrangement.spacedBy(12.dp)) {

            // Title
            Column(Modifier.alpha(titleAlpha).offset(y=titleOffsetY),
                horizontalAlignment=Alignment.CenterHorizontally) {
                Row {
                    Text("BHARAT", fontSize=28.sp, fontFamily=FontFamily.Monospace,
                        fontWeight=FontWeight.ExtraBold, color=Color(0xFF00F5FF), letterSpacing=0.06.em)
                    Text("SIGHT", fontSize=28.sp, fontFamily=FontFamily.Monospace,
                        fontWeight=FontWeight.ExtraBold, color=Color(0xFF00F5FF).copy(0.7f), letterSpacing=0.06.em)
                }
                Text("2075", fontSize=16.sp, fontFamily=FontFamily.Monospace,
                    fontWeight=FontWeight.Bold, color=Color(0xFFFF6B35))
                Spacer(Modifier.height(4.dp))
                Text("INDIA ECONOMIC INTELLIGENCE PLATFORM", fontSize=8.sp,
                    fontFamily=FontFamily.Monospace, color=Color(0xFF00F5FF).copy(0.45f), letterSpacing=0.16.em)
            }

            // Loading bar
            Column(Modifier.alpha(if(phase>=4) 1f else 0f),
                horizontalAlignment=Alignment.CenterHorizontally, verticalArrangement=Arrangement.spacedBy(5.dp)) {
                Box(Modifier.width(200.dp).height(2.dp).background(Color(0xFF00F5FF).copy(0.12f),
                    RoundedCornerShape(1.dp))) {
                    Box(Modifier.fillMaxHeight().fillMaxWidth(barProgress)
                        .background(Brush.horizontalGradient(listOf(Color(0xFF00F5FF), Color(0xFFFF6B35))),
                            RoundedCornerShape(1.dp)))
                }
                Text("${(barProgress*100).toInt()}%", fontSize=9.sp,
                    fontFamily=FontFamily.Monospace, color=Color(0xFF00F5FF).copy(0.4f))
            }
        }
    }
}
