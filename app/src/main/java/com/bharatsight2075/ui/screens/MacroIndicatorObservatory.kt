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
fun MacroIndicatorObservatory(
    onBack: () -> Unit
) {
    val extendedColors = SciFiTheme.extendedColors
    
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
            // 1. CPI Trend
            item {
                key("cpi_trend") {
                    DashCard(title = "CPI INFLATION TREND (24M)") {
                        GradientAreaChart(
                            data = listOf(4.2f, 4.8f, 5.1f, 4.9f, 4.5f, 4.2f, 4.1f, 4.3f, 4.5f, 4.7f, 4.2f, 4.0f),
                            modifier = Modifier.height(200.dp),
                            brush = GradPalette.ORANGE_PINK,
                            strokeColor = Color(0xFFFF6B35)
                        )
                    }
                }
            }

            // 2. WPI vs CPI
            item {
                key("wpi_vs_cpi") {
                    DashCard(title = "CPI vs WPI DIVERGENCE") {
                        MultiLineChart(
                            data = listOf(
                                listOf(4.2f, 4.5f, 4.3f, 4.1f, 4.0f, 4.2f),
                                listOf(2.1f, 2.5f, 2.8f, 2.3f, 1.9f, 2.1f)
                            ),
                            colors = listOf(Color(0xFFFF6B35), Color(0xFF00F5FF)),
                            modifier = Modifier.height(200.dp)
                        )
                    }
                }
            }

            // 3. IIP Growth
            item {
                key("iip_growth") {
                    DashCard(title = "IIP SECTORAL GROWTH") {
                        GradientBarChart(
                            data = listOf(5.8f, 7.2f, 4.1f, 6.5f, 8.1f),
                            labels = listOf("MFG", "MIN", "ELE", "CON", "GEN"),
                            modifier = Modifier.height(180.dp),
                            brush = GradPalette.GREEN_TEAL
                        )
                    }
                }
            }

            // 4. PMI Gauge & Fiscal Deficit
            item {
                key("pmi_fiscal") {
                    TwoColumnRow {
                        DashCard(title = "PMI GAUGE", modifier = Modifier.weight(1f)) {
                            SpeedometerGauge(value = 56.4f, max = 70f, label = "COMPOSITE", modifier = Modifier.height(180.dp))
                        }
                        DashCard(title = "FISCAL DEFICIT", modifier = Modifier.weight(1f)) {
                            HalfDonutGauge(value = 5.1f, max = 10f, label = "% OF GDP", modifier = Modifier.height(180.dp))
                        }
                    }
                }
            }

            // 5. FX Reserves Ticker
            item {
                key("fx_reserves") {
                    DashCard(title = "FOREIGN EXCHANGE RESERVES") {
                        GlowingNumberTicker(
                            value = 620.0f,
                            unit = "USD BILLION",
                            delta = "▲ 2.1% MoM",
                            brush = GradPalette.GOLD_WHITE
                        )
                    }
                }
            }

            // 6. Bond Yield Curve
            item {
                key("yield_curve") {
                    DashCard(title = "G-SEC YIELD CURVE") {
                        MultiLineChart(
                            data = listOf(
                                listOf(6.8f, 7.0f, 7.15f, 7.25f, 7.32f, 7.40f)
                            ),
                            colors = listOf(Color(0xFF7C4DFF)),
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            // 7. Anomaly Heatmap
            item {
                key("anomaly_heatmap") {
                    DashCard(title = "INDICATOR ANOMALY MATRIX") {
                        HeatmapGridChart(
                            data = List(12) { List(12) { (0..100).random() / 100f } },
                            modifier = Modifier.height(280.dp)
                        )
                    }
                }
            }

            // 8. Liquidity Wave
            item {
                key("liquidity_wave") {
                    DashCard(title = "DAILY INTERBANK LIQUIDITY") {
                        WaveformChart(
                            modifier = Modifier.height(150.dp),
                            brush = GradPalette.CYAN_WHITE
                        )
                    }
                }
            }

            // 9. FDI Inflow Polar
            item {
                key("fdi_polar") {
                    DashCard(title = "FDI BY SOURCE SECTOR") {
                        PolarAreaChart(
                            data = listOf(85f, 65f, 45f, 30f, 25f),
                            brushes = listOf(GradPalette.TEAL_PURPLE, GradPalette.ORANGE_PINK, GradPalette.GREEN_TEAL, GradPalette.YELLOW_ORANGE, GradPalette.PURPLE_BLUE),
                            modifier = Modifier.height(250.dp)
                        )
                    }
                }
            }

            // 10. Alert Rings
            item {
                key("alert_rings") {
                    DashCard(title = "SYSTEM ALERTS MONITOR") {
                        RingProgressCluster(
                            rings = listOf(
                                RingData(0.2f, "HIGH", GradPalette.ORANGE_PINK),
                                RingData(0.4f, "MED", GradPalette.YELLOW_ORANGE),
                                RingData(0.8f, "LOW", GradPalette.GREEN_TEAL)
                            ),
                            centerStat = "03",
                            modifier = Modifier.height(200.dp)
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
fun PreviewMacroIndicatorObservatory() {
    SciFiTheme.ProvideSciFiTheme(SciFiTheme.Theme.Cyberpunk) {
        MacroIndicatorObservatory(onBack = {})
    }
}
