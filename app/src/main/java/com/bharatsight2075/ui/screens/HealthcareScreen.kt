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
class HealthcareViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class HealthcareData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(HealthcareData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = HealthcareData(heroStats = MockData.healthcareHeroStats)
    }}
}

@Composable
fun HealthcareScreen(navController: NavController) {
    val vm: HealthcareViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFF06292)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Healthcare & Pharma",
                    badge = "PHARMA HUB",
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
            item { HeroStatsRow(chartId = "health_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "health_spend_liquid", navController, "Public Health Spend") {
                    LiquidFillGauge(percent = 0.52f, primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "health_life_spiral", navController, "Life Expectancy (1947-2075)") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "health_outcome_radar", navController, "Health Outcomes", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                    DashCard(chartId = "health_insurance_orbital", navController, "Insurance Coverage", modifier = Modifier.weight(1f)) {
                        OrbitalDonutSystem(rings = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "health_telemedicine_wave", navController, "Tele-consultation Wave") {
                    WaveformChart(brush = Brush.verticalGradient(listOf(primaryColor, Color.Transparent)))
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
