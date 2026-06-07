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
class SoftPowerViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("soft_power")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun SoftPowerScreen(
    navController: NavController,
    viewModel: SoftPowerViewModel = hiltViewModel()
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { BharatSightTopBar(TopBarMode.Section(title="Soft Power & Diaspora",
            badge=null, onBackClick={ navController.popBackStack() })) },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(Modifier.padding(padding), contentPadding=PaddingValues(12.dp),
            verticalArrangement=Arrangement.spacedBy(12.dp)) {
            item { HeroStatsRow(chartId="soft_power_hero", navController, uiState.heroStats) }
            item { DashCard(chartId="brand_radar", navController, "BRAND VALUE TREND", description = "Brand India vs China/USA/UK/France axes") {
                GradientAreaChart(data=ChartMockData.forType(ChartType.AREA) as List<Float>,
                    modifier=Modifier.fillMaxWidth().height(180.dp)) }}
            item { TwoColumnRow {
                DashCard(chartId="diaspora_network", navController, "DIASPORA COUNT", description = "Global Indian diaspora connection network", modifier=Modifier.weight(1f)) {
                    SpeedometerGauge(value=0.72f, max=1.0f, label="MILLIONS", modifier=Modifier.fillMaxWidth().height(180.dp)) }
                DashCard(chartId="index_orbital", navController, "CULTURAL INDEX", description = "Soft power index components orbital rings", modifier=Modifier.weight(1f)) {
                    RingProgressCluster(rings=ChartMockData.forType(ChartType.RING_CLUSTER) as List<RingData>, modifier=Modifier.fillMaxWidth().height(180.dp)) }
            }}
        }
    }
}
