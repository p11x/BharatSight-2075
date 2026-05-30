package com.bharatsight2075.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.microinteraction.crtScanlineOverlay
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import com.bharatsight2075.ui.theme.SciFiTheme
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onMenuClick: () -> Unit,
    onSettingsClick: () -> Unit,
    userName: String = "Pavan",
    notificationCount: Int = 0
) {
    BackHandler { /* Handle Home back logic */ }

    GridBackgroundSurface(modifier = Modifier.fillMaxSize().crtScanlineOverlay()) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                BharatSightTopBar(
                    mode = TopBarMode.Home(
                        userName = userName,
                        onMenuClick = onMenuClick,
                        onSettingsClick = onSettingsClick,
                        notificationCount = notificationCount
                    )
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { HomeHeaderSection() }

                item {
                    SectionCard(
                        index = 0,
                        title = "Macro Overview",
                        subtitle = "Live GDP, inflation, repo rate & key economic indicators",
                        icon = Icons.Outlined.Dashboard,
                        accentColor = SciFiTheme.extendedColors.primary,
                        badge = "LIVE",
                        metrics = listOf("GDP 2075" to "$37T", "Inflation" to "4.2%", "Growth" to "8.1%"),
                        onClick = { onNavigate(Routes.MACRO) }
                    )
                }

                item {
                    SectionCard(
                        index = 1,
                        title = "Economic Forecaster",
                        subtitle = "Simulate 50-year GDP trajectories with policy parameters",
                        icon = Icons.AutoMirrored.Outlined.TrendingUp,
                        accentColor = SciFiTheme.extendedColors.accent,
                        badge = "AI",
                        metrics = listOf("Scenarios" to "4", "Horizon" to "50yr", "Model" to "TFLite"),
                        onClick = { onNavigate(Routes.FORECASTER) }
                    )
                }

                item {
                    SectionCard(
                        index = 2,
                        title = "India Economic Map",
                        subtitle = "Interactive state map with FDI and trade arc visualizations",
                        icon = Icons.Outlined.Public,
                        accentColor = Color(0xFF00E676),
                        badge = "36 STATES",
                        metrics = listOf("States" to "36", "Trade Arcs" to "142", "FDI Flows" to "Live"),
                        onClick = { onNavigate(Routes.GLOBE) }
                    )
                }

                item {
                    SectionCard(
                        index = 3,
                        title = "Macro Indicator Hub",
                        subtitle = "CPI · WPI · IIP · PMI · Fiscal Deficit · FX Reserves orbit panel",
                        icon = Icons.Outlined.Radar,
                        accentColor = Color(0xFFFFD600),
                        badge = "LIVE",
                        metrics = listOf("Indicators" to "12", "Alerts" to "3", "Anomalies" to "1"),
                        onClick = { onNavigate(Routes.OBSERVATORY) }
                    )
                }

                item {
                    SectionCard(
                        index = 4,
                        title = "State Deep Dive",
                        subtitle = "GSDP profiles, HDI, and 2035 forecasts for all 36 states & UTs",
                        icon = Icons.Outlined.Map,
                        accentColor = Color(0xFFB39DDB),
                        badge = "36 STATES",
                        metrics = listOf("States" to "36", "UTs" to "8", "Years" to "2000–35"),
                        onClick = { onNavigate(Routes.STATES) }
                    )
                }

                item {
                    SectionCard(
                        index = 5,
                        title = "Nifty 500 Heatmap",
                        subtitle = "Live market cap treemap with 1-day return color coding",
                        icon = Icons.Outlined.BarChart,
                        accentColor = Color(0xFF4FC3F7),
                        badge = "LIVE",
                        metrics = listOf("Stocks" to "500", "Sectors" to "12", "Refresh" to "5s"),
                        onClick = { onNavigate(Routes.MARKET) }
                    )
                }

                item {
                    SectionCard(
                        index = 6,
                        title = "Global Trade Network",
                        subtitle = "Force-directed graph of India's top 30 trading partners",
                        icon = Icons.Outlined.AccountTree,
                        accentColor = Color(0xFFFF6B35),
                        badge = "30 PARTNERS",
                        metrics = listOf("Partners" to "30", "Trade Vol" to "$1.2T", "Surplus" to "8"),
                        onClick = { onNavigate(Routes.TRADE) }
                    )
                }

                item {
                    SectionCard(
                        index = 7,
                        title = "AI Query Engine",
                        subtitle = "Ask natural language questions about India's economy",
                        icon = Icons.Outlined.Psychology,
                        accentColor = Color(0xFF7C4DFF),
                        badge = "GEMINI NANO",
                        metrics = listOf("Model" to "Gemini Nano", "Language" to "Hi+En", "Mode" to "On-device"),
                        onClick = { onNavigate(Routes.QUERY) }
                    )
                }

                item {
                    SectionCard(
                        index = 8,
                        title = "Sector Analysis",
                        subtitle = "Radar chart breakdown of 8 economic sectors",
                        icon = Icons.Outlined.DonutSmall,
                        accentColor = SciFiTheme.extendedColors.primary,
                        onClick = { onNavigate(Routes.SECTOR) }
                    )
                }

                item {
                    SectionCard(
                        index = 9,
                        title = "Demographics",
                        subtitle = "Population pyramid, urbanization trends, and HDI",
                        icon = Icons.Outlined.People,
                        accentColor = Color(0xFFF06292),
                        badge = "2075 PROJ",
                        onClick = { onNavigate(Routes.DEMOGRAPHICS) }
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "BharatSight 2075 · Data updated ${System.currentTimeMillis()}",
                            style = SciFiTheme.typography.ChartCaption,
                            color = SciFiTheme.extendedColors.primary.copy(0.4f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeaderSection() {
    val extendedColors = SciFiTheme.extendedColors
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(extendedColors.primary.copy(0.15f), extendedColors.accent.copy(0.08f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, extendedColors.primary.copy(0.3f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "BHARATSIGHT",
                    style = SciFiTheme.typography.HeroNumber.copy(fontSize = 22.sp),
                    color = extendedColors.primary,
                    letterSpacing = 0.08.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "2075",
                    style = SciFiTheme.typography.BodyMono.copy(fontSize = 14.sp),
                    color = extendedColors.accent
                )
            }
            Text(
                text = "INDIA ECONOMIC INTELLIGENCE PLATFORM",
                style = SciFiTheme.typography.ChartCaption.copy(fontSize = 9.sp),
                color = extendedColors.primary.copy(0.6f),
                letterSpacing = 0.15.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Rupee Symbol Animation
        val rotationTransition = rememberInfiniteTransition(label = "RupeeRotation")
        val rotation by rotationTransition.animateFloat(
            initialValue = -5f,
            targetValue = 5f,
            animationSpec = infiniteRepeatable(tween(3000, easing = EaseInOutSine), RepeatMode.Reverse),
            label = "rotation"
        )
        
        Text(
            text = "₹",
            style = SciFiTheme.typography.HeroNumber.copy(fontSize = 56.sp),
            color = extendedColors.primary,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .graphicsLayer { rotationZ = rotation }
        )

        Row(
            modifier = Modifier.align(Alignment.BottomStart),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KpiChip("GDP ▲12.4%")
            KpiChip("SENSEX 81,234")
            KpiChip("₹/$ 83.2")
        }
    }
}

@Composable
fun KpiChip(text: String) {
    Box(
        modifier = Modifier
            .border(1.dp, SciFiTheme.extendedColors.primary.copy(0.4f), RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            style = SciFiTheme.typography.ChartCaption.copy(fontSize = 10.sp),
            color = SciFiTheme.extendedColors.textPrimary
        )
    }
}

@Composable
fun SectionCard(
    index: Int,
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    badge: String? = null,
    metrics: List<Pair<String, String>> = emptyList(),
    onClick: () -> Unit
) {
    val isAnimEnabled = true 
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(stiffness = 400f),
        label = "PressScale"
    )

    var visible by remember { mutableStateOf(!isAnimEnabled) }
    LaunchedEffect(Unit) {
        if (isAnimEnabled) {
            delay(index * 80L)
            visible = true
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { 30.dp.value.toInt() } + fadeIn(tween(400)),
        modifier = Modifier.scale(scale)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(interactionSource = interactionSource, indication = null) { onClick() }
                .border(1.dp, accentColor.copy(0.35f), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = SciFiTheme.extendedColors.surface
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(accentColor.copy(0.06f), Color.Transparent),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
                        )
                    )
            ) {
                Row(modifier = Modifier.fillMaxHeight()) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .fillMaxHeight()
                            .background(accentColor)
                    )
                    
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = title, style = SciFiTheme.typography.BodyMono.copy(fontWeight = FontWeight.SemiBold, fontSize = 14.sp), color = Color.White)
                            
                            badge?.let {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .border(1.dp, accentColor.copy(0.7f), RoundedCornerShape(4.dp))
                                        .background(accentColor.copy(0.12f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = it, color = accentColor, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        
                        Text(
                            text = subtitle,
                            style = SciFiTheme.typography.ChartCaption.copy(fontSize = 11.sp),
                            color = SciFiTheme.extendedColors.textPrimary.copy(0.6f),
                            modifier = Modifier.padding(top = 4.dp),
                            maxLines = 2
                        )
                        
                        if (metrics.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                metrics.forEachIndexed { i, metric ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = metric.second, style = SciFiTheme.typography.BodyMono.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp), color = accentColor)
                                        Text(text = metric.first, style = SciFiTheme.typography.ChartCaption.copy(fontSize = 9.sp), color = Color.White.copy(0.5f))
                                    }
                                    if (i < metrics.size - 1) {
                                        Box(modifier = Modifier.width(1.dp).height(20.dp).background(Color.White.copy(0.1f)))
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "EXPLORE →", style = SciFiTheme.typography.ChartCaption.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp), color = accentColor)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Updated 2 min ago", style = SciFiTheme.typography.ChartCaption.copy(fontSize = 9.sp), color = Color.White.copy(0.4f))
                        }
                    }
                }
            }
        }
    }
}
