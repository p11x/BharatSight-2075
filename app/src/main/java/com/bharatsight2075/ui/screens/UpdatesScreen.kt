package com.bharatsight2075.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.WaveformChart

@Composable
fun UpdatesScreen(
    navController: NavController,
    viewModel: UpdatesViewModel = hiltViewModel()
) {
    val updates by viewModel.updates.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val extendedColors = SciFiTheme.extendedColors
    
    val filteredUpdates = if (selectedCategory == "ALL") updates else updates.filter { it.category == selectedCategory }

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Economic Updates",
                    badge = "LIVE",
                    badgeColor = extendedColors.positive,
                    onBackClick = { /* Tab root */ }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // 1. Live Ticker Banner
            LiveTickerBanner(updates, extendedColors)
            
            // 2. Category Filters
            CategoryFilters(selectedCategory, onSelect = { viewModel.selectCategory(it) }, extendedColors)
            
            // 3. Updates List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredUpdates) { update ->
                    MarketUpdateCard(update, extendedColors)
                }
                
                item { Spacer(Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
private fun LiveTickerBanner(updates: List<MarketUpdate>, colors: SciFiTheme.SciFiColors) {
    val infiniteTransition = rememberInfiniteTransition(label = "Ticker")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -1000f,
        animationSpec = infiniteRepeatable(tween(20000, easing = LinearEasing)),
        label = "Offset"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(colors.primary.copy(alpha = 0.05f))
            .border(width = 0.5.dp, color = colors.primary.copy(alpha = 0.2f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier.offset(x = offset.dp)) {
            repeat(10) { // Repeat for infinite look
                updates.forEach { update ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(update.name, fontSize = 10.sp, color = colors.textSecondary)
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = if (update.change >= 0) "▲" else "▼",
                            fontSize = 8.sp,
                            color = if (update.change >= 0) colors.positive else colors.negative
                        )
                        Spacer(Modifier.width(2.dp))
                        Text(update.value, fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryFilters(selected: String, onSelect: (String) -> Unit, colors: SciFiTheme.SciFiColors) {
    val categories = listOf("ALL", "EQUITY", "CURRENCY", "COMMODITY", "CRYPTO")
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { cat ->
            val isSelected = selected == cat
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSelect(cat) },
                color = if (isSelected) colors.primary.copy(alpha = 0.2f) else Color.Transparent,
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) colors.primary else Color.White.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = cat,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) colors.primary else Color.Gray
                )
            }
        }
    }
}

@Composable
private fun MarketUpdateCard(update: MarketUpdate, colors: SciFiTheme.SciFiColors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.3f))
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(update.category, fontSize = 9.sp, color = colors.textDisabled)
                Text(update.name, style = SciFiTheme.typography.SectionHead, color = Color.White)
                Text(update.timestamp, fontSize = 9.sp, color = colors.textSecondary)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = update.value,
                    style = SciFiTheme.typography.HeroNumber.copy(fontSize = 18.sp),
                    color = Color.White
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (update.change >= 0) "+${String.format(Locale.UK, "%.2f", update.change)}" else String.format(Locale.UK, "%.2f", update.change),
                        fontSize = 11.sp,
                        color = if (update.change >= 0) colors.positive else colors.negative
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "(${String.format(Locale.UK, "%.2f", update.changePct)}%)",
                        fontSize = 10.sp,
                        color = if (update.change >= 0) colors.positive.copy(alpha = 0.7f) else colors.negative.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
