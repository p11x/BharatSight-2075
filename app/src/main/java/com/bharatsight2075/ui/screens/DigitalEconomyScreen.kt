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
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.SectionDefaultData
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartType
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DigitalEconomyViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("digital_economy")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun DigitalEconomyScreen(
    navController: NavController,
    viewModel: DigitalEconomyViewModel = hiltViewModel()
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { BharatSightTopBar(TopBarMode.Section(title="Digital Economy",
            badge=null, onBackClick={ navController.popBackStack() })) },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(Modifier.padding(padding), contentPadding=PaddingValues(12.dp),
            verticalArrangement=Arrangement.spacedBy(12.dp)) {
            item { HeroStatsRow(chartId="digital_hero", navController, uiState.heroStats) }
            item { DashCard(chartId="upi_wave", navController, "UPI TRANSACTION VOLUME", description = "Real-time UPI transaction volume animated wave") {
                GradientAreaChart(data=ChartMockData.forType(ChartType.AREA) as List<Float>,
                    modifier=Modifier.fillMaxWidth().height(180.dp)) }}
            item { TwoColumnRow {
                DashCard(chartId="adoption_race", navController, "INTERNET PENETRATION", description = "Internet user growth by state animated bar race", modifier=Modifier.weight(1f)) {
                    SpeedometerGauge(value=0.72f, max=1.0f, label="RATIO", modifier=Modifier.fillMaxWidth().height(180.dp)) }
                DashCard(chartId="payment_sankey", navController, "E-COMMERCE GROWTH", description = "Cash → UPI → cards → BNPL → crypto flow", modifier=Modifier.weight(1f)) {
                    RingProgressCluster(rings=ChartMockData.forType(ChartType.RING_CLUSTER) as List<RingData>, modifier=Modifier.fillMaxWidth().height(180.dp)) }
            }}
        }
    }
}
