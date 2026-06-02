package com.bharatsight2075.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarAction
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.line.DataPoint
import com.bharatsight2075.ui.visualization.line.HolographicLineChart

@Composable
fun ProfileScreen(navController: NavController) {
    val extendedColors = SciFiTheme.extendedColors
    
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
                    title = "Profile",
                    onBackClick = { /* Handled by home logic */ },
                    actions = listOf(
                        TopBarAction(Icons.Outlined.Edit, "Edit", {})
                    )
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // SECTION 1 - User Hero Card
            item {
                UserHeroCard()
            }

            // SECTION 2 - Stats Row
            item {
                StatsRow()
            }

            // SECTION 3 - Saved Scenarios
            item {
                SectionHeader(title = "SAVED SCENARIOS", actionText = "View All →")
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(3) { i ->
                        ScenarioCard("Scenario $i", "May ${20+i}, 2025")
                    }
                }
            }

            // SECTION 4 - Watchlist
            item {
                SectionHeader(title = "WATCHLIST")
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(3) { i ->
                WatchlistItem("Asset $i", "$${1200+i*50}", i % 2 == 0)
                if (i < 2) HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            }

            // SECTION 5 - Preferences
            item {
                SectionHeader(title = "PREFERENCES")
                Spacer(modifier = Modifier.height(12.dp))
                PreferencesGroup()
            }

            // SECTION 6 - Account Actions
            item {
                OutlinedButton(
                    onClick = { /* Export */ },
                    modifier = Modifier.fillMaxWidth(),
                    border = androidx.compose.foundation.BorderStroke(1.dp, extendedColors.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("EXPORT MY DATA", color = extendedColors.primary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { /* Sign Out */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SIGN OUT", color = Color(0xFFFF5252))
                }
            }
        }
    }
}

@Composable
fun UserHeroCard() {
    val colors = SciFiTheme.extendedColors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(
                Brush.linearGradient(listOf(colors.primary.copy(alpha = 0.12f), colors.accent.copy(alpha = 0.08f))),
                RoundedCornerShape(16.dp)
            )
            .border(1.dp, colors.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .border(2.dp, colors.primary, CircleShape)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(colors.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("PA", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = colors.primary)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Pavan", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color.White)
            Text("Economic Analyst · BharatSight Pro", style = MaterialTheme.typography.bodySmall, color = colors.textSecondary)
            Text("Member since May 2025", fontSize = 9.sp, color = colors.textSecondary.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem("12", "Scenarios Saved")
        Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color.White.copy(alpha = 0.1f)))
        StatItem("47", "Forecasts Run")
        Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color.White.copy(alpha = 0.1f)))
        StatItem("3", "Watchlists")
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = SciFiTheme.extendedColors.primary)
        Text(label.uppercase(), fontSize = 9.sp, color = SciFiTheme.extendedColors.textSecondary)
    }
}

@Composable
fun ScenarioCard(name: String, date: String) {
    Surface(
        modifier = Modifier.size(180.dp, 80.dp),
        color = SciFiTheme.extendedColors.surface.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(name, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
            Text(date, fontSize = 9.sp, color = SciFiTheme.extendedColors.textSecondary)
            Spacer(modifier = Modifier.weight(1f))
            // Mini bars placeholder
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(4) { i ->
                    Box(modifier = Modifier.width(20.dp).height(4.dp).background(SciFiTheme.extendedColors.primary.copy(alpha = 0.3f + i*0.2f)))
                }
            }
        }
    }
}

@Composable
fun WatchlistItem(name: String, value: String, isUp: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(name, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
            Text("Ticker Info", fontSize = 10.sp, color = SciFiTheme.extendedColors.textSecondary)
        }
        
        Box(modifier = Modifier.size(60.dp, 24.dp)) {
             HolographicLineChart(
                dataPoints = listOf(0.4f, 0.6f, 0.3f, 0.8f, 0.7f).mapIndexed { i, v -> DataPoint(i * 100f, 1000f - v * 1000f) },
                lineColor = if(isUp) Color(0xFF00E676) else Color(0xFFFF5252),
                modifier = Modifier.fillMaxSize()
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(horizontalAlignment = Alignment.End) {
            Text(value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Color.White)
            Text(if(isUp) "▲ 1.2%" else "▼ 0.8%", fontSize = 10.sp, color = if(isUp) Color(0xFF00E676) else Color(0xFFFF5252))
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String? = null) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        Text(title, style = SciFiTheme.typography.SectionHead, color = SciFiTheme.extendedColors.primary)
        if (actionText != null) {
            Text(actionText, style = SciFiTheme.typography.ChartCaption, color = SciFiTheme.extendedColors.accent)
        }
    }
}

@Composable
fun PreferencesGroup() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = SciFiTheme.extendedColors.surface.copy(alpha = 0.3f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Column {
            PreferenceRow("Theme Mode", "Cyberpunk")
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            PreferenceRow("Notifications", "On")
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            PreferenceRow("Language", "English")
        }
    }
}

@Composable
fun PreferenceRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
        Text(value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = SciFiTheme.extendedColors.primary)
    }
}
