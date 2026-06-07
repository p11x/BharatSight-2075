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
import com.bharatsight2075.ui.theme.SciFiTheme
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
class AgricultureViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("agriculture")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun AgricultureScreen(
    navController: NavController,
    viewModel: AgricultureViewModel = hiltViewModel()
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { BharatSightTopBar(TopBarMode.Section(title="Agriculture Analytics",
            badge=null, onBackClick={ navController.popBackStack() })) },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(Modifier.padding(padding), contentPadding=PaddingValues(12.dp),
            verticalArrangement=Arrangement.spacedBy(12.dp)) {
            item { HeroStatsRow(chartId="agriculture_hero", navController, uiState.heroStats) }
            item { DashCard(chartId="crop_gauge", navController, "CROP YIELD TRENDS", description = "Total foodgrain production as % of national target", cardIndex = 1) {
                GradientAreaChart(data=ChartMockData.generateMockFor(ChartType.AREA),
                    modifier=Modifier.fillMaxWidth().height(180.dp)) }}
            item { TwoColumnRow {
                DashCard(chartId="soil_radar", navController, "SOIL HEALTH", description = "N/P/K/pH/organic matter/moisture soil health axes", modifier=Modifier.weight(1f), cardIndex = 2) {
                    SpeedometerGauge(value=72f, max=100f, modifier=Modifier.fillMaxWidth().height(180.dp)) }
                DashCard(chartId="msp_area", navController, "MSP RINGS", description = "Minimum Support Price trend 2015–2025", modifier=Modifier.weight(1f), cardIndex = 3) {
                    @Suppress("UNCHECKED_CAST")
                    RingProgressCluster(rings=ChartMockData.generateMockData(ChartType.RING_CLUSTER) as List<RingData>, modifier=Modifier.fillMaxWidth().height(180.dp)) }
            }}
        }
    }
}
