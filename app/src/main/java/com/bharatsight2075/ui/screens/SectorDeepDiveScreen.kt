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
fun SectorDeepDiveScreen(
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Sector Analysis",
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
            // 1. Sector Treemap (C16)
            item {
                key("sector_market_cap") {
                    DashCard(title = "SUB-SECTOR MARKET DENSITY") {
                        TreemapChart(
                            weights = listOf(53.0, 28.0, 19.0, 12.0, 8.0, 5.0),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL),
                            modifier = Modifier.height(320.dp)
                        )
                    }
                }
            }

            // 2. Sector Area Trend (C07)
            item {
                key("gva_trend") {
                    DashCard(title = "GVA CONTRIBUTION TREND (%)") {
                        StackedAreaChart(
                            data = listOf(
                                listOf(50f, 52f, 53f, 54f, 53f),
                                listOf(25f, 26f, 28f, 27f, 28f),
                                listOf(25f, 22f, 19f, 19f, 19f)
                            ),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL),
                            modifier = Modifier.height(240.dp)
                        )
                    }
                }
            }

            // 3. Sector Performance Radar (C09)
            item {
                key("sector_metrics_radar") {
                    DashCard(title = "CORE PERFORMANCE ATTRIBUTES") {
                        RadarPolygonChart(
                            data = listOf(0.85f, 0.72f, 0.90f, 0.65f, 0.55f, 0.88f),
                            labels = listOf("GROWTH", "MARGIN", "ESG", "DEBT", "CAPEX", "FDI"),
                            modifier = Modifier.height(320.dp),
                            brush = GradPalette.PINK_PURPLE
                        )
                    }
                }
            }

            // 4. Productivity Scatter (C17)
            item {
                key("productivity_dynamics") {
                    DashCard(title = "OUTPUT PER WORKER vs AVG WAGE") {
                        ScatterWithTrendLine(
                            data = listOf(
                                androidx.compose.ui.geometry.Offset(20f, 35f),
                                androidx.compose.ui.geometry.Offset(40f, 50f),
                                androidx.compose.ui.geometry.Offset(60f, 75f),
                                androidx.compose.ui.geometry.Offset(80f, 85f),
                                androidx.compose.ui.geometry.Offset(90f, 95f)
                            ),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 5. Gauges Row (C04 / C20)
            item {
                key("sector_gauges") {
                    TwoColumnRow {
                        DashCard(title = "MARKET SHARE", modifier = Modifier.weight(1f)) {
                            HalfDonutGauge(value = 53.2f, max = 100f, label = "PERCENT", modifier = Modifier.height(180.dp))
                        }
                        DashCard(title = "UTILIZATION", modifier = Modifier.weight(1f)) {
                            SpeedometerGauge(value = 78.5f, max = 100f, label = "CAPACITY", modifier = Modifier.height(180.dp))
                        }
                    }
                }
            }

            // 6. Growth Rate Bars (C05)
            item {
                key("growth_by_subsector") {
                    DashCard(title = "SUB-SECTOR YOY GROWTH (%)") {
                        GradientBarChart(
                            data = listOf(9.2f, 7.5f, 12.1f, 4.8f, 6.3f, 10.5f),
                            labels = emptyList(),
                            modifier = Modifier.height(200.dp),
                            brush = GradPalette.GREEN_TEAL
                        )
                    }
                }
            }

            // 7. Export Revenue Mirror (C22)
            item {
                key("revenue_mix") {
                    DashCard(title = "DOMESTIC vs EXPORT REVENUE") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(60f, 40f, "IT"),
                                MirrorData(85f, 15f, "AGRI"),
                                MirrorData(45f, 55f, "MFG")
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 8. Sector Price Index Wave (C11)
            item {
                key("price_wave") {
                    DashCard(title = "SECTORAL WPI VOLATILITY") {
                        WaveformChart(
                            modifier = Modifier.height(150.dp),
                            brush = GradPalette.ORANGE_PINK
                        )
                    }
                }
            }

            // 9. Waterfall Revenue Decomposition (C19)
            item {
                key("rev_waterfall") {
                    DashCard(title = "REVENUE COMPONENT WATERFALL") {
                        WaterfallBarChart(
                            data = listOf(100f, 25f, 15f, -10f, -20f, 30f),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 10. Sector OHLCV Candles (C10)
            item {
                key("index_candles") {
                    DashCard(title = "SECTOR INDEX FLUCTUATION (OHLC)") {
                        CandlestickChart(
                            data = listOf(
                                CandleData(120f, 135f, 115f, 130f),
                                CandleData(130f, 140f, 125f, 128f),
                                CandleData(128f, 132f, 110f, 112f),
                                CandleData(112f, 125f, 108f, 122f)
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 11. FDI Matrix Heatmap (C15)
            item {
                key("fdi_sector_matrix") {
                    DashCard(title = "HISTORICAL FDI INFLOW MATRIX") {
                        HeatmapGridChart(
                            data = List(6) { List(10) { (10..100).random() / 100f } },
                            modifier = Modifier.height(240.dp),
                            brush = GradPalette.TEAL_PURPLE
                        )
                    }
                }
            }

            // 12. Employment Polar (C18)
            item {
                key("employment_polar_sector") {
                    DashCard(title = "WORKFORCE DISTRIBUTION") {
                        PolarAreaChart(
                            data = listOf(45f, 25f, 15f, 10f, 5f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE),
                            modifier = Modifier.height(250.dp)
                        )
                    }
                }
            }

            // 13. Investment Timeline (C23)
            item {
                key("sector_timeline") {
                    DashCard(title = "MAJOR STRATEGIC INVESTMENTS") {
                        TimelineEventChart(
                            events = listOf(
                                TimelineEvent(2022, "PLI PHASE 1", true),
                                TimelineEvent(2023, "JV SIGNED", false),
                                TimelineEvent(2024, "FAB LAUNCH", true),
                                TimelineEvent(2025, "EXPORT PEAK", false)
                            ),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            // 14. Global Rank Ring (C12)
            item {
                key("rank_rings") {
                    DashCard(title = "GLOBAL SUB-SECTOR RANKINGS") {
                        RingProgressCluster(
                            rings = listOf(
                                RingData(0.95f, "CORE", GradPalette.TEAL_PURPLE),
                                RingData(0.82f, "AUX", GradPalette.ORANGE_PINK),
                                RingData(0.65f, "ALLIED", GradPalette.GREEN_TEAL)
                            ),
                            centerStat = "#02",
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 15. Sector Synergy Venn (C13)
            item {
                key("synergy_venn") {
                    DashCard(title = "INTER-SECTORAL SYNERGY MAP") {
                        VennDiagramChart(modifier = Modifier.height(280.dp))
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSectorDeepDiveScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        SectorDeepDiveScreen(onBack = {})
    }
}
