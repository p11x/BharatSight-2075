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
class StartupViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class StartupData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(StartupData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = StartupData(heroStats = MockData.startupHeroStats)
    }}
}

@Composable
fun StartupScreen(navController: NavController) {
    val vm: StartupViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF7C4DFF)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Startup & Innovation",
                    badge = "UNICORNS",
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
            item { HeroStatsRow(chartId = "startup_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "startup_funding_race", navController, "Startup Funding by Sector") {
                    // Using BarChart as Race fallback for now
                    GradientBarChart(data = emptyList(), labels = emptyList())
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "startup_exit_waterfall", navController, "Exits Waterfall", modifier = Modifier.weight(1f)) {
                        WaterfallBarChart(data = emptyList())
                    }
                    DashCard(chartId = "startup_innovation_radar", navController, "Innovation Radar", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "startup_patent_spiral", navController, "Patent Filing Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "startup_founder_venn", navController, "Founder Demographics") {
                    VennDiagramChart()
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
