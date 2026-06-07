package com.bharatsight2075.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
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
import com.bharatsight2075.ui.components.BharatSightLogo
import com.bharatsight2075.ui.screens.Routes
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun AdvancedTelemetryDrawer(
    selectedRoute: String,
    onNavigate: (String) -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors

    ModalDrawerSheet(
        drawerContainerColor = Color(0xFF080810),
        drawerContentColor = Color.White,
        modifier = Modifier.width(320.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Header Logo
            Row(verticalAlignment = Alignment.CenterVertically) {
                BharatSightLogo(modifier = Modifier.size(60.dp))
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("BHARATSIGHT", style = SciFiTheme.typography.HeroNumber.copy(fontSize = 18.sp), color = extendedColors.primary)
                    Text("TELEMETRY HUD v2.4", fontSize = 9.sp, color = extendedColors.accent, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(Modifier.height(32.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
            
            LazyColumn(modifier = Modifier.weight(1f)) {
                item { DrawerSectionHeader("CORE OBSERVATORY") }
                
                item {
                    DrawerItem(Icons.Outlined.Dashboard, "MACRO OVERVIEW", selectedRoute == Routes.MACRO_OVERVIEW) { onNavigate(Routes.MACRO_OVERVIEW) }
                }
                item {
                    DrawerItem(Icons.AutoMirrored.Outlined.TrendingUp, "FORECASTER 2075", selectedRoute == Routes.FORECASTER) { onNavigate(Routes.FORECASTER) }
                }
                item {
                    DrawerItem(Icons.Outlined.TrackChanges, "INDICATOR HUB", selectedRoute == Routes.MACRO_HUB) { onNavigate(Routes.MACRO_HUB) }
                }

                item { DrawerSectionHeader("◈ GEOGRAPHIC ANALYTICS") }
                item {
                    DrawerItem(Icons.Outlined.Map, "INDIA ECONOMIC MAPS", selectedRoute == Routes.MAPS) { onNavigate(Routes.MAPS) }
                }

                item { DrawerSectionHeader("DEEP ANALYTICS") }
                item {
                    DrawerItem(Icons.Outlined.BarChart, "SECTOR DEEP DIVE", selectedRoute == Routes.SECTOR_DIVE) { onNavigate(Routes.SECTOR_DIVE) }
                }
                item {
                    DrawerItem(Icons.Outlined.Hub, "TRADE NETWORK", selectedRoute == Routes.TRADE_NETWORK) { onNavigate(Routes.TRADE_NETWORK) }
                }
                item {
                    DrawerItem(Icons.Outlined.Grid4x4, "MARKET HEATMAP", selectedRoute == Routes.STOCK_HEATMAP) { onNavigate(Routes.STOCK_HEATMAP) }
                }
                item {
                    DrawerItem(Icons.Outlined.Groups, "DEMOGRAPHICS", selectedRoute == Routes.DEMOGRAPHICS) { onNavigate(Routes.DEMOGRAPHICS) }
                }

                item { DrawerSectionHeader("SYSTEM DIAGNOSTICS") }
                item {
                    DiagnosticPanel(extendedColors)
                }
            }

            // Theme Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.05f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("INTERFACE THEME", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(modifier = Modifier.size(24.dp).background(extendedColors.primary, RoundedCornerShape(4.dp)))
                    Box(modifier = Modifier.size(24.dp).background(extendedColors.accent, RoundedCornerShape(4.dp)))
                }
            }
        }
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) extendedColors.primary.copy(alpha = 0.1f) else Color.Transparent)
            .border(if (isSelected) 1.dp else 0.dp, if (isSelected) extendedColors.primary.copy(alpha = 0.3f) else Color.Transparent, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if (isSelected) extendedColors.primary else Color.White.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun DrawerSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Gray,
        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp, start = 4.dp),
        letterSpacing = 1.sp
    )
}

@Composable
private fun DiagnosticPanel(colors: SciFiTheme.SciFiColors) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DiagnosticRow("ENGINE", "TFLITE_V2", colors.primary)
        DiagnosticRow("UPTIME", "102:44:12", colors.accent)
        DiagnosticRow("SYNC", "ACTIVE", colors.positive)
        DiagnosticRow("API", "GEMINI-1.5-PRO", colors.primary)
    }
}

@Composable
private fun DiagnosticRow(label: String, value: String, color: Color) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 8.sp, color = Color.Gray)
        Text(value, fontSize = 8.sp, color = color, fontWeight = FontWeight.Bold)
    }
}
