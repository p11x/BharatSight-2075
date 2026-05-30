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
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmartCitiesViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class SmartCitiesData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(SmartCitiesData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = SmartCitiesData(heroStats = MockData.smartCitiesHeroStats)
    }}
}

@Composable
fun SmartCitiesScreen(navController: NavController) {
    val vm: SmartCitiesViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF4FC3F7)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Infrastructure & Cities",
                    badge = "100 CITIES",
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
            item { HeroStatsRow(chartId = "smart_cities_hero", navController, stats = data.heroStats) }

            item {
                TwoColumnRow {
                    DashCard(chartId = "cities_housing_liquid", navController, "Housing Deficit", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.35f, primaryColor = Color(0xFFFF6B35))
                    }
                    DashCard(chartId = "cities_infra_radar", navController, "Infra Quality", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "cities_land_use", navController, "Urban Land Allocation") {
                    TreemapChart(weights = emptyList(), brushes = emptyList())
                }
            }

            item {
                DashCard(chartId = "cities_port_orbital", navController, "Major Port Throughput") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item {
                DashCard(chartId = "cities_capex_waterfall", navController, "NIP Project Spend") {
                    WaterfallBarChart(data = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
