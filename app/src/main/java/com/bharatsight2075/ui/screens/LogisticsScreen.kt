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
class LogisticsViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class LogisticsData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(LogisticsData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = LogisticsData(heroStats = listOf("LPI" to "3.44", "Cold Chain" to "₹2,100C", "Port TEU" to "12.3M"))
    }}
}

@Composable
fun LogisticsScreen(navController: NavController) {
    val vm: LogisticsViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFFB74D)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Supply Chain & Logistics",
                    badge = "LOGI",
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
            item { HeroStatsRow(chartId = "logistics_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "logistics_port_candle", navController, "Port Traffic Vol (OHLCV)") {
                    CandlestickChart(data = emptyList())
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "logistics_cold_liquid", navController, "Cold Chain Coverage", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.45f, primaryColor = primaryColor)
                    }
                    DashCard(chartId = "logistics_cost_mirror", navController, "Logistics Cost vs Peers", modifier = Modifier.weight(1f)) {
                        MirrorBarChart(data = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "logistics_customs_spiral", navController, "Customs Dwell Time Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "logistics_modal_split", navController, "Modal Split (Rail/Road/Sea)") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
