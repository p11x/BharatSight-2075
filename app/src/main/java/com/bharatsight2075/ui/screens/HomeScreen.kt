package com.bharatsight2075.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarAction
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.microinteraction.DecodingText
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import com.bharatsight2075.ui.theme.SciFiTheme

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onMenuClick: () -> Unit,
    onSettingsClick: () -> Unit,
    userName: String = "Pavan",
    notificationCount: Int = 3
) {
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Home(
                    userName = userName,
                    notificationCount = notificationCount,
                    onMenuClick = onMenuClick,
                    onNotificationsClick = { /* Handle */ },
                    onSettingsClick = onSettingsClick
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        GridBackgroundSurface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { HomeHeaderSection() }

                // --- CORE OBSERVATORY ---
                item { SectionGroupHeader("CORE OBSERVATORY") }
                
                item {
                    SectionCard(
                        index = 0,
                        title = "Macro Overview",
                        badge = "LIVE",
                        icon = Icons.Outlined.Dashboard,
                        accentColor = SciFiTheme.extendedColors.primary,
                        description = "Live GDP, inflation, repo rate & key economic indicators",
                        metrics = listOf("$37T" to "GDP 2075", "4.2%" to "Inflation", "8.1%" to "Growth"),
                        onClick = { onNavigate(Routes.MACRO) }
                    )
                }

                item {
                    SectionCard(
                        index = 1,
                        title = "Economic Forecaster",
                        badge = "AI",
                        icon = Icons.Outlined.Timeline,
                        accentColor = SciFiTheme.extendedColors.accent,
                        description = "Simulate 50-year GDP trajectories with policy parameters",
                        metrics = listOf("4" to "Scenarios", "50yr" to "Horizon", "TFLite" to "Model"),
                        onClick = { onNavigate(Routes.FORECASTER) }
                    )
                }

                item {
                    SectionCard(
                        index = 2,
                        title = "India Economic Map",
                        badge = "36 STATES",
                        icon = Icons.Outlined.Public,
                        accentColor = Color(0xFF00E676),
                        description = "Interactive state map with FDI and trade arc visualizations",
                        metrics = listOf("36" to "States", "142" to "Trade Arcs", "Live" to "FDI Flows"),
                        onClick = { onNavigate(Routes.GLOBE) }
                    )
                }

                item {
                    SectionCard(
                        index = 3,
                        title = "Macro Indicator Hub",
                        badge = "LIVE",
                        icon = Icons.Outlined.TrackChanges,
                        accentColor = Color(0xFFFFD600),
                        description = "CPI · WPI · IIP · PMI · Fiscal Deficit · FX Reserves orbit panel",
                        metrics = listOf("12" to "Indicators", "3" to "Alerts", "1" to "Anomalies"),
                        onClick = { onNavigate(Routes.OBSERVATORY) }
                    )
                }

                // --- SECTORS & INDUSTRIES ---
                item { SectionGroupHeader("SECTORS & INDUSTRIES") }
                
                item {
                    SectionCard(
                        index = 4,
                        title = "Agriculture",
                        badge = "KHARIF",
                        icon = Icons.Outlined.Grass,
                        accentColor = Color(0xFF76C442),
                        description = "Crop production, MSP trends and monsoon impact analytics",
                        metrics = listOf("23" to "Crops", "28" to "States", "₹2,183" to "MSP"),
                        onClick = { onNavigate(Routes.AGRICULTURE) }
                    )
                }

                item {
                    SectionCard(
                        index = 5,
                        title = "Banking & Finance",
                        badge = "RBI",
                        icon = Icons.Outlined.AccountBalance,
                        accentColor = Color(0xFF00B0FF),
                        description = "Credit growth, NPA analysis and interbank flow networks",
                        metrics = listOf("156" to "Banks", "3.9%" to "NPA", "16.8%" to "CRAR"),
                        onClick = { onNavigate(Routes.BANKING) }
                    )
                }

                item {
                    SectionCard(
                        index = 6,
                        title = "Energy & Power",
                        badge = "RENEWABLE",
                        icon = Icons.Outlined.Bolt,
                        accentColor = Color(0xFFFFD600),
                        description = "Grid load, solar capacity race and carbon intensity metrics",
                        metrics = listOf("950GW" to "Capacity", "46%" to "Renewable", "0.4%" to "Deficit"),
                        onClick = { onNavigate(Routes.ENERGY) }
                    )
                }

                item {
                    SectionCard(
                        index = 7,
                        title = "Smart Cities",
                        badge = "GATI SHAKTI",
                        icon = Icons.Outlined.LocationCity,
                        accentColor = Color(0xFF4FC3F7),
                        description = "Urban infrastructure, NH network and metro ridership data",
                        metrics = listOf("100" to "Cities", "1.46L" to "NH km", "945" to "Metro km"),
                        onClick = { onNavigate(Routes.SMART_CITIES) }
                    )
                }

                item {
                    SectionCard(
                        index = 8,
                        title = "Startup Ecosystem",
                        badge = "113 UNICORNS",
                        icon = Icons.Outlined.RocketLaunch,
                        accentColor = Color(0xFF7C4DFF),
                        description = "Funding race, deal flow sankey and innovation radar",
                        metrics = listOf("1.2L" to "Startups", "113" to "Unicorns", "$42B" to "Funding"),
                        onClick = { onNavigate(Routes.STARTUP) }
                    )
                }

                // --- ECONOMY & SOCIETY ---
                item { SectionGroupHeader("ECONOMY & SOCIETY") }

                item {
                    SectionCard(
                        index = 9,
                        title = "Defence Strategic",
                        badge = "ATMANIRBHAR",
                        icon = Icons.Outlined.Security,
                        accentColor = Color(0xFFFF5252),
                        description = "Indigenisation, modernisation funnel and threat radar",
                        metrics = listOf("₹6.2L Cr" to "Budget", "$2.5B" to "Export", "350+" to "HAL Ord"),
                        onClick = { onNavigate(Routes.DEFENCE) }
                    )
                }

                item {
                    SectionCard(
                        index = 10,
                        title = "Climate & Green",
                        badge = "NET ZERO",
                        icon = Icons.Outlined.EnergySavingsLeaf,
                        accentColor = Color(0xFF00E676),
                        description = "Carbon budget, air quality and green finance flows",
                        metrics = listOf("2.8GT" to "CO₂", "24%" to "Forest", "45%" to "NDC"),
                        onClick = { onNavigate(Routes.CLIMATE) }
                    )
                }

                item {
                    SectionCard(
                        index = 11,
                        title = "Digital Economy",
                        badge = "₹ UPI",
                        icon = Icons.Outlined.PhoneAndroid,
                        accentColor = Color(0xFF00F5FF),
                        description = "Digital adoption, UPI waves and fintech unicorn bubbles",
                        metrics = listOf("14B/mo" to "UPI Txn", "850M" to "Internet", "$120B" to "e-Comm"),
                        onClick = { onNavigate(Routes.DIGITAL_ECONOMY) }
                    )
                }

                item {
                    SectionCard(
                        index = 12,
                        title = "Education & Skill",
                        badge = "NEP 2020",
                        icon = Icons.Outlined.School,
                        accentColor = Color(0xFFFFC107),
                        description = "Literacy spiral, skill gap radar and edtech investment",
                        metrics = listOf("28.4%" to "GER", "51M/yr" to "Skilled", "56K+" to "Inst"),
                        onClick = { onNavigate(Routes.EDUCATION) }
                    )
                }

                item {
                    SectionCard(
                        index = 13,
                        title = "Healthcare & Pharma",
                        badge = "GLOBAL HUB",
                        icon = Icons.Outlined.HealthAndSafety,
                        accentColor = Color(0xFFF06292),
                        description = "Disease burden hex, life expectancy and pharma exports",
                        metrics = listOf("$50B" to "Pharma", "71K" to "Hospitals", "540M" to "ABDM"),
                        onClick = { onNavigate(Routes.HEALTHCARE) }
                    )
                }

                // --- MARKETS & ASSETS ---
                item { SectionGroupHeader("MARKETS & ASSETS") }

                item {
                    SectionCard(
                        index = 14,
                        title = "Real Estate",
                        badge = "RERA",
                        icon = Icons.Outlined.Home,
                        accentColor = Color(0xFFFF8A65),
                        description = "City price hex, rental yields and inventory state race",
                        metrics = listOf("₹7,200" to "Index", "4.35L" to "Units", "6.5L" to "Unsold"),
                        onClick = { onNavigate(Routes.REAL_ESTATE) }
                    )
                }

                item {
                    SectionCard(
                        index = 15,
                        title = "Tourism",
                        badge = "INCREDIBLE",
                        icon = Icons.Outlined.TravelExplore,
                        accentColor = Color(0xFFFFD54F),
                        description = "Visitor origin sankey, pilgrimage growth and hotel occupancy",
                        metrics = listOf("9.2M" to "Foreign", "2.5B" to "Domestic", "$28B" to "Forex"),
                        onClick = { onNavigate(Routes.TOURISM) }
                    )
                }

                item {
                    SectionCard(
                        index = 16,
                        title = "Space Tech",
                        badge = "ISRO",
                        icon = Icons.Outlined.Brightness7,
                        accentColor = Color(0xFF9C27B0),
                        description = "Launch race, satellite constellations and mission timelines",
                        metrics = listOf("48/yr" to "Launches", "180+" to "Startups", "₹13K Cr" to "Budget"),
                        onClick = { onNavigate(Routes.SPACE_TECH) }
                    )
                }

                item {
                    SectionCard(
                        index = 17,
                        title = "Geopolitical Risk",
                        badge = "LIVE ALERT",
                        icon = Icons.Outlined.Gavel,
                        accentColor = Color(0xFFFF1744),
                        description = "Threat pulse radar, trade dependency and risk heatmap",
                        metrics = listOf("42" to "Risk", "7" to "Borders", "3" to "Conflicts"),
                        onClick = { onNavigate(Routes.GEO_RISK) }
                    )
                }

                item {
                    SectionCard(
                        index = 18,
                        title = "Inequality",
                        badge = "GINI",
                        icon = Icons.Outlined.AlignVerticalBottom,
                        accentColor = Color(0xFFE040FB),
                        description = "Wealth distribution, Lorenz curve and poverty timelines",
                        metrics = listOf("0.357" to "Gini", "22%" to "Top 1%", "3%" to "Bottom 50%"),
                        onClick = { onNavigate(Routes.INEQUALITY) }
                    )
                }

                // --- DEEP ANALYTICS ---
                item { SectionGroupHeader("DEEP ANALYTICS") }

                item {
                    SectionCard(
                        index = 19,
                        title = "Labour Market",
                        badge = "CMIE",
                        icon = Icons.Outlined.Groups,
                        accentColor = Color(0xFF80CBC4),
                        description = "LFP rates, gender divide and youth unemployment trends",
                        metrics = listOf("42.6%" to "LFP Rate", "21%" to "Formal", "7.2%" to "Wage Growth"),
                        onClick = { onNavigate(Routes.LABOUR) }
                    )
                }

                item {
                    SectionCard(
                        index = 20,
                        title = "Logistics",
                        badge = "PM GATI",
                        icon = Icons.Outlined.LocalShipping,
                        accentColor = Color(0xFFFFB74D),
                        description = "Port traffic, cold chain and customs dwell timelines",
                        metrics = listOf("3.44" to "LPI", "₹2,100C" to "Cold Chain", "12.3M" to "Port TEU"),
                        onClick = { onNavigate(Routes.LOGISTICS) }
                    )
                }

                item {
                    SectionCard(
                        index = 21,
                        title = "Media Economy",
                        badge = "OTT",
                        icon = Icons.Outlined.Movie,
                        accentColor = Color(0xFFFF4081),
                        description = "Digital consumption, podcast growth and ad spend trends",
                        metrics = listOf("550M" to "OTT Subs", "₹12K Cr" to "Box Office", "₹1.1L Cr" to "Ad Spend"),
                        onClick = { onNavigate(Routes.MEDIA) }
                    )
                }

                item {
                    SectionCard(
                        index = 22,
                        title = "Natural Resources",
                        badge = "CRZ",
                        icon = Icons.Outlined.Water,
                        accentColor = Color(0xFF4DD0E1),
                        description = "Water table levels, mineral types and resource risk",
                        metrics = listOf("High" to "Stress", "95" to "Minerals", "$7B" to "Fisheries"),
                        onClick = { onNavigate(Routes.NATURAL_RESOURCES) }
                    )
                }

                item {
                    SectionCard(
                        index = 23,
                        title = "Soft Power",
                        badge = "BRAND INDIA",
                        icon = Icons.Outlined.FavoriteBorder,
                        accentColor = Color(0xFFFFD700),
                        description = "Nation brand, diaspora reach and linguistic influence",
                        metrics = listOf("$2.6T" to "Brand Value", "32M" to "Diaspora", "300M" to "Practitioners"),
                        onClick = { onNavigate(Routes.SOFT_POWER) }
                    )
                }

                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
fun SectionGroupHeader(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = SciFiTheme.extendedColors.primary.copy(alpha = 0.2f))
        Text(
            text = name,
            style = SciFiTheme.typography.SectionHead,
            color = SciFiTheme.extendedColors.primary.copy(alpha = 0.5f),
            letterSpacing = 0.15.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = SciFiTheme.extendedColors.primary.copy(alpha = 0.2f))
    }
}

