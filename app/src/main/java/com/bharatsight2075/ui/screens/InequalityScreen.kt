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
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InequalityViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class InequalityData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(InequalityData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = InequalityData(heroStats = listOf("Gini" to "0.357", "Top 1%" to "22%", "Bottom 50%" to "3%"))
    }}
}

@Composable
fun InequalityScreen(navController: NavController) {
    val vm: InequalityViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFE040FB)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Wealth & Inequality",
                    badge = "GINI",
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
            item { HeroStatsRow(chartId = "inequality_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "inequality_lorenz_area", navController, "Lorenz Curve (Wealth Dist)") {
                    GradientAreaChart(data = emptyList(), strokeColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "inequality_consumption_mirror", navController, "Urban vs Rural Consumption") {
                    MirrorBarChart(data = emptyList())
                }
            }

            item {
                DashCard(chartId = "inequality_poverty_spiral", navController, "Poverty Elimination Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "inequality_safety_net_liquid", navController, "Social Safety Net Coverage") {
                    LiquidFillGauge(percent = 0.72f, primaryColor = Color(0xFF00E676))
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
