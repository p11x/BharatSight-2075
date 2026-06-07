package com.bharatsight2075.ui.navigation

import android.graphics.BlurMaskFilter
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.AirplaneTicket
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.screens.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BharatSightSideDrawer(
    selectedRoute: String,
    onNavigate: (String) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    val configuration = LocalConfiguration.current
    val drawerWidth = configuration.screenWidthDp.dp * 0.82f

    // Scanline sweep animation on open
    var playScanline by remember { mutableStateOf(false) }
    val scanlineProgress = remember { Animatable(0f) }

    LaunchedEffect(drawerState.isOpen) {
        if (drawerState.isOpen) {
            playScanline = true
            scanlineProgress.snapTo(0f)
            scanlineProgress.animateTo(1f, tween(600, easing = LinearEasing))
            playScanline = false
        }
    }

    ModalDrawerSheet(
        modifier = Modifier.width(drawerWidth).fillMaxHeight(),
        drawerContainerColor = Color.Transparent, // We draw our own gradient
        drawerTonalElevation = 0.dp
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF020B18), Color(0xFF0A1628), Color(0xFF020B18))
                    )
                )
        ) {
            // LEFT EDGE GLOW
            Canvas(Modifier.fillMaxHeight().width(10.dp)) {
                drawIntoCanvas { canvas ->
                    val paint = android.graphics.Paint().apply {
                        color = android.graphics.Color.CYAN
                        strokeWidth = 2.dp.toPx()
                        style = android.graphics.Paint.Style.STROKE
                        maskFilter = BlurMaskFilter(12f, BlurMaskFilter.Blur.SOLID)
                    }
                    canvas.nativeCanvas.drawLine(0f, 0f, 0f, size.height, paint)
                }
                drawLine(Color(0xFF00FFFF), Offset(0f, 0f), Offset(0f, size.height), 2.dp.toPx())
            }

            // SCANLINE SWEEP
            if (playScanline) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(configuration.screenHeightDp.dp)
                        .drawBehind {
                            val y = scanlineProgress.value * size.height
                            val alpha = if (scanlineProgress.value < 0.5f) {
                                (scanlineProgress.value / 0.5f) * 0.06f
                            } else {
                                (1f - (scanlineProgress.value - 0.5f) / 0.5f) * 0.06f
                            }
                            drawRect(
                                color = Color(0xFF00FFFF).copy(alpha = alpha),
                                topLeft = Offset(0f, y - 50.dp.toPx()),
                                size = androidx.compose.ui.geometry.Size(size.width, 100.dp.toPx())
                            )
                        }
                )
            }

            Column(Modifier.fillMaxSize()) {
                // HEADER
                DrawerHeader(scope, drawerState)

                // NAV ITEMS
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    // GROUP 1: MAIN
                    item { DrawerSectionDivider("◈ MAIN") }
                    val group1 = listOf(
                        NavItem(Icons.Outlined.Home, "Home", Routes.HOME),
                        NavItem(Icons.Outlined.BarChart, "Compare", Routes.COMPARE),
                        NavItem(Icons.Outlined.Bolt, "Updates", Routes.UPDATES, badge = "LIVE", badgeColor = Color(0xFF00E676)),
                        NavItem(Icons.Outlined.AutoAwesome, "Ask AI", Routes.ASK, badge = "GEMINI", badgeColor = Color(0xFF7C4DFF)),
                        NavItem(Icons.Outlined.Person, "Profile", Routes.PROFILE)
                    )
                    group1.forEachIndexed { index, item ->
                        item {
                            DrawerNavItemRow(
                                icon = item.icon, label = item.label,
                                isSelected = selectedRoute == item.route, index = index,
                                isOpen = drawerState.isOpen, badge = item.badge, badgeColor = item.badgeColor
                            ) {
                                scope.launch {
                                    drawerState.close()
                                    delay(150)
                                    onNavigate(item.route)
                                }
                            }
                        }
                    }

                    // GROUP 2: CORE OBSERVATORY
                    item { DrawerSectionDivider("◈ CORE OBSERVATORY") }
                    val group2 = listOf(
                        NavItem(Icons.Outlined.Dashboard, "Macro Overview", Routes.MACRO_OVERVIEW, badge = "LIVE"),
                        NavItem(Icons.AutoMirrored.Outlined.TrendingUp, "Economic Forecaster", Routes.FORECASTER, badge = "AI"),
                        NavItem(Icons.Outlined.Public, "India Economic Map", Routes.INDIA_GLOBE),
                        NavItem(Icons.Outlined.Radar, "Macro Indicator Hub", Routes.MACRO_HUB, badge = "NEW"),
                        NavItem(Icons.Outlined.Map, "State Deep Dive", Routes.STATE_DIVE),
                        NavItem(Icons.Outlined.DonutSmall, "Sector Analysis", Routes.SECTOR_DIVE),
                        NavItem(Icons.Outlined.People, "Demographics", Routes.DEMOGRAPHICS),
                        NavItem(Icons.Outlined.Layers, "India Maps (30)", Routes.MAPS)
                    )
                    group2.forEachIndexed { index, item ->
                        item {
                            DrawerNavItemRow(
                                icon = item.icon, label = item.label,
                                isSelected = selectedRoute == item.route, index = index + group1.size,
                                isOpen = drawerState.isOpen, badge = item.badge
                            ) {
                                scope.launch {
                                    drawerState.close()
                                    delay(150)
                                    onNavigate(item.route)
                                }
                            }
                        }
                    }

                    // GROUP 3: SECTORS & INDUSTRIES
                    item { DrawerSectionDivider("◈ SECTORS & INDUSTRIES") }
                    val group3 = listOf(
                        NavItem(Icons.Outlined.Grass, "Agriculture & Food", Routes.AGRICULTURE),
                        NavItem(Icons.Outlined.AccountBalance, "Banking & Finance", Routes.BANKING),
                        NavItem(Icons.Outlined.Bolt, "Energy & Power", Routes.ENERGY),
                        NavItem(Icons.Outlined.LocationCity, "Smart Cities", Routes.SMART_CITIES),
                        NavItem(Icons.Outlined.RocketLaunch, "Startup Ecosystem", Routes.STARTUPS, badge = "🦄"),
                        NavItem(Icons.Outlined.Security, "Defence Economy", Routes.DEFENCE),
                        NavItem(Icons.Outlined.EnergySavingsLeaf, "Climate & Green", Routes.CLIMATE)
                    )
                    group3.forEachIndexed { index, item ->
                        item {
                            DrawerNavItemRow(
                                icon = item.icon, label = item.label,
                                isSelected = selectedRoute == item.route, index = index + group1.size + group2.size,
                                isOpen = drawerState.isOpen, badge = item.badge
                            ) {
                                scope.launch {
                                    drawerState.close()
                                    delay(150)
                                    onNavigate(item.route)
                                }
                            }
                        }
                    }

                    // GROUP 4: ECONOMY & SOCIETY
                    item { DrawerSectionDivider("◈ ECONOMY & SOCIETY") }
                    val group4 = listOf(
                        NavItem(Icons.Outlined.PhoneAndroid, "Digital Economy", Routes.DIGITAL_ECONOMY),
                        NavItem(Icons.Outlined.School, "Education & Skills", Routes.EDUCATION),
                        NavItem(Icons.Outlined.HealthAndSafety, "Healthcare & Pharma", Routes.HEALTHCARE),
                        NavItem(Icons.Outlined.Home, "Real Estate", Routes.REAL_ESTATE),
                        NavItem(Icons.AutoMirrored.Outlined.AirplaneTicket, "Tourism", Routes.TOURISM),
                        NavItem(Icons.Outlined.Balance, "Wealth & Inequality", Routes.INEQUALITY)
                    )
                    group4.forEachIndexed { index, item ->
                        item {
                            DrawerNavItemRow(
                                icon = item.icon, label = item.label,
                                isSelected = selectedRoute == item.route, index = index + group1.size + group2.size + group3.size,
                                isOpen = drawerState.isOpen
                            ) {
                                scope.launch {
                                    drawerState.close()
                                    delay(150)
                                    onNavigate(item.route)
                                }
                            }
                        }
                    }

                    // GROUP 5: MARKETS & ANALYTICS
                    item { DrawerSectionDivider("◈ MARKETS & ANALYTICS") }
                    val group5 = listOf(
                        NavItem(Icons.Outlined.BarChart, "Nifty 500 Heatmap", Routes.STOCK_HEATMAP, badge = "LIVE"),
                        NavItem(Icons.Outlined.AccountTree, "Trade Network", Routes.TRADE_NETWORK),
                        NavItem(Icons.Outlined.Work, "Labour Market", Routes.LABOUR),
                        NavItem(Icons.Outlined.LocalShipping, "Logistics", Routes.LOGISTICS),
                        NavItem(Icons.Outlined.Movie, "Media & Entertainment", Routes.MEDIA),
                        NavItem(Icons.Outlined.Water, "Natural Resources", Routes.NATURAL_RESOURCES),
                        NavItem(Icons.Outlined.Warning, "Geopolitical Risk", Routes.GEO_RISK, badge = "ALERT")
                    )
                    group5.forEachIndexed { index, item ->
                        item {
                            DrawerNavItemRow(
                                icon = item.icon, label = item.label,
                                isSelected = selectedRoute == item.route, index = index + group1.size + group2.size + group3.size + group4.size,
                                isOpen = drawerState.isOpen, badge = item.badge
                            ) {
                                scope.launch {
                                    drawerState.close()
                                    delay(150)
                                    onNavigate(item.route)
                                }
                            }
                        }
                    }

                    // GROUP 6: DEEP ANALYTICS
                    item { DrawerSectionDivider("◈ DEEP ANALYTICS") }
                    val group6 = listOf(
                        NavItem(Icons.Outlined.RocketLaunch, "Space & Deep Tech", Routes.SPACE_TECH),
                        NavItem(Icons.Outlined.Language, "India's Soft Power", Routes.SOFT_POWER)
                    )
                    group6.forEachIndexed { index, item ->
                        item {
                            DrawerNavItemRow(
                                icon = item.icon, label = item.label,
                                isSelected = selectedRoute == item.route, index = index + group1.size + group2.size + group3.size + group4.size + group5.size,
                                isOpen = drawerState.isOpen
                            ) {
                                scope.launch {
                                    drawerState.close()
                                    delay(150)
                                    onNavigate(item.route)
                                }
                            }
                        }
                    }
                }

                // FOOTER
                DrawerFooter(onNavigate)
            }
        }
    }
}

