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
fun SectorStockHeatmapScreen(
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Nifty 500 Heatmap",
                    badge = "LIVE",
                    badgeColor = Color(0xFF4FC3F7),
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
            // 1. Nifty 500 Treemap (C16)
            item {
                key("nifty_treemap") {
                    DashCard(title = "NIFTY 500 MARKET CAP MATRIX") {
                        TreemapChart(
                            weights = listOf(19.0, 14.2, 11.8, 7.8, 6.2, 5.8, 5.6, 4.8, 4.1, 3.8, 3.5, 3.1),
                            brushes = listOf(GradPalette.GREEN_TEAL, GradPalette.TEAL_PURPLE, GradPalette.YELLOW_ORANGE, GradPalette.ORANGE_PINK),
                            modifier = Modifier.height(350.dp)
                        )
                    }
                }
            }

            // 2. Sector Returns Bar (C05)
            item {
                key("sector_returns_bar") {
                    DashCard(title = "1-DAY SECTORAL PERFORMANCE (%)") {
                        GradientBarChart(
                            data = listOf(2.1f, 1.8f, 0.9f, 1.2f, -0.8f, 0.4f, 2.3f, -1.8f),
                            labels = emptyList(),
                            modifier = Modifier.height(200.dp),
                            brush = GradPalette.TEAL_PURPLE
                        )
                    }
                }
            }

            // 3. FII/DII Flow Mirror (C22)
            item {
                key("fii_dii_mirror") {
                    DashCard(title = "FII vs DII DAILY BUYING (₹ Cr)") {
                        MirrorBarChart(
                            data = listOf(
                                MirrorData(1200f, 850f, "MON"),
                                MirrorData(450f, 1100f, "TUE"),
                                MirrorData(900f, 950f, "WED")
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 4. Volatility Wave (C11)
            item {
                key("vix_wave_stock") {
                    DashCard(title = "INDIA VIX VOLATILITY DYNAMICS") {
                        WaveformChart(
                            modifier = Modifier.height(160.dp),
                            brush = GradPalette.ORANGE_PINK
                        )
                    }
                }
            }

            // 5. Volume Candlestick (C10)
            item {
                key("nifty_candle") {
                    DashCard(title = "NIFTY 50 OHLCV ANALYSIS") {
                        CandlestickChart(
                            data = listOf(
                                CandleData(24200f, 24500f, 24100f, 24450f),
                                CandleData(24450f, 24600f, 24350f, 24550f),
                                CandleData(24550f, 24550f, 24300f, 24400f)
                            ),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 6. PE Ratio Scatter (C17)
            item {
                key("pe_scatter") {
                    DashCard(title = "VALUATION RADAR (PE vs EPS)") {
                        ScatterWithTrendLine(
                            data = listOf(
                                androidx.compose.ui.geometry.Offset(15f, 20f),
                                androidx.compose.ui.geometry.Offset(25f, 30f),
                                androidx.compose.ui.geometry.Offset(40f, 35f),
                                androidx.compose.ui.geometry.Offset(60f, 55f)
                            ),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 7. Options PCR Gauge (C04)
            item {
                key("pcr_gauge") {
                    TwoColumnRow {
                        DashCard(title = "PUT-CALL RATIO", modifier = Modifier.weight(1f)) {
                            HalfDonutGauge(value = 1.05f, max = 3f, label = "PCR", modifier = Modifier.height(180.dp))
                        }
                        DashCard(title = "MKT BREADTH", modifier = Modifier.weight(1f)) {
                            GradientDonutChart(
                                values = listOf(320f, 150f, 30f),
                                brushes = listOf(GradPalette.GREEN_TEAL, GradPalette.ORANGE_PINK, GradPalette.YELLOW_ORANGE),
                                label = "ADV/DEC",
                                modifier = Modifier.height(180.dp)
                            )
                        }
                    }
                }
            }

            // 8. FII Flow Waterfall (C19)
            item {
                key("fii_waterfall") {
                    DashCard(title = "CUMULATIVE FII FLOW (YTD)") {
                        WaterfallBarChart(
                            data = listOf(500f, 300f, -400f, 600f, 200f),
                            modifier = Modifier.height(220.dp)
                        )
                    }
                }
            }

            // 9. Delivery % Sparklines (C25)
            item {
                key("delivery_sparks") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("DELIVERY TRENDS (TOP STOCKS)", style = SciFiTheme.typography.SectionHead, color = extendedColors.primary)
                        MiniSparklineCard(label = "RELIANCE", value = "42%", data = listOf(0.4f, 0.45f, 0.38f, 0.42f, 0.4f))
                        MiniSparklineCard(label = "HDFCBANK", value = "58%", data = listOf(0.5f, 0.55f, 0.6f, 0.58f, 0.6f))
                    }
                }
            }

            // 10. Bubble Opportunity Map (C08)
            item {
                key("alpha_bubbles") {
                    DashCard(title = "ALPHA OPPORTUNITY MAP") {
                        BubbleScatterChart(
                            data = listOf(
                                BubbleData(20f, 15f, 1.2f, "RELI"),
                                BubbleData(40f, 25f, 0.8f, "TCS"),
                                BubbleData(70f, 10f, 0.4f, "SBIN")
                            ),
                            modifier = Modifier.height(240.dp)
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
fun PreviewSectorStockHeatmapScreen() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        SectorStockHeatmapScreen(onBack = {})
    }
}
