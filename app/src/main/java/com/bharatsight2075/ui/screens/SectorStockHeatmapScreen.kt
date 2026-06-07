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
class MarketViewModel @Inject constructor() : ViewModel() {
    private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("stock_heatmap")))
    val data: StateFlow<SectionDefaultData> = _data.asStateFlow()
}

@Composable
fun SectorStockHeatmapScreen(
    navController: NavController,
    viewModel: MarketViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.data.collectAsStateWithLifecycle()
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
            item {
                HeroStatsRow(
                    chartId = "market_hero",
                    navController = navController,
                    stats = uiState.heroStats
                )
            }
            item {
                DashCard(
                    chartId = "stock_treemap",
                    navController = navController,
                    title = "NIFTY 500 MARKET CAP MATRIX"
                ) {
                    TreemapChart(
                        weights = emptyList(),
                        brushes = emptyList(),
                        modifier = Modifier.height(350.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "stock_sector_returns",
                    navController = navController,
                    title = "1-DAY SECTORAL PERFORMANCE (%)"
                ) {
                    GradientBarChart(
                        data = emptyList(),
                        labels = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "stock_fii_mirror",
                    navController = navController,
                    title = "FII vs DII DAILY BUYING (₹ Cr)"
                ) {
                    MirrorBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "stock_vix_wave",
                    navController = navController,
                    title = "INDIA VIX VOLATILITY DYNAMICS"
                ) {
                    WaveformChart(
                        modifier = Modifier.height(160.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "stock_nifty_candle",
                    navController = navController,
                    title = "NIFTY 50 OHLCV ANALYSIS"
                ) {
                    CandlestickChart(
                        data = emptyList(),
                        modifier = Modifier.height(200.dp)
                    )
                }
            }

            item {
                DashCard(
                    chartId = "stock_pe_scatter",
                    navController = navController,
                    title = "VALUATION RADAR (PE vs EPS)"
                ) {
                    ScatterWithTrendLine(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                TwoColumnRow {
                    DashCard(
                        chartId = "stock_pcr_gauge",
                        navController = navController,
                        title = "PUT-CALL RATIO", 
                        modifier = Modifier.weight(1f)
                    ) {
                        HalfDonutGauge(value = 1.05f, max = 3f, label = "PCR", modifier = Modifier.height(180.dp))
                    }
                    DashCard(
                        chartId = "stock_breadth_donut",
                        navController = navController,
                        title = "MKT BREADTH", 
                        modifier = Modifier.weight(1f)
                    ) {
                        GradientDonutChart(
                            values = emptyList(),
                            brushes = emptyList(),
                            label = "ADV/DEC",
                            modifier = Modifier.height(180.dp)
                        )
                    }
                }
            }

            item {
                DashCard(
                    chartId = "stock_fii_waterfall",
                    navController = navController,
                    title = "CUMULATIVE FII FLOW (YTD)"
                ) {
                    WaterfallBarChart(
                        data = emptyList(),
                        modifier = Modifier.height(220.dp)
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("DELIVERY TRENDS (TOP STOCKS)", style = SciFiTheme.typography.SectionHead, color = extendedColors.primary)
                    MiniSparklineCard(label = "RELIANCE", value = "42%", data = emptyList())
                    MiniSparklineCard(label = "HDFCBANK", value = "58%", data = emptyList())
                }
            }

            item {
                DashCard(
                    chartId = "stock_alpha_bubbles",
                    navController = navController,
                    title = "ALPHA OPPORTUNITY MAP"
                ) {
                    BubbleScatterChart(
                        data = emptyList(),
                        modifier = Modifier.height(240.dp)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
