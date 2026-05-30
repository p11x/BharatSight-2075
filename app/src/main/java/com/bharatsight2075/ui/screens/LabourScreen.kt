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
class LabourViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class LabourData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(LabourData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = LabourData(heroStats = listOf("LFP Rate" to "42.6%", "Formal" to "21%", "Wage Growth" to "7.2%"))
    }}
}

@Composable
fun LabourScreen(navController: NavController) {
    val vm: LabourViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF80CBC4)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Labour Market",
                    badge = "CMIE",
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
            item { HeroStatsRow(chartId = "labour_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "labour_sector_race", navController, "Employment by Sector (Race)") {
                    GradientBarChart(data = emptyList(), labels = emptyList())
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "labour_remote_liquid", navController, "Remote Work Index", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.35f, primaryColor = primaryColor)
                    }
                    DashCard(chartId = "labour_quality_radar", navController, "Job Quality Index", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "labour_youth_unemployment_spiral", navController, "Youth Unemployment Trend") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "labour_gender_mirror", navController, "Gender LFP Divide") {
                    MirrorBarChart(data = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
