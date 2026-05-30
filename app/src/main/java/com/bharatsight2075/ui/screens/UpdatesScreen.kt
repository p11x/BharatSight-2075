package com.bharatsight2075.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.line.DataPoint
import com.bharatsight2075.ui.visualization.line.HolographicLineChart

data class MarketUpdate(
    val category: String,
    val name: String,
    val icon: ImageVector,
    val value: String,
    val change: Double,
    val changePct: Double,
    val timestamp: String,
    val history: List<Float>
)

object UpdatesMockData {
    val items = listOf(
        MarketUpdate("Indices", "SENSEX", Icons.Outlined.ShowChart, "81,234", 645.0, 0.8, "12:45 PM", listOf(0.4f, 0.6f, 0.5f, 0.7f, 0.8f, 0.75f, 0.8f)),
        MarketUpdate("Indices", "NIFTY 50", Icons.Outlined.ShowChart, "24,567", 142.0, 0.6, "12:45 PM", listOf(0.3f, 0.5f, 0.4f, 0.55f, 0.6f, 0.58f, 0.6f)),
        MarketUpdate("Currency", "USD/INR", Icons.Outlined.CurrencyExchange, "83.21", -0.08, -0.1, "12:44 PM", listOf(0.2f, 0.15f, 0.18f, 0.12f, 0.1f, 0.11f, 0.09f)),
        MarketUpdate("Commodities", "GOLD", Icons.Outlined.LocalFireDepartment, "72,450", 215.0, 0.3, "12:40 PM", listOf(0.1f, 0.2f, 0.25f, 0.3f, 0.28f, 0.32f, 0.3f)),
        MarketUpdate("Commodities", "CRUDE OIL", Icons.Outlined.OilBarrel, "$78.4", -0.32, -0.4, "12:35 PM", listOf(0.5f, 0.45f, 0.48f, 0.42f, 0.4f, 0.38f, 0.36f)),
        MarketUpdate("Indices", "NIFTY IT", Icons.Outlined.Computer, "38,120", 520.0, 1.4, "12:30 PM", listOf(0.2f, 0.4f, 0.6f, 0.9f, 1.1f, 1.3f, 1.4f))
    )
}

@Composable
fun UpdatesScreen(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Indices", "Currency", "Commodities", "Bonds", "Crypto")
    
    val filteredUpdates = if (selectedCategory == "All") UpdatesMockData.items else UpdatesMockData.items.filter { it.category == selectedCategory }

    BackHandler {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.HOME) { inclusive = true }
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Live Market Updates",
                    badge = "REAL-TIME",
                    badgeColor = Color(0xFF00E676),
                    onBackClick = { /* Handled by filter */ }
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Live Ticker
            LiveTickerBanner()

            // Filter Row
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SciFiTheme.extendedColors.primary,
                            selectedLabelColor = Color.Black
                        )
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredUpdates) { update ->
                    UpdateCard(update)
                }
            }
        }
    }
}

@Composable
fun LiveTickerBanner() {
    val extendedColors = SciFiTheme.extendedColors
    val infiniteTransition = rememberInfiniteTransition(label = "Ticker")
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 1000f,
        targetValue = -1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "offset"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .background(extendedColors.primary.copy(alpha = 0.08f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "SENSEX 81,234 ▲0.8% | NIFTY 24,567 ▲0.6% | ₹/$ 83.21 ▼0.1% | GOLD ₹72,450 ▲0.3% | CRUDE $78.4 ▼0.4%",
            style = SciFiTheme.typography.ChartCaption.copy(fontSize = 11.sp),
            color = extendedColors.primary,
            modifier = Modifier.offset(x = offsetX.dp),
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
fun UpdateCard(update: MarketUpdate) {
    val extendedColors = SciFiTheme.extendedColors
    val isPositive = update.change >= 0
    val accentColor = if (isPositive) Color(0xFF00E676) else Color(0xFFFF5252)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, extendedColors.primary.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
        color = accentColor.copy(alpha = 0.04f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = update.icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = update.name,
                        style = SciFiTheme.typography.BodyMono.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(extendedColors.surface, RoundedCornerShape(4.dp))
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(update.category.uppercase(), fontSize = 8.sp, color = extendedColors.textSecondary)
                    }
                }
                Text(update.timestamp, fontSize = 9.sp, color = extendedColors.textSecondary)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = update.value,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    text = "${if (isPositive) "▲" else "▼"}${Math.abs(update.change)} (${update.changePct}%)",
                    style = SciFiTheme.typography.ChartCaption,
                    color = accentColor
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Sparkline placeholder
            Box(modifier = Modifier.size(60.dp, 30.dp)) {
                HolographicLineChart(
                    dataPoints = update.history.mapIndexed { i, v -> DataPoint(i * 100f, 1000f - v * 1000f) },
                    lineColor = accentColor,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
