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
class SpaceTechViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("space_tech")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun SpaceTechScreen(
    navController: NavController,
    viewModel: SpaceTechViewModel = hiltViewModel()
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { BharatSightTopBar(TopBarMode.Section(title="Space Tech & ISRO",
            badge=null, onBackClick={ navController.popBackStack() })) },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(Modifier.padding(padding), contentPadding=PaddingValues(12.dp),
            verticalArrangement=Arrangement.spacedBy(12.dp)) {
            item { HeroStatsRow(chartId="space_tech_hero", navController, uiState.heroStats) }
            item { DashCard(chartId="launch_orbital", navController, "LAUNCH FREQUENCY", description = "Launch vehicle types and payload capacity rings") {
                GradientAreaChart(data=ChartMockData.forType(ChartType.AREA) as List<Float>,
                    modifier=Modifier.fillMaxWidth().height(180.dp)) }}
            item { TwoColumnRow {
                DashCard(chartId="global_race", navController, "STARTUP COUNT", description = "Space economy share animated country race", modifier=Modifier.weight(1f)) {
                    SpeedometerGauge(value=0.72f, max=1.0f, label="GROWTH", modifier=Modifier.fillMaxWidth().height(180.dp)) }
                DashCard(chartId="space_terrace", navController, "BUDGET UTILIZATION", description = "Discovery → build → launch → operations tier", modifier=Modifier.weight(1f)) {
                    RingProgressCluster(rings=ChartMockData.forType(ChartType.RING_CLUSTER) as List<RingData>, modifier=Modifier.fillMaxWidth().height(180.dp)) }
            }}
        }
    }
}
