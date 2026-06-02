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
class MediaViewModel @Inject constructor(repo: EconomicRepository) : ViewModel() {
    data class MediaData(val heroStats: List<Pair<String, String>> = emptyList())
    private val _data = MutableStateFlow(MediaData())
    val sectionData = _data.asStateFlow()
    init { viewModelScope.launch(Dispatchers.IO) {
        _data.value = MediaData(heroStats = listOf("OTT Subs" to "550M", "Box Office" to "₹12K Cr", "Ad Spend" to "₹1.1L Cr"))
    }}
}

@Composable
fun MediaScreen(navController: NavController) {
    val vm: MediaViewModel = hiltViewModel()
    val data by vm.sectionData.collectAsStateWithLifecycle()
    val primaryColor = Color(0xFFFF4081)

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Media & Entertainment",
                    badge = "OTT",
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
            item { HeroStatsRow(chartId = "media_hero", navController, stats = data.heroStats) }

            item {
                DashCard(chartId = "media_content_wave", navController, "Digital Consumption Dynamics") {
                    WaveformChart(brush = Brush.verticalGradient(listOf(primaryColor, Color.Transparent)))
                }
            }

            item {
                DashCard(chartId = "media_podcast_spiral", navController, "Podcast Growth Timeline") {
                    SpiralTimelineChart(events = emptyList(), primaryColor = primaryColor)
                }
            }

            item {
                TwoColumnRow {
                    DashCard(chartId = "media_streaming_mirror", navController, "Streaming vs Linear", modifier = Modifier.weight(1f)) {
                        MirrorBarChart(data = emptyList())
                    }
                    DashCard(chartId = "media_genre_radar", navController, "Content Affinity", modifier = Modifier.weight(1f)) {
                        RadarPolygonChart(data = emptyList(), labels = emptyList())
                    }
                }
            }

            item {
                DashCard(chartId = "media_ipl_orbital", navController, "IPL Economic Multiplier") {
                    OrbitalDonutSystem(rings = emptyList())
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}
