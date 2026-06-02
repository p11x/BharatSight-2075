package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bharatsight2075.data.repositories.EconomicRepository
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClimateViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class ClimateData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(ClimateData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = ClimateData(heroStats = MockData.climateHeroStats)
    }}
}

@Composable
fun ClimateScreen(navController: NavController) {
    val vm: ClimateViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF00E676)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Climate & Green",
                    badge = "NET ZERO",
                    badgeColor = primaryColor,
                    onBackClick = { navController.popBackStack() }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { HeroStatsRow(chartId = "climate_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "climate_budget_liquid", navController, "Carbon Budget Remaining") {
                    LiquidFillGauge(percent = 0.45f, primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "climate_risk_radar", navController, "Climate Risk", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                    DashCard(chartId = "climate_forest_hex", navController, "Forest Cover", modifier = Modifier.weight(1f)) {
                        // HexMap fallback
                        HeatmapGridChart(data = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "climate_temp_spiral", navController, "Temp Anomaly Spiral") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = Color(0xFFFF6B35))
                }
            }

            item {
                DashCard(chartId = "climate_sink_orbital", navController, "Carbon Sink Capacity") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
