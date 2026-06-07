package com.bharatsight2075.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightLogo
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import kotlinx.coroutines.delay

import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.sqrt

@Composable
fun HomeScreen(
    navController: NavController,
    onNavigate: (String) -> Unit,
    onMenuClick: () -> Unit,
    onSettingsClick: () -> Unit,
    userName: String = "PAVAN"
) {
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Home(
                    userName = userName,
                    notificationCount = 3,
                    onMenuClick = onMenuClick,
                    onNotificationsClick = { /* Handle */ },
                    onSettingsClick = onSettingsClick
                )
            )
        },
        containerColor = Color(0xFF0A0A1A)
    ) { padding ->
        Column(Modifier.padding(padding)) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item { HomeHeroHeader(navController) }
                item { LiveMarketTicker() }

                item { SectionGroupHeader("◈ CORE ECONOMIC INTELLIGENCE") }
                
                var cardIdx = 0
                
                item {
                    SectionCard(Routes.MACRO_OVERVIEW, "Macro Overview", Icons.Outlined.Dashboard, Color(0xFF00F5FF), badge="LIVE", metrics=listOf("GDP 2075" to "$37T", "Inflation" to "4.2%", "Growth" to "8.1%"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.FORECASTER, "Forecaster 2075", Icons.Outlined.TrendingUp, Color(0xFFFF6B35), badge="AI", metrics=listOf("Scenarios" to "4", "Horizon" to "50yr", "Model" to "TFLite"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.INDIA_GLOBE, "India Economic Map", Icons.Outlined.Public, Color(0xFF00E676), metrics=listOf("States" to "36", "Trade Arcs" to "142", "FDI" to "Live"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.MACRO_HUB, "Macro Indicator Hub", Icons.Outlined.Radar, Color(0xFFFFD600), badge="NEW", metrics=listOf("Indicators" to "12", "Alerts" to "3"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.STATE_DIVE, "State Deep Dive", Icons.Outlined.Map, Color(0xFFB39DDB), metrics=listOf("States" to "36", "UTs" to "8", "Years" to "2000-35"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.STOCK_HEATMAP, "Nifty 500 Heatmap", Icons.Outlined.BarChart, Color(0xFF4FC3F7), badge="LIVE", metrics=listOf("Stocks" to "500", "Sectors" to "12"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.TRADE_NETWORK, "Global Trade Network", Icons.Outlined.AccountTree, Color(0xFFFF6B35), metrics=listOf("Partners" to "30", "Vol" to "$1.2T"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.SECTOR_DIVE, "Sector Analysis", Icons.Outlined.DonutSmall, Color(0xFF00F5FF), metrics=listOf("Sectors" to "8", "KPIs" to "850+"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.DEMOGRAPHICS, "Demographics 2075", Icons.Outlined.People, Color(0xFFF06292), metrics=listOf("Population" to "1.4B", "HDI" to "0.644"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.MAPS, "India Economic Maps", Icons.Outlined.Layers, Color(0xFFFFD600), metrics=listOf("Styles" to "30", "Districts" to "766"), cardIndex = cardIdx++, navController = navController)
                }

                item { SectionGroupHeader("◈ SECTORS & INDUSTRIES") }
                
                item {
                    SectionCard(Routes.AGRICULTURE, "Agriculture & Food", Icons.Outlined.Grass, Color(0xFF76C442), metrics=listOf("Crops" to "23", "MSP" to "₹2183"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.BANKING, "Banking & Finance", Icons.Outlined.AccountBalance, Color(0xFF00B0FF), metrics=listOf("Banks" to "156", "NPA" to "3.9%"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.ENERGY, "Energy & Power", Icons.Outlined.Bolt, Color(0xFFFFD600), metrics=listOf("Capacity" to "950GW", "RE" to "46%"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.SMART_CITIES, "Smart Cities & Infra", Icons.Outlined.LocationCity, Color(0xFF4FC3F7), metrics=listOf("Cities" to "100", "NH km" to "1.46L"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.STARTUPS, "Startup Ecosystem", Icons.Outlined.RocketLaunch, Color(0xFF7C4DFF), badge="🦄", metrics=listOf("Unicorns" to "113", "Funding" to "$42B"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.DEFENCE, "Defence Economy", Icons.Outlined.Security, Color(0xFFFF5252), metrics=listOf("Budget" to "₹6.2L Cr", "Export" to "$2.5B"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.CLIMATE, "Climate & Green Economy", Icons.Outlined.EnergySavingsLeaf, Color(0xFF00E676), metrics=listOf("CO₂" to "2.8GT", "NDC" to "45%"), cardIndex = cardIdx++, navController = navController)
                }

                item { SectionGroupHeader("◈ ECONOMY & SOCIETY") }
                
                item {
                    SectionCard(Routes.DIGITAL_ECONOMY, "Digital Economy", Icons.Outlined.PhoneAndroid, Color(0xFF00F5FF), metrics=listOf("UPI Txn" to "14B/mo", "Internet" to "850M"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.EDUCATION, "Education & Skills", Icons.Outlined.School, Color(0xFFFFC107), metrics=listOf("GER" to "28.4%", "Skilled" to "51M/yr"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.HEALTHCARE, "Healthcare & Pharma", Icons.Outlined.HealthAndSafety, Color(0xFFF06292), metrics=listOf("Pharma" to "$50B", "ABDM" to "540M"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.REAL_ESTATE, "Real Estate", Icons.Outlined.Home, Color(0xFFFF8A65), metrics=listOf("Index" to "₹7,200/sqft", "Units" to "4.35L"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.TOURISM, "Tourism", Icons.Outlined.AirplaneTicket, Color(0xFFFFD54F), metrics=listOf("Foreign" to "9.2M", "Forex" to "$28B"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.INEQUALITY, "Wealth & Inequality", Icons.Outlined.Balance, Color(0xFFE040FB), metrics=listOf("Gini" to "0.357", "Top 1%" to "22%"), cardIndex = cardIdx++, navController = navController)
                }

                item { SectionGroupHeader("◈ MARKETS & ANALYTICS") }
                
                item {
                    SectionCard(Routes.LABOUR, "Labour Market", Icons.Outlined.Work, Color(0xFF80CBC4), metrics=listOf("LFPR" to "42.6%", "Formal" to "21%"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.LOGISTICS, "Logistics & Supply Chain", Icons.Outlined.LocalShipping, Color(0xFFFFB74D), metrics=listOf("LPI" to "3.44/5", "TEU" to "12.3M"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.MEDIA, "Media & Entertainment", Icons.Outlined.Movie, Color(0xFFFF4081), metrics=listOf("OTT Subs" to "550M", "Box Office" to "₹12K Cr"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.NATURAL_RESOURCES, "Natural Resources", Icons.Outlined.Water, Color(0xFF4DD0E1), metrics=listOf("Minerals" to "95 types", "Fisheries" to "$7B"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.SPACE_TECH, "Space & Deep Tech", Icons.Outlined.RocketLaunch, Color(0xFF9C27B0), metrics=listOf("Launches" to "48/yr", "Startups" to "180+"), cardIndex = cardIdx++, navController = navController)
                }
                item {
                    SectionCard(Routes.GEO_RISK, "Geopolitical Risk", Icons.Outlined.Warning, Color(0xFFFF1744), badge="ALERT", metrics=listOf("Risk" to "42/100"), cardIndex = cardIdx++, navController = navController)
                }

                item { SectionGroupHeader("◈ DEEP ANALYTICS") }
                
                item {
                    SectionCard(Routes.SOFT_POWER, "India's Soft Power", Icons.Outlined.Language, Color(0xFFFFD700), metrics=listOf("Brand" to "$2.6T", "Diaspora" to "32M"), cardIndex = cardIdx++, navController = navController)
                }

                item {
                    Box(Modifier.fillMaxWidth().height(80.dp), contentAlignment=Alignment.Center) {
                        Text("BharatSight 2075 · 31 Sections · India Economic Intelligence", 
                            fontSize=8.sp, fontFamily=FontFamily.Monospace, color=Color(0xFF00F5FF).copy(0.2f))
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeroHeader(navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition(label = "HeroInfinite")
    val driftOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(50000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "HexDrift"
    )

    Box(Modifier.fillMaxWidth().height(180.dp)) {
        // Layered background with Hexagon Grid
        Box(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF080814))
        ) {
            Canvas(Modifier.fillMaxSize()) {
                val hexSize = 30.dp.toPx()
                val hexWidth = hexSize * sqrt(3f)
                val hexHeight = hexSize * 2f
                val hSpacing = hexWidth
                val vSpacing = hexHeight * 0.75f

                val cols = (size.width / hSpacing).toInt() + 2
                val rows = (size.height / vSpacing).toInt() + 2

                for (r in 0 until rows) {
                    for (c in 0 until cols) {
                        val x = c * hSpacing + (if (r % 2 == 1) hSpacing / 2f else 0f) + (driftOffset % hSpacing)
                        val y = r * vSpacing + (driftOffset % vSpacing)
                        
                        val path = Path().apply {
                            for (i in 0 until 6) {
                                val angle = 60f * i - 30f
                                val px = x + hexSize * androidx.compose.ui.util.lerp(
                                    0.9f, 1f, 0.5f // Simplified for now
                                ) * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
                                val py = y + hexSize * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
                                if (i == 0) moveTo(px, py) else lineTo(px, py)
                            }
                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color(0xFF00FFFF).copy(alpha = 0.03f),
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }
                }
            }
            
            // Border gradient
            Box(
                Modifier
                    .fillMaxSize()
                    .border(
                        1.dp,
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF00F5FF).copy(0.5f),
                                Color(0xFFFF6B35).copy(0.3f),
                                Color(0xFF00F5FF).copy(0.1f)
                            )
                        ),
                        RoundedCornerShape(20.dp)
                    )
            )
        }

    Column(Modifier.padding(20.dp).fillMaxSize()) {
      // TOP ROW: Logo + App name
      Row(verticalAlignment=Alignment.CenterVertically, horizontalArrangement=Arrangement.spacedBy(12.dp)) {
        // Hexagonal logo
        BharatSightLogo(size=44.dp, animated=true)
        Column {
          Text("BHARATSIGHT", fontSize=18.sp, fontFamily=FontFamily.Monospace,
            fontWeight=FontWeight.ExtraBold,
            style=LocalTextStyle.current.copy(brush=Brush.horizontalGradient(
              listOf(Color(0xFF00F5FF), Color(0xFF00F5FF), Color(0xFFFF6B35)))))
          Text("INDIA ECONOMIC INTELLIGENCE PLATFORM", fontSize=8.sp,
            fontFamily=FontFamily.Monospace, color=Color(0xFF00F5FF).copy(0.5f),
            letterSpacing=0.12.em)
        }
        Spacer(Modifier.weight(1f))
        // LIVE badge pulsing
        val livePulse by rememberInfiniteTransition(label="live")
          .animateFloat(0.5f,1f,infiniteRepeatable(tween(800,easing=EaseInOutSine),RepeatMode.Reverse),label="lp")
        Box(Modifier.border(1.dp, Color(0xFF00E676).copy(0.7f), RoundedCornerShape(4.dp))
          .background(Color(0xFF00E676).copy(0.1f)).padding(horizontal=8.dp,vertical=3.dp)) {
          Row(verticalAlignment=Alignment.CenterVertically, horizontalArrangement=Arrangement.spacedBy(4.dp)) {
            Box(Modifier.size(5.dp).alpha(livePulse).clip(CircleShape).background(Color(0xFF00E676)))
            Text("LIVE", fontSize=8.sp, fontFamily=FontFamily.Monospace,
              fontWeight=FontWeight.SemiBold, color=Color(0xFF00E676))
          }
        }
      }

      Spacer(Modifier.height(14.dp))

      // MIDDLE: GDP number + year badge
      Row(verticalAlignment=Alignment.Bottom, horizontalArrangement=Arrangement.spacedBy(12.dp)) {
        Column {
          Text("ESTIMATED 2075 GDP", fontSize=8.sp, fontFamily=FontFamily.Monospace,
            color=Color(0xFF00F5FF).copy(0.5f), letterSpacing=0.1.em)
          // Animated counting number
          var counted by remember{mutableStateOf(false)}
          val countVal by animateFloatAsState(if(counted) 37f else 0f, tween(2000,easing=EaseOutCubic), label = "count")
          LaunchedEffect(Unit){counted=true}
          Text("$${"%.0f".format(countVal)}T USD", fontSize=26.sp,
            fontFamily=FontFamily.Monospace, fontWeight=FontWeight.Bold,
            color=Color(0xFF00F5FF))
        }
        Spacer(Modifier.weight(1f))
        // YoY badge
        Box(Modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFF00E676).copy(0.12f))
          .border(1.dp, Color(0xFF00E676).copy(0.4f), RoundedCornerShape(8.dp)).padding(horizontal=10.dp,vertical=6.dp)) {
          Column(horizontalAlignment=Alignment.CenterHorizontally) {
            Text("▲ 8.1%", fontSize=13.sp, fontFamily=FontFamily.Monospace,
              fontWeight=FontWeight.Bold, color=Color(0xFF00E676))
            Text("YoY GROWTH", fontSize=7.sp, fontFamily=FontFamily.Monospace,
              color=Color(0xFF00E676).copy(0.6f))
          }
        }
      }

      Spacer(Modifier.height(14.dp))

      // BOTTOM ROW: 3 animated KPI chips
      Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
        listOf(
          Triple("GDP 2075", "$37T", Color(0xFF00F5FF)),
          Triple("SENSEX",  "81,234 ▲", Color(0xFF00E676)),
          Triple("₹ / \$",  "83.2",  Color(0xFFFFD600))
        ).forEach { (label, value, color) ->
          Box(Modifier.weight(1f).clip(RoundedCornerShape(10.dp))
            .background(color.copy(0.06f))
            .border(1.dp, color.copy(0.3f), RoundedCornerShape(10.dp)).padding(horizontal=8.dp,vertical=8.dp)) {
            Column(horizontalAlignment=Alignment.CenterHorizontally) {
              Text(value, fontSize=12.sp, fontFamily=FontFamily.Monospace,
                fontWeight=FontWeight.Bold, color=color)
              Text(label, fontSize=8.sp, fontFamily=FontFamily.Monospace, color=color.copy(0.55f))
            }
          }
        }
      }
    }
  }
}

@Composable
fun SectionCard(
    route: String,
    title: String,
    icon: ImageVector,
    accentColor: Color,
    badge: String? = null,
    metrics: List<Pair<String, String>> = emptyList(),
    subtitle: String = "",
    cardIndex: Int = 0,
    navController: NavController
) {
    // Staggered entry
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600, delayMillis = cardIndex * 100),
        label = "alpha"
    )
    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 40.dp,
        animationSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = 0.7f),
        label = "offset"
    )
    LaunchedEffect(Unit) { visible = true }

    // Press state
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (pressed) 0.97f else 1f, spring(stiffness = 600f), label = "scale")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha)
            .offset(y = offsetY)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF0D1F35).copy(alpha = 0.7f))
            .border(
                1.dp,
                accentColor.copy(alpha = 0.3f),
                RoundedCornerShape(16.dp)
            )
            .drawBehind {
                // Top-edge highlight
                drawLine(
                    color = Color(0xFF00FFFF).copy(alpha = 0.15f),
                    start = Offset(0f, 0.5.dp.toPx()),
                    end = Offset(size.width, 0.5.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .clickable(interactionSource = interactionSource, indication = null) { navController.navigate(route) }
    ) {
    Row(Modifier.padding(16.dp), verticalAlignment=Alignment.CenterVertically) {
      // Left accent bar
      Box(Modifier.width(3.dp).height(48.dp).clip(RoundedCornerShape(2.dp))
        .background(Brush.verticalGradient(listOf(accentColor, accentColor.copy(0.2f)))))
      Spacer(Modifier.width(12.dp))
      Column(Modifier.weight(1f)) {
        // Title + badge
        Row(verticalAlignment=Alignment.CenterVertically, horizontalArrangement=Arrangement.spacedBy(8.dp)) {
          Icon(icon, null, Modifier.size(18.dp), accentColor)
          Text(title, fontSize=13.sp, fontFamily=FontFamily.Monospace,
            fontWeight=FontWeight.SemiBold, color=Color.White)
          if (badge != null) {
            val shimmerTranslate by rememberInfiniteTransition(label = "BadgeShimmer")
                .animateFloat(
                    initialValue = 0f,
                    targetValue = 1000f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "ShimmerTranslate"
                )
                
            Box(
                Modifier
                    .border(1.dp, accentColor.copy(0.6f), RoundedCornerShape(3.dp))
                    .background(accentColor.copy(0.1f))
                    .drawBehind {
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Transparent, Color.White.copy(alpha = 0.2f), Color.Transparent),
                                start = Offset(shimmerTranslate - 200f, 0f),
                                end = Offset(shimmerTranslate, size.height)
                            )
                        )
                    }
                    .padding(horizontal = 5.dp, vertical = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (badge == "LIVE" || badge == "AI" || badge == "🦄") {
                        val badgePulse by rememberInfiniteTransition(label = "BadgePulse")
                            .animateFloat(
                                initialValue = 0.4f,
                                targetValue = 1f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(800, easing = EaseInOutSine),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "Pulse"
                            )
                        Box(
                            Modifier
                                .size(4.dp)
                                .alpha(badgePulse)
                                .clip(CircleShape)
                                .background(accentColor)
                        )
                    }
                    Text(badge, fontSize = 7.5.sp, fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold, color = accentColor)
                }
            }
          }
        }
        if (subtitle.isNotEmpty()) {
            Spacer(Modifier.height(3.dp))
            Text(subtitle, fontSize=11.sp, color=Color.White.copy(0.5f),
                fontFamily=FontFamily.Monospace, maxLines=1, overflow=TextOverflow.Ellipsis)
        }
        if (metrics.isNotEmpty()) {
          Spacer(Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            metrics.forEach { (label, value) ->
              Column {
                val numericValue = value.filter { it.isDigit() || it == '.' }.toFloatOrNull() ?: 0f
                val suffix = value.filter { !it.isDigit() && it != '.' }
                
                var counted by remember { mutableStateOf(false) }
                val animatedValue by animateFloatAsState(
                    targetValue = if (counted) numericValue else 0f,
                    animationSpec = tween(1200, easing = EaseOutCubic),
                    label = "MetricCount"
                )
                LaunchedEffect(Unit) { counted = true }

                Text(
                    text = if (numericValue > 0) "${"%.1f".format(animatedValue)}$suffix" else value,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
                Text(label, fontSize = 8.sp, fontFamily = FontFamily.Monospace, color = Color.White.copy(0.35f))
              }
            }
          }
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment=Alignment.CenterVertically) {
          Text("EXPLORE", fontSize=9.sp, fontFamily=FontFamily.Monospace,
            color=accentColor.copy(0.7f))
          Icon(Icons.AutoMirrored.Filled.ArrowForward, null, Modifier.size(10.dp), accentColor.copy(0.7f))
          Spacer(Modifier.weight(1f))
          Text("Updated 2 min ago", fontSize=8.sp, fontFamily=FontFamily.Monospace,
            color=Color.White.copy(0.25f))
        }
      }
    }
  }
}

@Composable
fun SectionGroupHeader(title: String) {
    var animTrigger by remember { mutableStateOf(false) }
    val underlineProgress by animateFloatAsState(
        targetValue = if (animTrigger) 1f else 0f,
        animationSpec = tween(1000, easing = EaseInOutSine),
        label = "UnderlineAnim"
    )
    LaunchedEffect(Unit) { animTrigger = true }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00F5FF).copy(0.6f))
            )
            Spacer(Modifier.width(8.dp))
            Text(
                title, fontSize = 9.sp, fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold, color = Color(0xFF00F5FF).copy(0.55f), letterSpacing = 0.18.em
            )
        }
        if (title.contains("CORE")) {
            Spacer(Modifier.height(4.dp))
            Box(
                Modifier
                    .fillMaxWidth(underlineProgress)
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF00F5FF), Color.Transparent)
                        )
                    )
            )
        }
    }
}

@Composable
fun LiveMarketTicker() {
  val tickerItems = "SENSEX 81,234 ▲0.80%  ◆  NIFTY 24,567 ▲0.58%  ◆  ₹/$ 83.21 ▼0.10%  ◆  GOLD ₹72,450 ▲0.30%  ◆  CRUDE $78.4 ▼0.40%  ◆  "
  val offset by rememberInfiniteTransition(label="ticker")
    .animateFloat(0f, -2000f, infiniteRepeatable(tween(20000, easing=LinearEasing)), label="off")
  Box(Modifier.fillMaxWidth().height(28.dp)
    .background(Color(0xFF00F5FF).copy(0.04f))
    .drawBehind {
        drawLine(
            color = Color(0xFF00F5FF).copy(0.15f),
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 0.5.dp.toPx()
        )
    }) {
    Row(verticalAlignment=Alignment.CenterVertically,
      modifier=Modifier.offset(x=offset.dp).fillMaxHeight().padding(horizontal=8.dp)) {
      // Prefix: LIVE badge
      Box(Modifier.border(1.dp,Color(0xFF00E676).copy(0.5f),RoundedCornerShape(3.dp))
        .background(Color(0xFF00E676).copy(0.08f)).padding(horizontal=6.dp,vertical=2.dp)) {
        Text("● LIVE", fontSize=7.sp, fontFamily=FontFamily.Monospace,
          fontWeight=FontWeight.Bold, color=Color(0xFF00E676))
      }
      Spacer(Modifier.width(12.dp))
      Text(tickerItems + tickerItems, fontSize=10.sp, fontFamily=FontFamily.Monospace,
        color=Color(0xFF00F5FF).copy(0.75f), maxLines=1)
    }
  }
}
