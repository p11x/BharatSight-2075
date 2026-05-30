package com.bharatsight2075.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.service.LiveMarketData
import com.bharatsight2075.ui.theme.RetroDarkColors

@Composable
fun TickerTapeOverlay(
    marketData: List<LiveMarketData>,
    modifier: Modifier = Modifier
) {
    var containerWidth by remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    val infiniteTransition = rememberInfiniteTransition(label = "Ticker")
    val xOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -1000f, // Simplified, should be based on content width
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "Offset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(Color.Black.copy(alpha = 0.7f))
            .onGloballyPositioned { containerWidth = it.size.width.toFloat() },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.offset(x = with(density) { xOffset.dp }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Repeat to create seamless loop
            (marketData + marketData + marketData).forEach { data ->
                TickerItem(data)
                Spacer(modifier = Modifier.width(32.dp))
            }
        }
    }
}

@Composable
fun TickerItem(data: LiveMarketData) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = data.symbol,
            color = RetroDarkColors.TextSecondary,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = String.format("%.2f", data.price),
            color = Color.White,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = if (data.change >= 0) "▲" else "▼",
            color = if (data.change >= 0) RetroDarkColors.NeonGreen else RetroDarkColors.NeonOrange,
            fontSize = 8.sp
        )
        Text(
            text = String.format("%.2f%%", Math.abs(data.change)),
            color = if (data.change >= 0) RetroDarkColors.NeonGreen else RetroDarkColors.NeonOrange,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
