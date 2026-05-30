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
class EducationViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class EducationData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(EducationData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = EducationData(heroStats = MockData.educationHeroStats)
    }}
}

@Composable
fun EducationScreen(navController: NavController) {
    val vm: EducationViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFFC107)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Education & Skill",
                    badge = "NEP 2020",
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
            item { HeroStatsRow(chartId = "education_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "education_literacy_spiral", navController, "Literacy Milestones") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "education_skill_radar", navController, "Skill Gap Radar", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                    DashCard(chartId = "education_skill_liquid", navController, "Skill Target", modifier = Modifier.weight(1f)) {
                        LiquidFillGauge(percent = 0.58f, primaryColor = primaryColor)
                    }
                }
            }

            item {
                DashCard(chartId = "education_rank_orbital", navController, "Global Institution Tiers") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item {
                DashCard(chartId = "education_spend_waterfall", navController, "Funding Composition") {
                    WaterfallBarChart(data = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
