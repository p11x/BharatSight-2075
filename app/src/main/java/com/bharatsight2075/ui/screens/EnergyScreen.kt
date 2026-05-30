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
class EnergyViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class EnergyData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(EnergyData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = EnergyData(heroStats = MockData.energyHeroStats)
    }}
}

@Composable
fun EnergyScreen(navController: NavController) {
    val vm: EnergyViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFFD600)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Energy & Power",
                    badge = "LIVE GW",
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
            item { HeroStatsRow(chartId = "energy_hero", navController, stats = data.heroStats) }

            item {
                TwoColumnRow {
                    DashCard(chartId = "energy_grid_wave", navController, "Live Grid Load", modifier = Modifier.weight(1f)) {
                        WaveformChart(brush = Brush.verticalGradient(listOf(primaryColor, Color.Transparent)))
                    }
                    DashCard(chartId = "energy_ev_liquid", navController, "EV Adoption Rate", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.46f, primaryColor = Color(0xFF00E676))
                    }
                }
            }

            item {
                DashCard(chartId = "energy_mix_orbital", navController, "Renewable Mix (GW)") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item {
                DashCard(chartId = "energy_source_stacked", navController, "Coal vs Renewable Forecast") {
                    StackedAreaChart(data = emptyList(), brushes = emptyList())
                }
            }

            item {
                DashCard(chartId = "energy_carbon_spiral", navController, "Carbon Intensity (1947-2075)") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
