package com.bharatsight2075.ui.visualization

import androidx.compose.ui.graphics.Color
import com.bharatsight2075.ui.screens.Routes

data class ChartMeta(
    val chartId: String,
    val title: String,
    val badge: String? = null,
    val badgeColor: Color = Color(0xFF00F5FF),
    val dataSource: String = "RBI / NSE / World Bank",
    val insights: List<String> = emptyList(),
    val relatedChartIds: List<String> = emptyList(),
    val chartType: ChartType,
    val sectionRoute: String
)

object ChartRegistry {
    private val registry = mutableMapOf<String, ChartMeta>()

    fun register(meta: ChartMeta) { registry[meta.chartId] = meta }
    
    fun get(chartId: String): ChartMeta? {
        return registry[chartId] ?: autoGenerate(chartId)
    }
    
    fun getRelated(chartId: String): List<ChartMeta> =
        registry[chartId]?.relatedChartIds?.mapNotNull { registry[it] } ?: emptyList()

    private fun autoGenerate(chartId: String): ChartMeta {
        val parts  = chartId.split("_")
        val section = parts.firstOrNull() ?: "unknown"
        val name   = parts.drop(1).joinToString(" ") { it.replaceFirstChar(Char::uppercase) }
        val type   = when {
            chartId.contains("area") || chartId.contains("projection") || chartId.contains("trend") -> ChartType.AREA
            chartId.contains("bar")  || chartId.contains("growth")    || chartId.contains("race")   -> ChartType.BAR
            chartId.contains("donut")|| chartId.contains("composition")|| chartId.contains("sector")-> ChartType.DONUT
            chartId.contains("gauge")|| chartId.contains("speed")     || chartId.contains("rate")   -> ChartType.GAUGE
            chartId.contains("radar")|| chartId.contains("spider")    || chartId.contains("index")  -> ChartType.RADAR
            chartId.contains("heat") || chartId.contains("matrix")    || chartId.contains("map")    -> ChartType.HEATMAP
            chartId.contains("line") || chartId.contains("multi")     || chartId.contains("compare")-> ChartType.LINE
            chartId.contains("candle")|| chartId.contains("ohlc")                                    -> ChartType.CANDLE
            chartId.contains("sankey")|| chartId.contains("flow")     || chartId.contains("river")  -> ChartType.SANKEY
            chartId.contains("bubble")|| chartId.contains("scatter")                                 -> ChartType.BUBBLE
            chartId.contains("ring") || chartId.contains("cluster")   || chartId.contains("orbital")-> ChartType.RING
            chartId.contains("wave") || chartId.contains("waveform")                                 -> ChartType.WAVE
            chartId.contains("waterfall")                                                             -> ChartType.WATERFALL
            chartId.contains("polar")|| chartId.contains("pie")                                      -> ChartType.POLAR
            chartId.contains("treemap")                                                               -> ChartType.TREEMAP
            chartId.contains("timeline")|| chartId.contains("milestone")                             -> ChartType.TIMELINE
            chartId.contains("liquid")|| chartId.contains("fill")     || chartId.contains("gauge")  -> ChartType.LIQUID
            else -> ChartType.AREA
        }
        return ChartMeta(
            chartId     = chartId,
            title       = name.ifEmpty { chartId },
            badge       = null,
            badgeColor  = Color(0xFF00F5FF),
            dataSource  = "India Economic Data / MockData",
            insights    = listOf(
                "$name shows significant economic activity in this period",
                "Peak value is ${ChartMockData.generateMockFor(type).maxOrNull()?.let{"%.1f".format(it)} ?: "N/A"}",
                "Source: Reserve Bank of India · NSE · World Bank · MockData"
            ),
            relatedChartIds = emptyList(),
            chartType   = type,
            sectionRoute= "/${section}_overview"
        )
    }

