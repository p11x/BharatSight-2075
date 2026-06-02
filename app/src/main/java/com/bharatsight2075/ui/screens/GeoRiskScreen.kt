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
class GeoRiskViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class GeoRiskData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(GeoRiskData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = GeoRiskData(heroStats = listOf("Risk Score" to "42", "Borders" to "7 nations", "Conflicts" to "3 active"))
    }}
}

@Composable
fun GeoRiskScreen(navController: NavController) {
    val vm: GeoRiskViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFF1744)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Geopolitical Risk",
                    badge = "LIVE ALERT",
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
            item { HeroStatsRow(chartId = "georisk_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "georisk_asset_liquid", navController, "Strategic Asset Liquid Index") {
                    LiquidFillGauge(percent = 0.42f, primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "georisk_conflict_spiral", navController, "Geopolitical Conflict Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "georisk_dependency_mirror", navController, "Trade Dependency Balance") {
                    MirrorBarChart(data = emptyList())
                }
            }

            item {
                DashCard(chartId = "georisk_risk_heatmap", navController, "Country Risk Heatmap") {
                    HeatmapGridChart(data = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