private data class NavItem(
    val icon: ImageVector,
    val label: String,
    val route: String,
    val badge: String? = null,
    val badgeColor: Color? = null
)

@Composable
private fun DrawerHeader(scope: CoroutineScope, drawerState: DrawerState) {
    val alpha by animateFloatAsState(
        targetValue = if (drawerState.isOpen) 1f else 0f,
        animationSpec = tween(600, easing = LinearOutSlowInEasing),
        label = "headerAlpha"
    )

    Box(Modifier.fillMaxWidth().height(120.dp).padding(20.dp).alpha(alpha)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Hexagonal Logo (Reuse hexagonPath)
            Box(Modifier.size(44.dp)) {
                Canvas(Modifier.fillMaxSize()) {
                    val radius = 20.dp.toPx()
                    val center = Offset(22.dp.toPx(), 22.dp.toPx())
                    drawPath(
                        hexagonPath(center, radius),
                        Brush.linearGradient(listOf(Color(0xFF00FFFF), Color(0xFF006666)))
                    )
                    drawPath(
                        hexagonPath(center, radius - 2.dp.toPx()),
                        Color(0xFF020B18)
                    )
                }
                Icon(Icons.Outlined.Analytics, null, Modifier.size(20.dp).align(Center), tint = Color(0xFF00FFFF))
            }

            Column(Modifier.padding(start = 16.dp).weight(1f)) {
                Text(
                    "BHARATSIGHT",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 0.1.em
                )
                Text(
                    "TELEMETRY HUD v2.4",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFF00FFFF).copy(alpha = 0.7f),
                    letterSpacing = 0.3.em
                )
            }

            IconButton(
                onClick = { scope.launch { drawerState.close() } },
                modifier = Modifier.size(28.dp).border(1.dp, Color(0xFF00FFFF).copy(alpha = 0.3f), RoundedCornerShape(6.dp))
            ) {
                Icon(Icons.Outlined.Close, null, Modifier.size(14.dp), tint = Color(0xFF00FFFF).copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
private fun DrawerNavItemRow(
    icon: ImageVector, label: String, isSelected: Boolean, index: Int,
    isOpen: Boolean, badge: String? = null, badgeColor: Color? = null, onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(
            initialOffsetX = { -100 },
            animationSpec = tween(400, delayMillis = index * 60, easing = EaseOutCubic)
        ) + fadeIn(animationSpec = tween(400, delayMillis = index * 60)),
        exit = fadeOut()
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        
        Box(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(color = Color(0xFF00FFFF).copy(alpha = 0.1f)),
                    onClick = onClick
                )
                .background(if (isSelected) Color(0xFF00FFFF).copy(alpha = 0.08f) else Color.Transparent)
        ) {
            if (isSelected) {
                Box(
                    Modifier
                        .width(3.dp)
                        .fillMaxHeight()
                        .background(Color(0xFF00FFFF))
                        .align(CenterStart)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF00F5FF)
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    color = if (isSelected) Color(0xFF00F5FF) else Color.White.copy(alpha = 0.7f)
                )
                if (badge != null) {
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .border(1.dp, (badgeColor ?: Color(0xFF00FFFF)).copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                            .background((badgeColor ?: Color(0xFF00FFFF)).copy(alpha = 0.1f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            badge,
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace,
                            color = (badgeColor ?: Color(0xFF00FFFF))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawerSectionDivider(title: String) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
        Text(
            title.uppercase(),
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = Color(0xFF00FFFF).copy(alpha = 0.5f),
            letterSpacing = 0.2.em
        )
        Spacer(Modifier.height(4.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF00FFFF).copy(alpha = 0.2f), Color.Transparent)
                    )
                )
        )
    }
}

@Composable
private fun DrawerFooter(onNavigate: (String) -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "terminal")
    val cursorAlpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse),
        label = "cursor"
    )

    Column(Modifier.fillMaxWidth().padding(20.dp)) {
        // SYSTEM DIAGNOSTICS
        Text(
            "SYSTEM DIAGNOSTICS",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = Color(0xFF00FFFF).copy(alpha = 0.5f),
            letterSpacing = 0.2.em
        )
        Spacer(Modifier.height(8.dp))
        
        DiagnosticRow("TFLite ENGINE", "v2.16.1", cursorAlpha)
        DiagnosticRow("HUD UPTIME", "14:22:05", cursorAlpha)
        DiagnosticRow("CLOUD SYNC", "NOMINAL", cursorAlpha)
        DiagnosticRow("AI CORE", "ACTIVE", cursorAlpha)

        Spacer(Modifier.height(20.dp))

        // THEME TOGGLE
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "VISUAL MODE",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFF00FFFF).copy(alpha = 0.5f),
                letterSpacing = 0.2.em,
                modifier = Modifier.weight(1f)
            )
            
            ThemeDot(Color(0xFF00FFFF), isSelected = true)
            Spacer(Modifier.width(12.dp))
            ThemeDot(Color(0xFFFF6600), isSelected = false)
        }

        Spacer(Modifier.height(20.dp))
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FooterChip(Icons.Outlined.Settings, "CONFIG") { onNavigate(Routes.SETTINGS) }
            FooterChip(Icons.Outlined.Share, "DEPLOY") { /* Intent */ }
        }
    }
}

