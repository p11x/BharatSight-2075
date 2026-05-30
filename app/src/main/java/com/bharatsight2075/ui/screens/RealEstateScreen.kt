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
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.charts.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class RealEstateData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(RealEstateData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = RealEstateData(heroStats = listOf("Index" to "₹7,200", "Launches" to "4.35L", "Unsold" to "6.5L"))
    }}
}

@Composable
fun RealEstateScreen(navController: NavController) {
    val vm: RealEstateViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFF8A65)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Real Estate",
                    badge = "RERA",
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
            item { HeroStatsRow(chartId = "realestate_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "realestate_housing_liquid", navController, "Affordable Housing Progress") {
                    LiquidFillGauge(percent = 0.65f, primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "realestate_inventory_race", navController, "Inventory Trends (State-wise)") {
                    GradientBarChart(data = emptyList(), labels = emptyList())
                }
            }

            item {
                DashCard(chartId = "realestate_permit_spiral", navController, "Project Permit Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                DashCard(chartId = "realestate_reits_orbital", navController, "REITs Market Depth") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
