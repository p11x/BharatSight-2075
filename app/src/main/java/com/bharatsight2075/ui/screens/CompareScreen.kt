package com.bharatsight2075.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bharatsight2075.ui.components.BharatSightTopBar
import com.bharatsight2075.ui.components.TopBarMode
import com.bharatsight2075.ui.theme.SciFiTheme
import com.bharatsight2075.ui.visualization.charts.*

@Composable
fun CompareScreen(
    navController: NavController,
    onBack: () -> Unit,
    viewModel: CompareViewModel = hiltViewModel()
) {
    val countries by viewModel.countries.collectAsStateWithLifecycle()
    val extendedColors = SciFiTheme.extendedColors
    val pagerState = rememberPagerState(pageCount = { 4 })

    Scaffold(
        topBar = {
            BharatSightTopBar(
                mode = TopBarMode.Section(
                    title = "Global Economy Compare",
                    badge = "2075 PROJ",
                    badgeColor = extendedColors.accent,
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
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 1. Horizontal Pager for Bar Charts
            item {
                Column {
                    Text(
                        text = when(pagerState.currentPage) {
                            0 -> "GDP PROJECTED ($ TRILLION)"
                            1 -> "ANNUAL GROWTH RATE (%)"
                            2 -> "INFLATION TARGET (%)"
                            else -> "HUMAN DEVELOPMENT INDEX (HDI)"
                        },
                        style = SciFiTheme.typography.SectionHead,
                        color = extendedColors.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .height(220.dp)
                            .border(1.dp, Color.White.copy(alpha = 0.1f))
                            .background(Color.Black.copy(alpha = 0.2f))
                    ) { page ->
                        val data = countries.map {
                            when(page) {
                                0 -> it.gdp
                                1 -> it.growth
                                2 -> it.inflation
                                else -> it.hdi * 100f // Scale for bar chart visibility
                            }
                        }
                        GradientBarChart(
                            data = data,
                            labels = countries.map { it.name },
                            modifier = Modifier.fillMaxSize().padding(16.dp)
                        )
                    }
                    
                    // Pager Indicator
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(4) { i ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(width = if (pagerState.currentPage == i) 16.dp else 6.dp, height = 4.dp)
                                    .background(if (pagerState.currentPage == i) extendedColors.primary else Color.Gray)
                            )
                        }
                    }
                }
            }

            // 2. Radar Comparison
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("STRUCTURAL COMPARISON", style = SciFiTheme.typography.SectionHead, color = extendedColors.accent)
                        Spacer(Modifier.height(16.dp))
                        RadarPolygonChart(
                            data = listOf(0.9f, 0.8f, 0.7f, 0.95f, 0.85f), // Composite mock
                            labels = listOf("AGRI", "IND", "SERV", "DIGIT", "R&D"),
                            modifier = Modifier.height(240.dp).fillMaxWidth()
                        )
                    }
                }
            }

            // 3. Metric Table
            item {
                MetricTable(countries, extendedColors)
            }

            item { Spacer(Modifier.height(100.dp)) }
        }
    }
}

@Composable
private fun MetricTable(countries: List<CountryMetric>, colors: SciFiTheme.SciFiColors) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White.copy(alpha = 0.1f))
            .background(Color.Black.copy(alpha = 0.2f))
    ) {
        // Header
        Row(
            modifier = Modifier.background(colors.primary.copy(alpha = 0.1f)).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("METRIC", modifier = Modifier.weight(1.5f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = colors.primary)
            countries.forEach { 
                Text(it.name.take(3), modifier = Modifier.weight(1f), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (it.isPrimary) colors.primary else Color.Gray)
            }
        }

        // Rows
        MetricRow("GDP ($ T)", countries.map { it.gdp.toString() }, colors)
        MetricRow("GROWTH %", countries.map { it.growth.toString() }, colors)
        MetricRow("INFLTN %", countries.map { it.inflation.toString() }, colors)
        MetricRow("HDI INDEX", countries.map { it.hdi.toString() }, colors)
    }
}

@Composable
private fun MetricRow(label: String, values: List<String>, colors: SciFiTheme.SciFiColors) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1.5f), fontSize = 10.sp, color = Color.LightGray)
        values.forEachIndexed { i, value ->
            val isIndia = i == 0 // Based on ViewModel list
            Text(
                text = value,
                modifier = Modifier.weight(1f),
                fontSize = 10.sp,
                color = if (isIndia) colors.primary else Color.White,
                fontWeight = if (isIndia) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
    HorizontalDivider(color = Color.White.copy(alpha = 0.05f))
}
