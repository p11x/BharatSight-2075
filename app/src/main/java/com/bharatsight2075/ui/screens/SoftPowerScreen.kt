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
class SoftPowerViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class SoftPowerData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(SoftPowerData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = SoftPowerData(heroStats = listOf("Brand Value" to "$2.6T", "Diaspora" to "32M", "Yoga" to "300M"))
    }}
}

@Composable
fun SoftPowerScreen(navController: NavController) {
    val vm: SoftPowerViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFFD700)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Global Soft Power",
                    badge = "BRAND INDIA",
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
            item { HeroStatsRow(chartId = "softpower_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "softpower_index_orbital", navController, "Soft Power Index Metrics") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item {
                DashCard(chartId = "softpower_sanskrit_spiral", navController, "Linguistic Influence Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "softpower_perception_radar", navController, "Global Perception", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                    DashCard(chartId = "softpower_cricket_pulse", navController, "Cricket Economy Pulse", modifier = Modifier.weight(1f)) {
                        // PulseRadar fallback
                        LiquidFillGauge(percent = 0.95f, primaryColor = primaryColor)
                    }
                }
            }

            item {
                DashCard(chartId = "softpower_brand_mirror", navController, "Nation Brand vs Peers") {
                    MirrorBarChart(data = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
