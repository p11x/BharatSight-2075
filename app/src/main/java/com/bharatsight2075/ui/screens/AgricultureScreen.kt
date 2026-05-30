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
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgricultureViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class AgricultureData(
        val heroStats: List<Pair<String, String>> = emptyList()
    )
    private val _data = MutableStateFlow(AgricultureData())
    val sectionData = _data.asStateFlow()
    
    init {
        viewModelScope.launch(Dispatchers.IO) {
            _data.value = AgricultureData(
                heroStats = MockData.agricultureHeroStats
            )
        }
    }
}

@Composable
fun AgricultureScreen(navController: NavController) {
    val vm: AgricultureViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF76C442)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Agriculture & Food Security",
                    badge = "KHARIF 2025",
                    badgeColor = primaryColor,
                    onBackClick = { navController.popBackStack() }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { HeroStatsRow(chartId = "agri_hero", navController, stats = data.heroStats) }

            item {
                TwoColumnRow {
                    DashCard(chartId = "agri_liquid_fill", navController, "Crop Production Pulse", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.78f, primaryColor = primaryColor)
                    }
                    DashCard(chartId = "agri_inflation_wave", navController, "Food Inflation Wave", modifier = Modifier.weight(1f)) {
                        WaveformChart(brush = GradPalette.ORANGE_PINK)
                    }
                }
            }

            item {
                DashCard(chartId = "agri_msp_trend", navController, "MSP 10-YEAR TREND") {
                    GradientAreaChart(data = emptyList(), strokeColor = Color(0xFFFFD600))
                }
            }

            item {
                DashCard(chartId = "agri_export_polar", navController, "Export Commodities Share") {
                    PolarAreaChart(data = emptyList(), brushes = emptyList())
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "agri_income_mirror", navController, "Farmer Income vs Exp", modifier = Modifier.weight(1f)) {
                        MirrorBarChart(data = emptyList())
                    }
                    DashCard(chartId = "agri_soil_radar", navController, "Soil Health Radar", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "agri_spiral_timeline", navController, "Agri GDP Timeline (1947-2075)") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "agri_orbital_insurance", navController, "Crop Insurance Coverage") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
