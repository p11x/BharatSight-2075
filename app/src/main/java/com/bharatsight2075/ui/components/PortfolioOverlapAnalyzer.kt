package com.bharatsight2075.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bharatsight2075.ui.theme.SciFiTheme // Changed theme import if needed, or keep RetroDarkColors if it exists
import com.bharatsight2075.ui.visualization.charts.RadarDataSet
import com.bharatsight2075.ui.visualization.charts.RadarPolygonChart

@Immutable
data class PortfolioSector(
    val name: String,
    val allocation: Float,
    val macroOutlook: Float // 0..1
)

@Composable
fun PortfolioOverlapAnalyzer(portfolio: List<PortfolioSector>) {
    val neonCyan = Color(0xFF00F5FF)
    val neonMagenta = Color(0xFFFF00FF)
    val neonOrange = Color(0xFFFF6B35)
    val neonGreen = Color(0xFF00E676)

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            "PORTFOLIO MACRO ALIGNMENT",
            color = neonCyan,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        RadarPolygonChart(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            labels = portfolio.map { it.name },
            dataSets = listOf(
                RadarDataSet(
                    label = "YOUR ALLOCATION",
                    values = portfolio.map { it.allocation },
                    color = neonCyan
                ),
                RadarDataSet(
                    label = "MACRO FORECAST",
                    values = portfolio.map { it.macroOutlook },
                    color = neonMagenta
                )
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        val totalExposure = portfolio.sumOf { (it.allocation * (1 - it.macroOutlook)).toDouble() }
        Text(
            text = "RISK EXPOSURE: ${(totalExposure * 100).toInt()}%",
            color = if (totalExposure > 0.3) neonOrange else neonGreen,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}
