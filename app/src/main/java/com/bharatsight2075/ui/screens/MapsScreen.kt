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
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.HeroStatsRow
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.maps.*
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.viewmodel.IndiaMapViewModel
import com.bharatsight2075.viewmodel.MapStyle

@Composable
fun MapsScreen(
    navController: NavController,
    viewModel: IndiaMapViewModel = hiltViewModel()
) {
    val selectedStyle by viewModel.selectedStyle.collectAsStateWithLifecycle()
    val selectedState by viewModel.selectedState.collectAsStateWithLifecycle()
    val states by viewModel.states.collectAsStateWithLifecycle()
    val surfaceColor = SciFiTheme.colors.surface
    val onSurfaceColor = SciFiTheme.colors.onSurface

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "India Economic Maps",
                    badge = "30 STYLES",
                    badgeColor = Color(0xFFFFD600),
                    onBackClick = { navController.popBackStack() }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        val uiState by viewModel.data.collectAsStateWithLifecycle()
        Column(Modifier.fillMaxSize().padding(padding)) {
            HeroStatsRow(
                chartId = "maps_hero",
                navController = navController,
                stats = uiState.heroStats
            )
            // SECTION A — MAP STYLE SELECTOR
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(MapStyle.entries) { style ->
                    val selected = selectedStyle == style
                    Column(
                        Modifier
                            .width(90.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selected) Color(style.accentColor).copy(0.15f) else surfaceColor)
                            .border(1.dp, if (selected) Color(style.accentColor) else Color(style.accentColor).copy(0.3f), RoundedCornerShape(10.dp))
                            .clickable { viewModel.selectStyle(style) }
                            .padding(8.dp),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Box(
                            Modifier.size(32.dp).clip(CircleShape).background(Color(style.accentColor).copy(0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.Map, null, Modifier.size(16.dp), tint = Color(style.accentColor))
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = style.title,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = SemiBold,
                            color = if (selected) Color(style.accentColor) else onSurfaceColor.copy(0.6f),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }

            // SECTION B — MAIN MAP CANVAS
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Color(selectedStyle.accentColor).copy(0.4f), RoundedCornerShape(16.dp))
                    .background(Color(0xFF0A0A18))
            ) {
                var mapScale by remember { mutableFloatStateOf(1f) }
                var mapOffset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }

                Box(Modifier.fillMaxSize().graphicsLayer {
                    scaleX = mapScale; scaleY = mapScale; translationX = mapOffset.x; translationY = mapOffset.y
                }) {
                    val mapVm: IndiaMapViewModel = hiltViewModel()
                    NeonIndiaMap(
                        viewModel     = mapVm,
                        modifier      = Modifier.fillMaxSize(),
                        borderColor   = Color(selectedStyle.accentColor),
                        highlightedState = selectedState,
                        showCities  = true,
                        onStateClick     = { viewModel.selectState(it) }
                    )
                }

                CompassRose(Modifier.align(TopEnd).padding(12.dp).size(48.dp))
                MapScaleBar(scale = mapScale, Modifier.align(BottomStart).padding(12.dp))
                
                androidx.compose.animation.AnimatedVisibility(
                    visible = selectedState != null,
                    enter = slideInVertically { it } + fadeIn(),
                    exit = slideOutVertically { it } + fadeOut(),
                    modifier = Modifier.align(BottomCenter).padding(12.dp)
                ) {
                    StateInfoCard(selectedState ?: "", selectedStyle.title, onClose = { viewModel.selectState(null) })
                }
                
                MapLegend(style = selectedStyle.title, Modifier.align(TopStart).padding(12.dp))
                
                Box(
                    Modifier
                        .align(TopCenter)
                        .padding(top = 8.dp)
                        .border(1.dp, Color(selectedStyle.accentColor).copy(0.5f), RoundedCornerShape(20.dp))
                        .background(Color(0xFF0A0A18).copy(0.9f))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(text = selectedStyle.title, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = SemiBold, color = Color(selectedStyle.accentColor), letterSpacing = 0.1.sp)
                }
            }

            // SECTION C — STATE/DISTRICT DATA STRIP
            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(IndiaMapMockData.getTopStates(selectedStyle)) { (state, value, rank) ->
                    MiniStateCard(rank, state, value, Color(selectedStyle.accentColor))
                }
            }

            // SECTION D — DISTRICT-LEVEL DRILL
            AnimatedVisibility(visible = selectedState != null) {
                Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Text("DISTRICT BREAKDOWN · ${selectedState?.uppercase()}", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    LazyColumn(Modifier.heightIn(max = 200.dp)) {
                        items(IndiaMapMockData.getDistrictsForState(selectedState ?: "", selectedStyle)) { (district, value) ->
                            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(district, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = onSurfaceColor.copy(0.7f), modifier = Modifier.weight(1f))
                                Box(Modifier.width(80.dp).height(4.dp).clip(RoundedCornerShape(2.dp)).background(onSurfaceColor.copy(0.1f))) {
                                    Box(Modifier.fillMaxHeight().fillMaxWidth(value).background(Color(selectedStyle.accentColor)))
                                }
                                Spacer(Modifier.width(8.dp))
                                Text("${(value * 100).toInt()}%", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = Color(selectedStyle.accentColor))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MiniStateCard(rank: Int, name: String, value: Float, color: Color) {
    Box(
        Modifier
            .width(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(0.05f))
            .border(1.dp, color.copy(0.2f), RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(16.dp).clip(CircleShape).background(color), contentAlignment = Alignment.Center) {
                    Text(rank.toString(), fontSize = 8.sp, color = Color.Black, fontWeight = Bold)
                }
                Spacer(Modifier.width(6.dp))
                Text(name, fontSize = 10.sp, fontWeight = Bold, color = Color.White, maxLines = 1)
            }
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(progress = { value }, color = color, trackColor = color.copy(0.1f), modifier = Modifier.fillMaxWidth().height(2.dp))
        }
    }
}
