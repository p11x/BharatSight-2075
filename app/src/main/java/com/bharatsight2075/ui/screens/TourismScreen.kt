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
class TourismViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class TourismData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(TourismData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = TourismData(heroStats = listOf("Foreign" to "9.2M", "Domestic" to "2.5B", "Forex" to "$28B"))
    }}
}

@Composable
fun TourismScreen(navController: NavController) {
    val vm: TourismViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFFD54F)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Tourism & Hospitality",
                    badge = "INCREDIBLE IN",
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
            item { HeroStatsRow(chartId = "tourism_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "tourism_visa_liquid", navController, "Visa Approval Rate") {
                    LiquidFillGauge(percent = 0.88f, primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "tourism_pilgrimage_spiral", navController, "Pilgrimage Hub Growth") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "tourism_state_radar", navController, "State Readiness", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                    DashCard(chartId = "tourism_hotel_candle", navController, "Hotel Occupancy (OHLC)", modifier = Modifier.weight(1f)) {
                        CandlestickChart(data = emptyList())
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
