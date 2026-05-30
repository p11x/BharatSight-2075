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
class DigitalEconomyViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class DigitalData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(DigitalData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = DigitalData(heroStats = MockData.digitalHeroStats)
    }}
}

@Composable
fun DigitalEconomyScreen(navController: NavController) {
    val vm: DigitalEconomyViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF00F5FF)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Digital & Fintech",
                    badge = "UPI ₹",
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
            item { HeroStatsRow(chartId = "digital_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "digital_upi_wave", navController, "Real-time UPI Volume") {
                    WaveformChart(brush = Brush.verticalGradient(listOf(primaryColor, Color.Transparent)))
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "digital_consumption_liquid", navController, "Data Consumption", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.92f, primaryColor = primaryColor)
                    }
                    DashCard(chartId = "digital_security_radar", navController, "Cybersecurity Radar", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "digital_locker_spiral", navController, "India Stack Adoption") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "digital_cbdc_orbital", navController, "Digital Rupee Use Cases") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
