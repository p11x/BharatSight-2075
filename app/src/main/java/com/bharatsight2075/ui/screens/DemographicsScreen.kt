package com.bharatsight2075.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun DemographicsScreen(
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
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
            // 1. Population Pyramid (C22)
            item {
                key("population_pyramid") {
                    DashCard(title = "POPULATION PYRAMID (AGE COHORT)") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(80f, 78f, "0-14"),
                                MirrorData(100f, 95f, "15-64"),
                                MirrorData(40f, 45f, "65+")
                            ),
                            leftBrush = GradPalette.TEAL_PURPLE,
                            rightBrush = GradPalette.PINK_PURPLE,
                            modifier = Modifier.height(260.dp)
                        )
                    }
                }
            }

            // 2. Age Distribution Polar (C18)
            item {
                key("age_distribution") {
                    DashCard(title = "AGE COHORT RADIAL SHARE") {
                        PolarAreaChart(
                            data = listOf(22f, 25f, 28f, 25f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE),
                            modifier = Modifier.height(250.dp)
                        )
                    }
                }
            }

            // 3. Urbanization Wave (C11)
            item {
                key("urban_drift") {
                    DashCard(title = "URBAN POPULATION GROWTH WAVE") {
                        WaveformChart(
                            modifier = Modifier.height(180.dp),
                            brush = GradPalette.ORANGE_PINK
                        )
                    }
                }
            }

            // 4. Literacy Heatmap (C15)
            item {
                key("literacy_matrix") {
                    DashCard(title = "STATE-WISE LITERACY DYNAMICS") {
                        HeatmapGridChart(
                            data = List(12) { List(8) { (40..100).random() / 100f } },
                            modifier = Modifier.height(280.dp),
                            brush = GradPalette.GREEN_TEAL
                        )
                    }
                }
            }

            // 5. Fertility Rate Area (C01)
            item {
                key("fertility_trend") {
                    DashCard(title = "FERTILITY RATE (TFR) PROJECTION") {
                        GradientAreaChart(
                            data = listOf(2.4f, 2.3f, 2.1f, 2.0f, 1.9f, 1.8f),
                            modifier = Modifier.height(200.dp),
                            brush = GradPalette.PINK_PURPLE,
                            strokeColor = Color(0xFFF06292)
                        )
                    }
                }
            }

            // 6. Indicators Speedo (C20) + Half Donut (C04)
            item {
                key("demo_gauges") {
                    TwoColumnRow {
                        DashCard(title = "LIFE EXPECTANCY", modifier = Modifier.weight(1f)) {
                            SpeedometerGauge(value = 70.1f, max = 100f, label = "YEARS", modifier = Modifier.height(180.dp))
                        }
                        DashCard(title = "DEP RATIO", modifier = Modifier.weight(1f)) {
                            HalfDonutGauge(value = 0.45f, max = 1f, label = "INDEX", modifier = Modifier.height(180.dp))
                        }
                    }
                }
            }

            // 7. Demographic Dividend Area (C07)
            item {
                key("dividend_stack") {
                    DashCard(title = "DEMOGRAPHIC DIVIDEND PEAK") {
                        StackedAreaChart(
                            data = listOf(
                                listOf(35f, 30f, 25f, 20f),
                                listOf(50f, 55f, 60f, 58f),
                                listOf(15f, 15f, 15f, 22f)
                            ),
                            brushes = listOf(GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.TEAL_PURPLE),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 8. Language Treemap (C16)
            item {
                key("language_treemap") {
                    DashCard(title = "LANGUAGE FAMILY DISTRIBUTION") {
                        TreemapChart(
                            weights = listOf(43.0, 12.0, 8.0, 7.0, 5.0, 4.0),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL),
                            modifier = Modifier.height(300.dp)
                        )
                    }
                }
            }

            // 9. HDI Multi-line (C02)
            item {
                key("hdi_global_comp") {
                    DashCard(title = "HDI TRAJECTORY vs PEERS") {
                        MultiLineChart(
                            data = listOf(
                                listOf(0.45f, 0.52f, 0.61f, 0.64f),
                                listOf(0.65f, 0.72f, 0.78f, 0.81f),
                                listOf(0.70f, 0.75f, 0.80f, 0.82f)
                            ),
                            colors = listOf(extendedColors.primary, extendedColors.accent, Color(0xFF00E676)),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDemographicsScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        DemographicsScreen(onBack = {})
    }
}