@Composable
fun HomeHeaderSection() {
    val extendedColors = SciFiTheme.extendedColors
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "BHARATSIGHT",
                        style = SciFiTheme.typography.HeroNumber.copy(fontSize = 28.sp),
                        color = extendedColors.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "2075",
                        style = SciFiTheme.typography.HeroNumber.copy(fontSize = 18.sp),
                        color = extendedColors.accent
                    )
                }
                Text(
                    text = "INDIA ECONOMIC INTELLIGENCE PLATFORM",
                    style = SciFiTheme.typography.MetricLabel,
                    color = extendedColors.textSecondary.copy(alpha = 0.6f)
                )
            }
            
            val infiniteTransition = rememberInfiniteTransition(label = "RupeeRotation")
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing)), label = "Rotation"
            )
            
            Text(
                text = "₹",
                fontSize = 54.sp,
                color = extendedColors.primary,
                modifier = Modifier.graphicsLayer { rotationY = rotation }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KpiChip("GDP ▲12.4%")
            KpiChip("SENSEX 81,234")
            KpiChip("₹/$ 83.2")
        }
    }
}

@Composable
fun KpiChip(text: String) {
    Surface(
        color = SciFiTheme.extendedColors.surface.copy(alpha = 0.3f),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SciFiTheme.extendedColors.primary.copy(alpha = 0.2f))
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.8f),
            style = SciFiTheme.typography.BodyMono
        )
    }
}

