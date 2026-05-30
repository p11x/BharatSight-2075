package com.bharatsight2075.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.Dimensions
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun AdvancedTelemetryDrawer(
    selectedRoute: String = "macro",
    onNavigate: (String) -> Unit = {}
) {
    val extendedColors = SciFiTheme.extendedColors
    
    ModalDrawerSheet(
        drawerContainerColor = extendedColors.background.copy(alpha = 0.96f),
        drawerContentColor = extendedColors.textPrimary,
        modifier = Modifier
            .width(Dimensions.DrawerWidth)
            .border(width = 2.dp, brush = Brush.horizontalGradient(listOf(extendedColors.primary, Color.Transparent)), shape = RoundedCornerShape(0.dp)),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(extendedColors.primary.copy(alpha = 0.05f), Color.Transparent)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimensions.PaddingLarge)
                    .verticalScroll(rememberScrollState())
            ) {
                HeaderLogo()
                
                Spacer(modifier = Modifier.height(24.dp))
                
                DrawerSectionHeader(title = "OVERVIEW")
                NavStaggeredItem(0, "Dashboard Summary", Icons.Outlined.Dashboard, selectedRoute == "macro") { onNavigate("macro") }
                NavStaggeredItem(1, "India 3D Globe", Icons.Outlined.Public, selectedRoute == "globe") { onNavigate("globe") }
                NavStaggeredItem(2, "AI Query Engine", Icons.Outlined.Psychology, selectedRoute == "query") { onNavigate("query") }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                DrawerSectionHeader(title = "MACRO & OBSERVATORY")
                NavStaggeredItem(3, "Macro Indicator Hub", Icons.Outlined.Analytics, selectedRoute == "observatory") { onNavigate("observatory") }
                NavStaggeredItem(4, "GDP & Growth Rate", Icons.Outlined.TrendingUp, selectedRoute == "forecaster") { onNavigate("forecaster") }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                DrawerSectionHeader(title = "MARKETS & TRADE")
                NavStaggeredItem(5, "Nifty 500 Heatmap", Icons.Outlined.BarChart, selectedRoute == "market") { onNavigate("market") }
                NavStaggeredItem(6, "Global Trade Network", Icons.Outlined.Hub, selectedRoute == "trade") { onNavigate("trade") }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                DrawerSectionHeader(title = "SYSTEM PREFERENCES")
                SegmentedThemeToggle()
                
                Spacer(modifier = Modifier.height(24.dp))
                
                SystemDiagnosticsPanel()
            }
        }
    }
}

@Composable
fun NavStaggeredItem(
    index: Int,
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    var startAnimate by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimate = true }
    
    val offsetX by animateDpAsState(
        targetValue = if (startAnimate) 0.dp else (-20).dp,
        animationSpec = tween(400, delayMillis = index * 40),
        label = "SlideIn"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) extendedColors.primary.copy(alpha = 0.1f) else Color.Transparent,
        label = "BgColor"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = offsetX)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(Dimensions.NavIndicatorWidth)
                    .height(20.dp)
                    .background(extendedColors.primary, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(Dimensions.NavIconSize),
            tint = if (isSelected) extendedColors.primary else extendedColors.primary.copy(alpha = 0.4f)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = title,
            style = SciFiTheme.typography.BodyMono.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = if (isSelected) extendedColors.primary else extendedColors.textPrimary.copy(alpha = 0.7f),
            fontSize = 13.sp
        )
    }
}

@Composable
fun DrawerSectionHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.width(16.dp),
            thickness = 1.dp,
            color = SciFiTheme.extendedColors.primary.copy(alpha = 0.2f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = SciFiTheme.typography.SectionHead,
            color = SciFiTheme.extendedColors.primary.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SegmentedThemeToggle() {
    val extendedColors = SciFiTheme.extendedColors
    val currentTheme = SciFiTheme.current
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .border(1.dp, extendedColors.primary.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp))
    ) {
        ThemeOption("CYBERPUNK", currentTheme == SciFiTheme.Theme.Cyberpunk, Modifier.weight(1f))
        ThemeOption("HOLOGRAM", currentTheme == SciFiTheme.Theme.Hologram, Modifier.weight(1f))
    }
}

@Composable
fun ThemeOption(label: String, isActive: Boolean, modifier: Modifier) {
    val extendedColors = SciFiTheme.extendedColors
    val bgColor by animateColorAsState(
        targetValue = if (isActive) extendedColors.primary else Color.Transparent,
        label = "OptionBg"
    )
    
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(bgColor)
            .clickable { /* Trigger theme switch via SciFiTheme.ProvideSciFiTheme in root */ },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = SciFiTheme.typography.ChartCaption,
            color = if (isActive) Color.Black else extendedColors.primary.copy(alpha = 0.5f),
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun SystemDiagnosticsPanel() {
    var expanded by remember { mutableStateOf(false) }
    val extendedColors = SciFiTheme.extendedColors

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "SYSTEM DIAGNOSTICS",
                style = SciFiTheme.typography.SectionHead,
                color = extendedColors.positive.copy(alpha = 0.8f)
            )
            Icon(
                imageVector = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                contentDescription = null,
                tint = extendedColors.positive,
                modifier = Modifier.size(16.dp)
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DiagnosticRow("ML CORE VER", "v2.0.75-NANO")
                DiagnosticRow("LAST SYNC", "14:20:05 IST")
                DiagnosticRow("DB STATUS", "ENCRYPTED / 4.2MB")
            }
        }
    }
}

@Composable
fun DiagnosticRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = SciFiTheme.typography.ChartCaption, color = SciFiTheme.extendedColors.textSecondary)
        Text(value, style = SciFiTheme.typography.ChartCaption, color = SciFiTheme.extendedColors.primary)
    }
}

@Composable
fun HeaderLogo() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(48.dp)) {
        drawRoundRect(
            color = Color(0xFF1E1E1E),
            topLeft = Offset.Zero,
            size = size,
            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
        )
        drawLine(
            start = Offset(16.dp.toPx(), 12.dp.toPx()),
            end = Offset(16.dp.toPx(), 36.dp.toPx()),
            color = Color(0xFFFF9500),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            start = Offset(12.dp.toPx(), 16.dp.toPx()),
            end = Offset(24.dp.toPx(), 16.dp.toPx()),
            color = Color(0xFFFF9500),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            start = Offset(12.dp.toPx(), 28.dp.toPx()),
            end = Offset(24.dp.toPx(), 28.dp.toPx()),
            color = Color(0xFFFF9500),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            start = Offset(20.dp.toPx(), 28.dp.toPx()),
            end = Offset(28.dp.toPx(), 20.dp.toPx()),
            color = Color(0xFF00E5FF),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            start = Offset(28.dp.toPx(), 20.dp.toPx()),
            end = Offset(36.dp.toPx(), 12.dp.toPx()),
            color = Color(0xFF00E5FF),
            strokeWidth = 2.dp.toPx()
        )
    }
}