@Composable
private fun DiagnosticRow(label: String, value: String, cursorAlpha: Float) {
    Row(Modifier.fillMaxWidth().padding(vertical = 1.dp)) {
        Text(
            "$label:",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.White.copy(alpha = 0.4f)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            value,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = Color(0xFF00FF88)
        )
        Text(
            "█",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = Color(0xFF00FF88).copy(alpha = cursorAlpha)
        )
    }
}

@Composable
private fun ThemeDot(color: Color, isSelected: Boolean) {
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (clicked) 1.3f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessLow),
        label = "dotScale",
        finishedListener = { _ -> clicked = false }
    )

    Box(
        Modifier
            .size(24.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { clicked = true }
            .border(
                width = 1.dp,
                color = if (isSelected) color else Color.White.copy(alpha = 0.2f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.size(12.dp).clip(CircleShape).background(color))
    }
}

@Composable
private fun RowScope.FooterChip(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        Modifier.weight(1f).border(1.dp, Color(0xFF00F5FF).copy(0.15f), RoundedCornerShape(8.dp))
            .clickable { onClick() }.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Icon(icon, null, Modifier.size(12.dp), tint = Color(0xFF00F5FF).copy(0.7f))
        Spacer(Modifier.width(4.dp))
        Text(label, fontSize = 8.sp, fontFamily = FontFamily.Monospace, color = Color(0xFF00F5FF).copy(0.7f))
    }
}

private fun hexagonPath(center: Offset, radius: Float): Path {
    return Path().apply {
        for (i in 0 until 6) {
            val angle = Math.toRadians((60 * i).toDouble())
            val x = center.x + radius * cos(angle).toFloat()
            val y = center.y + radius * sin(angle).toFloat()
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }
}
