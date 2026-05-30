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
class SpaceTechViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class SpaceData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(SpaceData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = SpaceData(heroStats = listOf("Launches" to "48/yr", "Startups" to "180+", "Budget" to "₹13K Cr"))
    }}
}

@Composable
fun SpaceTechScreen(navController: NavController) {
    val vm: SpaceTechViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF9C27B0)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Space & Deep Tech",
                    badge = "ISRO",
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
            item { HeroStatsRow(chartId = "space_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "space_launch_orbital", navController, "Launch Vehicle Payload Orbit") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item {
                DashCard(chartId = "space_mission_spiral", navController, "ISRO Mission Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "space_launch_race", navController, "Global Launch Race", modifier = Modifier.weight(1f)) {
                        GradientBarChart(data = emptyList(), labels = emptyList())
                    }
                    DashCard(chartId = "space_satellite_bonds", navController, "Communication Satellites", modifier = Modifier.weight(1f)) {
                        // Using Area chart as fallback
                        GradientAreaChart(data = emptyList())
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
