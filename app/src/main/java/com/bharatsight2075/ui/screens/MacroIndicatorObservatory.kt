package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.SectionDefaultData
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MacroHubViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("macro_hub")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun MacroIndicatorObservatory(
    navController: NavController,
    viewModel: MacroHubViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Macro Indicator Hub",
                    badge = "LIVE",
                    badgeColor = Color(0xFF00E676),
                    onBackClick = onBack
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                HeroStatsRow(
                    chartId = "hub_hero",
                    navController = navController,
                    stats = uiState.heroStats
                )
            }
            item {
                DashCard(
                    chartId = "observatory_cpi",
                    navController = navController,
                    title = "CPI INFLATION TREND (24M)"
                ) {
                    GradientAreaChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp),
                        strokeColor = Color(0xFFFF6B35)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "observatory_wpi_cpi",
                    navController = navController,
                    title = "CPI vs WPI DIVERGENCE"
                ) {
                    MultiLineChart(
                        data = emptyList(),
                        colors = listOf(Color(0xFFFF6B35), Color(0xFF00F5FF)),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "observatory_iip",
                    navController = navController,
                    title = "IIP SECTORAL GROWTH"
                ) {
                    GradientBarChart(
                        data = emptyList(),
                        labels = listOf("MFG", "MIN", "ELE", "CON", "GEN"),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "observatory_pmi",
                        navController = navController,
                        title = "PMI GAUGE", 
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(value = 56.4f, max = 70f, label = "COMPOSITE", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "observatory_deficit",
                        navController = navController,
                        title = "FISCAL DEFICIT", 
                        modifier = Modifier.weight(1f)
                    ) {
                        HalfDonutGauge(value = 5.1f, max = 10f, label = "% OF GDP", modifier = Modifier.height(180.dp))
                    }
                }
            }

            item {
                DashCard(
                    chartId = "fx_reserves",
                    navController = navController,
                    title = "FOREIGN EXCHANGE RESERVES",
                    description = "India's foreign exchange reserves — monthly levels"
                ) {
                    GlowingNumberTicker(
                        value = 620.0f,
                        unit = "USD BILLION",
                        delta = "▲ 2.1% MoM"
                    )
                }
            }

            item {
                DashCard(
                    chartId = "observatory_yield",
                    navController = navController,
                    title = "G-SEC YIELD CURVE"
                ) {
                    MultiLineChart(
                        data = emptyList(),
                        colors = listOf(Color(0xFF7C4DFF)),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "observatory_anomalies",
                    navController = navController,
                    title = "INDICATOR ANOMALY MATRIX"
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(280.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "observatory_liquidity",
                    navController = navController,
                    title = "DAILY INTERBANK LIQUIDITY"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(150.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "observatory_fdi",
                    navController = navController,
                    title = "FDI BY SOURCE SECTOR"
                ) {
                    PolarAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(250.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "observatory_alerts",
                    navController = navController,
                    title = "SYSTEM ALERTS MONITOR"
                ) {
                    RingProgressCluster(
                        rings = emptyList(),
                        centerStat = "03",
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
