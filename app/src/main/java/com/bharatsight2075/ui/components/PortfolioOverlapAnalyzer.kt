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
import com.bharatsight2075.ui.theme.RetroDarkColors
import com.bharatsight2075.ui.visualization.radar.RadarDataSet
import com.bharatsight2075.ui.visualization.radar.RadarSpiderChart

@Immutable
data class PortfolioSector(
    val name: String,
    val allocation: Float,
    val macroOutlook: Float // 0..1
)

@Composable
fun PortfolioOverlapAnalyzer(portfolio: List<PortfolioSector>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            "PORTFOLIO MACRO ALIGNMENT",
            color = RetroDarkColors.NeonCyan,
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        RadarSpiderChart(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            labels = portfolio.map { it.name },
            dataSets = listOf(
                RadarDataSet(
                    values = portfolio.map { it.allocation },
                    maxValues = List(portfolio.size) { 1f },
                    label = "YOUR ALLOCATION",
                    color = RetroDarkColors.NeonCyan
                ),
                RadarDataSet(
                    values = portfolio.map { it.macroOutlook },
                    maxValues = List(portfolio.size) { 1f },
                    label = "MACRO FORECAST",
                    color = RetroDarkColors.NeonMagenta
                )
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        val totalExposure = portfolio.sumOf { (it.allocation * (1 - it.macroOutlook)).toDouble() }
        Text(
            text = "RISK EXPOSURE: ${(totalExposure * 100).toInt()}%",
            color = if (totalExposure > 0.3) RetroDarkColors.NeonOrange else RetroDarkColors.NeonGreen,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}
