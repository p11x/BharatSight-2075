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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.bharatsight2075.data.repositories.EconomicRepository
import com.bharatsight2075.ui.components.*
import com.bharatsight2075.ui.theme.GradPalette
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankingViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class BankingData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(BankingData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = BankingData(heroStats = MockData.bankingHeroStats)
    }}
}

@Composable
fun BankingScreen(navController: NavController) {
    val vm: BankingViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFF00B0FF)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Banking & Finance",
                    badge = "RBI",
                    badgeColor = primaryColor,
                    onBackClick = { navController.popBackStack() }
                )
            )
        },
        containerColor = Color(0xFF080810)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { HeroStatsRow(chartId = "banking_hero", navController, stats = data.heroStats) }

            item {
                TwoColumnRow {
                    DashCard(chartId = "banking_liquid_credit", navController, "Credit-to-GDP Pulse", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.62f, primaryColor = primaryColor)
                    }
                    DashCard(chartId = "banking_crar_gauge", navController, "CRAR Gauge", modifier = Modifier.weight(1f)) {
                        SpeedometerGauge(value = 16.8f, max = 25f, label = "CAPITAL ADQ")
                    }
                }
            }

            item {
                DashCard(chartId = "banking_npa_trend", navController, "Gross NPA Trend (%)") {
                    GradientAreaChart(data = emptyList(), strokeColor = Color(0xFFFF6B35))
                }
            }

            item {
                DashCard(chartId = "banking_portfolio_orbital", navController, "Loan Portfolio Rings") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item {
                DashCard(chartId = "banking_recovery_waterfall", navController, "IBC Resolution Waterfall") {
                    WaterfallBarChart(data = emptyList())
                }
            }

            item {
                DashCard(chartId = "banking_digital_spiral", navController, "Digital Payment Milestones") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