@Composable
fun SectionCard(
    index: Int,
    title: String,
    badge: String?,
    icon: ImageVector,
    accentColor: Color,
    description: String,
    metrics: List<Pair<String, String>>,
    onClick: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
    // Animation logic
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(animationSpec = tween(400, delayMillis = index * 100)) + fadeIn()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0E0E1A))
                .border(1.dp, Brush.linearGradient(listOf(accentColor.copy(alpha = 0.4f), Color.Transparent)), RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .drawBehind {
                    drawRect(Brush.verticalGradient(listOf(accentColor.copy(alpha = 0.05f), Color.Transparent), endY = 40f))
                }
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(title, style = SciFiTheme.typography.SectionHead, color = Color.White)
                    if (badge != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = accentColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp),
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, accentColor.copy(alpha = 0.5f))
                        ) {
                            Text(badge, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 8.sp, color = accentColor)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                Text(description, fontSize = 12.sp, color = extendedColors.textSecondary)
                
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    metrics.forEach { (value, label) ->
                        Column {
                            Text(value, style = SciFiTheme.typography.BodyMono, color = accentColor, fontWeight = FontWeight.Bold)
                            Text(label.uppercase(), style = SciFiTheme.typography.ChartCaption, color = extendedColors.textDisabled)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                Text("EXPLORE →", fontSize = 11.sp, color = accentColor, fontWeight = FontWeight.Bold)
                
                Text(
                    "Updated 2 min ago",
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 9.sp,
                    color = extendedColors.textDisabled.copy(alpha = 0.5f)
                )
            }
        }
    }
}
