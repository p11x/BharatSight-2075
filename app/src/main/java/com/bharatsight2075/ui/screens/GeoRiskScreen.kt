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
class GeoRiskViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("geo_risk")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun GeoRiskScreen(
    navController: NavController,
    viewModel: GeoRiskViewModel = hiltViewModel()
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { BharatSightTopBar(TopBarMode.Section(title="Geopolitical Risk",
            badge=null, onBackClick={ navController.popBackStack() })) },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(Modifier.padding(padding), contentPadding=PaddingValues(12.dp),
            verticalArrangement=Arrangement.spacedBy(12.dp)) {
            item { HeroStatsRow(chartId="geo_risk_hero", navController, uiState.heroStats) }
            item { DashCard(chartId="risk_heatmap", navController, "RISK SCORE TRAJECTORY", description = "Country risk score 12-month calendar heatmap") {
                GradientAreaChart(data=ChartMockData.forType(ChartType.AREA) as List<Float>,
                    modifier=Modifier.fillMaxWidth().height(180.dp)) }}
            item { TwoColumnRow {
                DashCard(chartId="border_candle", navController, "BORDER STABILITY", description = "Border tension index OHLC quarterly", modifier=Modifier.weight(1f)) {
                    SpeedometerGauge(value=0.72f, max=1.0f, label="INDEX", modifier=Modifier.fillMaxWidth().height(180.dp)) }
                DashCard(chartId="threat_pulse", navController, "CONFLICT MATRIX", description = "Active geopolitical threat blips by direction", modifier=Modifier.weight(1f)) {
                    RingProgressCluster(rings=ChartMockData.forType(ChartType.RING_CLUSTER) as List<RingData>, modifier=Modifier.fillMaxWidth().height(180.dp)) }
            }}
        }
    }
}