    private fun registerSectionCharts(sectionPrefix: String, sectionName: String, route: String) {
        register(ChartMeta("${sectionPrefix}_hero", "$sectionName KPIs", "LIVE", Color(0xFF00F5FF), "RBI / Govt Source",
            listOf("Primary performance indicator", "Baseline growth analysis", "Strategic trajectory"), 
            listOf("${sectionPrefix}_chart1", "${sectionPrefix}_chart2"), ChartType.NUMBER_TICKER, route))
        
        register(ChartMeta("${sectionPrefix}_chart1", "Primary Chart", null, Color(0xFF00F5FF), "RBI / NSE / DataHub",
            listOf("Core trend observation", "Momentum shifting upward", "Historical peak alignment"), 
            listOf("${sectionPrefix}_chart2", "${sectionPrefix}_chart3"), ChartType.AREA, route))
        
        register(ChartMeta("${sectionPrefix}_chart2", "Distribution", null, Color(0xFF00F5FF), "Economic Census",
            listOf("Compositional analysis", "Dominant segment identified", "Structural balance check"), 
            listOf("${sectionPrefix}_chart1", "${sectionPrefix}_chart3"), ChartType.DONUT, route))
        
        register(ChartMeta("${sectionPrefix}_chart3", "Comparison", null, Color(0xFF00F5FF), "Market Benchmarks",
            listOf("Relative performance", "Outperforming peer group", "Variance analysis active"), 
            listOf("${sectionPrefix}_chart2", "${sectionPrefix}_chart4"), ChartType.BAR, route))
        
        register(ChartMeta("${sectionPrefix}_chart4", "Trend Analysis", null, Color(0xFF00F5FF), "Analytical Engine",
            listOf("Longitudinal pattern", "Cyclical fluctuation noted", "Projected stabilization"), 
            listOf("${sectionPrefix}_chart3", "${sectionPrefix}_chart5"), ChartType.MULTI_LINE, route))
        
        register(ChartMeta("${sectionPrefix}_chart5", "Performance", null, Color(0xFF00F5FF), "KPI Tracker",
            listOf("Efficiency metric", "Target achievement 88%", "Optimization required"), 
            listOf("${sectionPrefix}_chart4", "${sectionPrefix}_chart1"), ChartType.GAUGE_SPEEDO, route))
    }

    init {
        // Sections defined in Routes
        registerSectionCharts("macro", "Macro", Routes.MACRO_OVERVIEW)
        registerSectionCharts("agriculture", "Agriculture", Routes.AGRICULTURE)
        registerSectionCharts("banking", "Banking", Routes.BANKING)
        registerSectionCharts("energy", "Energy", Routes.ENERGY)
        registerSectionCharts("smart_cities", "Smart Cities", Routes.SMART_CITIES)
        registerSectionCharts("startup", "Startup", Routes.STARTUPS)
        registerSectionCharts("defence", "Defence", Routes.DEFENCE)
        registerSectionCharts("climate", "Climate", Routes.CLIMATE)
        registerSectionCharts("digital", "Digital Economy", Routes.DIGITAL_ECONOMY)
        registerSectionCharts("education", "Education", Routes.EDUCATION)
        registerSectionCharts("healthcare", "Healthcare", Routes.HEALTHCARE)
        registerSectionCharts("real_estate", "Real Estate", Routes.REAL_ESTATE)
        registerSectionCharts("tourism", "Tourism", Routes.TOURISM)
        registerSectionCharts("inequality", "Inequality", Routes.INEQUALITY)
        registerSectionCharts("labour", "Labour", Routes.LABOUR)
        registerSectionCharts("logistics", "Logistics", Routes.LOGISTICS)
        registerSectionCharts("media", "Media", Routes.MEDIA)
        registerSectionCharts("nat_res", "Natural Resources", Routes.NATURAL_RESOURCES)
        registerSectionCharts("geo_risk", "Geo Risk", Routes.GEO_RISK)
        registerSectionCharts("space_tech", "Space Tech", Routes.SPACE_TECH)
        registerSectionCharts("soft_power", "Soft Power", Routes.SOFT_POWER)
        
        // Manual specific overrides or additional charts
        register(ChartMeta("macro_gdp_hero", "Estimated 2075 GDP", "LIVE", Color(0xFF00F5FF), "World Bank / RBI",
            listOf("GDP projected at $37T by 2075", "8.1% YoY growth rate"), emptyList(), ChartType.NUMBER_TICKER, Routes.MACRO_OVERVIEW))

        // Ensure legacy IDs from sections also work
        listOf("agri_msp_trend", "agri_export_polar", "agri_income_mirror", "agri_soil_radar", "agri_spiral_timeline", "agri_orbital_insurance",
               "banking_npa_trend", "banking_portfolio_orbital", "banking_recovery_waterfall", "banking_digital_spiral"
        ).forEach { id ->
            register(ChartMeta(id, id.replace("_", " ").uppercase(), null, Color.Cyan, "Govt Sources", listOf("Detailed analytical view", "High fidelity simulation"), emptyList(), ChartType.AREA, Routes.HOME))
        }
    }
}
