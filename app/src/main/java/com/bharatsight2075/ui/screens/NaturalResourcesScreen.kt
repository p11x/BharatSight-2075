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
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaturalResourcesViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class ResourcesData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(ResourcesData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = ResourcesData(heroStats = listOf("Water Stress" to "High", "Minerals" to "95 types", "Fisheries" to "$7B"))
    }}
}

@Composable
fun NaturalResourcesScreen(navController: NavController) {
    val vm: NaturalResourcesViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF4DD0E1)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Natural Resources",
                    badge = "CRZ",
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
            item { HeroStatsRow(chartId = "resources_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "resources_water_liquid", navController, "Water Table Level Index") {
                    LiquidFillGauge(percent = 0.32f, primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "resources_groundwater_spiral", navController, "Groundwater Depletion Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "resources_fisheries_orbital", navController, "Fisheries Sectors", modifier = Modifier.weight(1f)) {
                        OrbitalDonutSystem(rings = emptyList())
                    }
                    DashCard(chartId = "resources_curse_radar", navController, "Resource Curse Risk", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "resources_pollution_mirror", navController, "Pollution Load Balance") {
                    MirrorBarChart(data = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
