package com.bharatsight2075.ui.forecast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun Forecaster2075Screen(
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
            // 1. Live Trajectory Preview (C02)
            item {
                key("live_forecast") {
                    DashCard(title = "LIVE SCENARIO PROJECTION", showLiveDot = true) {
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
            }

            // 2. Confidence Shaded Bands (C01)
            item {
                key("confidence_bands") {
                    DashCard(title = "STATISTICAL VARIANCE (P10-P90)") {
                        GradientAreaChart(
                            data = forecastState.predictedGdp,
                            modifier = Modifier.height(200.dp),
                            strokeColor = extendedColors.primary.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // 3. Impact Analysis Radar (C09)
            item {
                key("policy_radar") {
                    DashCard(title = "POLICY CONFIGURATION IMPACT") {
                        RadarPolygonChart(
                            data = listOf(
                                forecastState.taxRate,
                                forecastState.infrastructure,
                                forecastState.education,
                                forecastState.foreignPolicy,
                                0.75f // Balanced weight
                            ),
                            labels = listOf("TAX", "INFRA", "EDU", "FP", "BAL"),
                            modifier = Modifier.height(300.dp),
                            brush = GradPalette.PINK_PURPLE
                        )
                    }
                }
            }

            // 4. GDP Decadal Waterfall (C19)
            item {
                key("decadal_delta") {
                    DashCard(title = "DECADAL GDP DELTA (ESTIMATED)") {
                        WaterfallBarChart(
                            data = listOf(8f, 12f, 15f, 22f, 28f, 35f, 42f, 50f),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 5. Macro Shift Pager (C07 / C04)
            item {
                key("forecast_indicators") {
                    TwoColumnRow {
                        DashCard(title = "HDI SPEEDO", modifier = Modifier.weight(1f)) {
                            SpeedometerGauge(value = 0.85f, max = 1f, label = "2075 INDEX", modifier = Modifier.height(180.dp))
                        }
                        DashCard(title = "GINI DONUT", modifier = Modifier.weight(1f)) {
                            HalfDonutGauge(value = 0.32f, max = 1f, label = "COEFFICIENT", modifier = Modifier.height(180.dp))
                        }
                    }
                }
            }

            // 6. Sector Shift Stacked Area (C07)
            item {
                key("sector_shift") {
                    DashCard(title = "GDP COMPOSITION SHIFT (2026-75)") {
                        StackedAreaChart(
                            data = listOf(
                                listOf(15f, 12f, 10f, 8f, 5f), // Agri
                                listOf(25f, 28f, 32f, 35f, 38f), // Industry
                                listOf(60f, 60f, 58f, 57f, 57f)  // Services
                            ),
                            brushes = listOf(GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.PURPLE_BLUE),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 7. Energy Mix Forecast (C03)
            item {
                key("energy_forecast") {
                    DashCard(title = "ENERGY SOURCE FORECAST") {
                        GradientDonutChart(
                            values = listOf(70f, 30f),
                            brushes = listOf(GradPalette.GREEN_TEAL, GradPalette.ORANGE_PINK),
                            label = "RENEWABLE %",
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 8. Urbanization Wave (C11)
            item {
                key("urban_wave") {
                    DashCard(title = "URBANIZATION SATURATION") {
                        WaveformChart(
                            modifier = Modifier.height(160.dp),
                            brush = GradPalette.CYAN_WHITE
                        )
                    }
                }
            }

            // 9. Export Polar Area (C18)
            item {
                key("export_polar") {
                    DashCard(title = "EXPORT CATEGORY DYNAMICS") {
                        PolarAreaChart(
                            data = listOf(80f, 65f, 90f, 50f, 75f, 40f, 85f, 60f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE),
                            modifier = Modifier.height(260.dp)
                        )
                    }
                }
            }

            // 10. Per-capita Bubble Map (C08)
            item {
                key("income_bubble") {
                    DashCard(title = "INCOME vs GROWTH DENSITY") {
                        BubbleScatterChart(
                            data = listOf(
                                BubbleData(10f, 20f, 0.4f, "MH"),
                                BubbleData(30f, 50f, 0.8f, "TN"),
                                BubbleData(60f, 40f, 1.2f, "GJ"),
                                BubbleData(80f, 10f, 0.6f, "KA")
                            ),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 11. Policy Preset Rings (C12)
            item {
                key("preset_rings") {
                    DashCard(title = "POLICY PRESET SCORECARD") {
                        RingProgressCluster(
                            rings = listOf(
                                RingData(0.85f, "GROWTH", GradPalette.TEAL_PURPLE),
                                RingData(0.70f, "WELFARE", GradPalette.ORANGE_PINK),
                                RingData(0.90f, "BALANCED", GradPalette.GREEN_TEAL)
                            ),
                            centerStat = "A+",
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 12. Projection Milestones (C23)
            item {
                key("milestone_timeline") {
                    DashCard(title = "PROJECTED ECONOMIC MILESTONES") {
                        TimelineEventChart(
                            events = listOf(
                                TimelineEvent(2035, "IND #3 GDP", true),
                                TimelineEvent(2050, "500M URBAN", false),
                                TimelineEvent(2065, "NET ZERO", true),
                                TimelineEvent(2075, "$37T TARGET", false)
                            ),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            // 13. Scenario Heatmap (C15)
            item {
                key("scenario_heatmap") {
                    DashCard(title = "SCENARIO ATTRIBUTE MATRIX") {
                        HeatmapGridChart(
                            data = List(4) { List(5) { (20..100).random() / 100f } },
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 14. Real-time Policy Progress (C06)
            item {
                key("policy_progress") {
                    DashCard(title = "SIMULATION STABILITY INDEX") {
                        HorizontalProgressBars(
                            items = listOf(
                                "COMPUTATIONAL ACCURACY" to 0.98f,
                                "DATA FRESHNESS" to 0.85f,
                                "MODEL CONFIDENCE" to 0.92f
                            )
                        )
                    }
                }
            }

            // 15. Global Synergy Venn (C13)
            item {
                key("global_synergy") {
                    DashCard(title = "GLOBAL TRADE SYNERGY") {
                        VennDiagramChart(modifier = Modifier.height(280.dp))
                    }
                }
            }

            // Config Sliders
            item {
                key("what_if_panel") {
                    DashCard(title = "POLICY CONFIGURATION (WHAT-IF)") {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            ThemedPolicySlider(
                                label = "TAX RATE",
                                value = forecastState.taxRate,
                                onValueChange = { viewModel.handleIntent(ForecastIntent.UpdateTaxRate(it)) }
                            )
                            ThemedPolicySlider(
                                label = "INFRASTRUCTURE",
                                value = forecastState.infrastructure,
                                onValueChange = { viewModel.handleIntent(ForecastIntent.UpdateInfrastructure(it)) }
                            )
                            ThemedPolicySlider(
                                label = "EDUCATION",
                                value = forecastState.education,
                                onValueChange = { viewModel.handleIntent(ForecastIntent.UpdateEducation(it)) }
                            )
                            ThemedPolicySlider(
                                label = "FOREIGN POLICY",
                                value = forecastState.foreignPolicy,
                                onValueChange = { viewModel.handleIntent(ForecastIntent.UpdateForeignPolicy(it)) }
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewForecaster2075Screen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        Forecaster2075Screen(onBack = {})
    }
}
