package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
class DemographicsViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("demographics")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun DemographicsScreen(
    navController: NavController,
    viewModel: DemographicsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Demographics",
                    badge = "2075 PROJ",
                    badgeColor = Color(0xFFF06292),
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
                    chartId = "demo_hero",
                    navController = navController,
                    stats = uiState.heroStats
                )
            }
            item {
                DashCard(
                    chartId = "demo_population_pyramid",
                    navController = navController,
                    title = "POPULATION PYRAMID (AGE COHORT)"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(260.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "demo_age_dist",
                    navController = navController,
                    title = "AGE COHORT RADIAL SHARE"
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
                    chartId = "demo_urban_wave",
                    navController = navController,
                    title = "URBAN POPULATION GROWTH WAVE"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "demo_literacy_heatmap",
                    navController = navController,
                    title = "STATE-WISE LITERACY DYNAMICS"
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(280.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "demo_fertility_area",
                    navController = navController,
                    title = "FERTILITY RATE (TFR) PROJECTION"
                ) {
                    GradientAreaChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp),
                        strokeColor = Color(0xFFF06292)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "demo_life_expectancy",
                        navController = navController,
                        title = "LIFE EXPECTANCY", 
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(value = 70.1f, max = 100f, label = "YEARS", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "demo_dep_ratio",
                        navController = navController,
                        title = "DEP RATIO", 
                        modifier = Modifier.weight(1f)
                    ) {
                        HalfDonutGauge(value = 0.45f, max = 1f, label = "INDEX", modifier = Modifier.height(180.dp))
                    }
                }
            }

            item {
                DashCard(
                    chartId = "demo_dividend_area",
                    navController = navController,
                    title = "DEMOGRAPHIC DIVIDEND PEAK"
                ) {
                    StackedAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "demo_language_treemap",
                    navController = navController,
                    title = "LANGUAGE FAMILY DISTRIBUTION"
                ) {
                    TreemapChart(
                        weights = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(300.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "demo_hdi_multi",
                    navController = navController,
                    title = "HDI TRAJECTORY vs PEERS"
                ) {
                    MultiLineChart(
                        data = emptyList(),
                        colors = listOf(SciFiTheme.extendedColors.primary, SciFiTheme.extendedColors.accent, Color(0xFF00E676)),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
