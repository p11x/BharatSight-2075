package com.bharatsight2075.ui.forecast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun Forecaster2075Screen(
    navController: NavController,
    viewModel: ForecastEngineViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val forecastState = state as? ForecastUiState ?: ForecastUiState()
    val extendedColors = SciFiTheme.extendedColors

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Economic Forecaster",
                    badge = "AI",
                    badgeColor = extendedColors.accent,
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
                DashCard(
                    chartId = "forecast_trajectory",
                    navController = navController,
                    title = "LIVE SCENARIO PROJECTION", 
                    showLiveDot = true
                ) {
                    MultiLineChart(
                        data = listOf(
                            forecastState.predictedGdp,
                            forecastState.predictedGdp.map { it * 0.85f },
                            forecastState.predictedGdp.map { it * 1.15f }
                        ),
                        colors = listOf(extendedColors.primary, extendedColors.accent, Color(0xFF00E676)),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_variance",
                    navController = navController,
                    title = "STATISTICAL VARIANCE (P10-P90)"
                ) {
                    GradientAreaChart(
                        data = forecastState.predictedGdp,
                        modifier = Modifier.height(200.dp),
                        strokeColor = extendedColors.primary.copy(alpha = 0.8f)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_impact",
                    navController = navController,
                    title = "POLICY CONFIGURATION IMPACT"
                ) {
                    RadarPolygonChart(
                        data = listOf(
                            forecastState.taxRate,
                            forecastState.infrastructure,
                            forecastState.education,
                            forecastState.foreignPolicy,
                            0.75f
                        ),
                        labels = listOf("TAX", "INFRA", "EDU", "FP", "BAL"),
                        modifier = Modifier.height(300.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_waterfall",
                    navController = navController,
                    title = "DECADAL GDP DELTA (ESTIMATED)"
                ) {
                    WaterfallBarChart(
                        data = listOf(8f, 12f, 15f, 22f, 28f, 35f, 42f, 50f),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "forecast_hdi",
                        navController = navController,
                        title = "HDI SPEEDO", 
                        modifier = Modifier.weight(1f)
                    ) {
                        SpeedometerGauge(value = 0.85f, max = 1f, label = "2075 INDEX", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "forecast_gini",
                        navController = navController,
                        title = "GINI DONUT", 
                        modifier = Modifier.weight(1f)
                    ) {
                        HalfDonutGauge(value = 0.32f, max = 1f, label = "COEFFICIENT", modifier = Modifier.height(180.dp))
                    }
                }
            }

            item {
                DashCard(
                    chartId = "forecast_composition",
                    navController = navController,
                    title = "GDP COMPOSITION SHIFT (2026-75)"
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
                    chartId = "forecast_energy",
                    navController = navController,
                    title = "ENERGY SOURCE FORECAST"
                ) {
                    GradientDonutChart(
                        values = listOf(70f, 30f),
                        brushes = emptyList(),
                        label = "RENEWABLE %",
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_urbanization",
                    navController = navController,
                    title = "URBANIZATION SATURATION"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(160.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_export_polar",
                    navController = navController,
                    title = "EXPORT CATEGORY DYNAMICS"
                ) {
                    PolarAreaChart(
                        data = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(260.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_income_bubbles",
                    navController = navController,
                    title = "INCOME vs GROWTH DENSITY"
                ) {
                    BubbleScatterChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_preset_rings",
                    navController = navController,
                    title = "POLICY PRESET SCORECARD"
                ) {
                    RingProgressCluster(
                        rings = emptyList(),
                        centerStat = "A+",
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_milestones",
                    navController = navController,
                    title = "PROJECTED ECONOMIC MILESTONES"
                ) {
                    TimelineEventChart(
                        events = emptyList(),
                        modifier = Modifier.height(180.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_heatmap",
                    navController = navController,
                    title = "SCENARIO ATTRIBUTE MATRIX"
                ) {
                    HeatmapGridChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_stability",
                    navController = navController,
                    title = "SIMULATION STABILITY INDEX"
                ) {
                    HorizontalProgressBars(
                        items = emptyList(),
                        modifier = Modifier.height(120.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "forecast_synergy",
                    navController = navController,
                    title = "GLOBAL TRADE SYNERGY"
                ) {
                    VennDiagramChart(modifier = Modifier.height(280.dp))
                }
            }

            item {
                DashCard(
                    chartId = "forecast_config",
                    navController = navController,
                    title = "POLICY CONFIGURATION (WHAT-IF)"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        val handleIntent = viewModel::handleIntent
                        ThemedPolicySlider(
                            label = "TAX RATE",
                            value = forecastState.taxRate,
                            onValueChange = { handleIntent(ForecastIntent.UpdateTaxRate(it)) }
                        )
                        ThemedPolicySlider(
                            label = "INFRASTRUCTURE",
                            value = forecastState.infrastructure,
                            onValueChange = { handleIntent(ForecastIntent.UpdateInfrastructure(it)) }
                        )
                        ThemedPolicySlider(
                            label = "EDUCATION",
                            value = forecastState.education,
                            onValueChange = { handleIntent(ForecastIntent.UpdateEducation(it)) }
                        )
                        ThemedPolicySlider(
                            label = "FOREIGN POLICY",
                            value = forecastState.foreignPolicy,
                            onValueChange = { handleIntent(ForecastIntent.UpdateForeignPolicy(it)) }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
