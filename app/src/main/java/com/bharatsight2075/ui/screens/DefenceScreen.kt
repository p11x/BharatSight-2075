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
class DefenceViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class DefenceData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(DefenceData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = DefenceData(heroStats = MockData.defenceHeroStats)
    }}
}

@Composable
fun DefenceScreen(navController: NavController) {
    val vm: DefenceViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFF5252)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Defence & Strategic",
                    badge = "CLASSIFIED",
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
            item { HeroStatsRow(chartId = "defence_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "defence_strategic_reserve", navController, "Strategic Reserve Level") {
                    LiquidFillGauge(percent = 0.85f, primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "defence_budget_orbital", navController, "Budget Allocation Rings") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "defence_modernization_funnel", navController, "Modernization Funnel", modifier = Modifier.weight(1f)) {
                        // Using BarChart as Funnel fallback
                        GradientBarChart(data = listOf(100f, 80f, 60f, 40f), labels = emptyList())
                    }
                    DashCard(chartId = "defence_capability_radar", navController, "Force Multiplier", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "defence_conflict_spiral", navController, "Strategic Conflict Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
