package com.bharatsight2075.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bharatsight2075.ui.microinteraction.DecodingText
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlinx.coroutines.delay

    @Composable
    fun SplashScreen(
        onFinishSplash: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        LaunchedEffect(Unit) {
            delay(2500)
            onFinishSplash()
        }

    GridBackgroundSurface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo: Stylized Rupee symbol merged with upward chart line
            Canvas(
                modifier = Modifier
                    .size(120.dp)
            ) {
                // Draw the stylized logo
                // Background circle for logo
                drawCircle(
                    color = Color(0xFF0F0F0F),
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.width / 2
                )
                
                // Outer glow effect
                drawCircle(
                    color = Color(0x3300E5FF),
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.width / 2 + 4.dp.toPx(),
                    style = Stroke(width = 2.dp.toPx())
                )
                
                // Stylized Rupee symbol (simplified as vertical line with two horizontal strokes)
                val rupeeWidth = size.width * 0.6f
                val rupeeHeight = size.height * 0.8f
                val centerX = size.width / 2
                val centerY = size.height / 2
                
                // Vertical stem of Rupee
                drawLine(
                    start = Offset(centerX - rupeeWidth * 0.1f, centerY - rupeeHeight * 0.4f),
                    end = Offset(centerX - rupeeWidth * 0.1f, centerY + rupeeHeight * 0.4f),
                    color = Color(0xFFFF9500),
                    strokeWidth = 4.dp.toPx()
                )
                
                // Upper horizontal stroke
                drawLine(
                    start = Offset(centerX - rupeeWidth * 0.1f, centerY - rupeeHeight * 0.2f),
                    end = Offset(centerX + rupeeWidth * 0.3f, centerY - rupeeHeight * 0.2f),
                    color = Color(0xFFFF9500),
                    strokeWidth = 4.dp.toPx()
                )
                
                // Lower horizontal stroke
                drawLine(
                    start = Offset(centerX - rupeeWidth * 0.1f, centerY + rupeeHeight * 0.2f),
                    end = Offset(centerX + rupeeWidth * 0.3f, centerY + rupeeHeight * 0.2f),
                    color = Color(0xFFFF9500),
                    strokeWidth = 4.dp.toPx()
                )
                
                // Upward chart line merging with Rupee
                val chartPoints = listOf(
                    Offset(centerX + rupeeWidth * 0.1f, centerY + rupeeHeight * 0.3f),
                    Offset(centerX + rupeeWidth * 0.3f, centerY + rupeeHeight * 0.1f),
                    Offset(centerX + rupeeWidth * 0.5f, centerY - rupeeHeight * 0.1f),
                    Offset(centerX + rupeeWidth * 0.7f, centerY - rupeeHeight * 0.3f)
                )
                
                // Draw chart line with glow
                drawLine(
                    start = chartPoints[0],
                    end = chartPoints[1],
                    color = Color(0xFF00E5FF),
                    strokeWidth = 3.dp.toPx()
                )
                drawLine(
                    start = chartPoints[1],
                    end = chartPoints[2],
                    color = Color(0xFF00E5FF),
                    strokeWidth = 3.dp.toPx()
                )
                drawLine(
                    start = chartPoints[2],
                    end = chartPoints[3],
                    color = Color(0xFF00E5FF),
                    strokeWidth = 3.dp.toPx()
                )
                
                // Glow effect for chart line
                drawLine(
                    start = chartPoints[0],
                    end = chartPoints[1],
                    color = Color(0x6600E5FF),
                    strokeWidth = 6.dp.toPx()
                )
                drawLine(
                    start = chartPoints[1],
                    end = chartPoints[2],
                    color = Color(0x6600E5FF),
                    strokeWidth = 6.dp.toPx()
                )
                drawLine(
                    start = chartPoints[2],
                    end = chartPoints[3],
                    color = Color(0x6600E5FF),
                    strokeWidth = 6.dp.toPx()
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // App Name
            Text(
                text = "BHARATSIGHT 2075",
                color = RetroDarkColors.TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 1.5.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Decoding Text
            DecodingText(
                text = "INITIALIZING PREDICTION ENGINE...",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                typingSpeed = 100L
            )
        }
    }
}