package com.bharatsight2075.ui.visualization

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.bharatsight2075.ui.components.HeroStat
import com.bharatsight2075.ui.visualization.charts.CandleData
import com.bharatsight2075.ui.visualization.charts.RingData
import com.bharatsight2075.ui.visualization.charts.TimelineEvent
import kotlin.random.Random

data class SectionDefaultData(val heroStats: List<HeroStat> = emptyList())

object MockData {
    fun generateHeroStats(section: String): List<HeroStat> {
        return when(section) {
            "macro_overview" -> macroHeroStats
            "forecaster" -> forecasterHeroStats
            "india_globe" -> globeHeroStats
            "macro_hub" -> hubHeroStats
            "state_dive" -> stateHeroStats
            "stock_heatmap" -> marketHeroStats
            "trade_network" -> tradeHeroStats
            "sector_dive" -> sectorHeroStats
            "demographics" -> demographicsHeroStats
            "maps" -> mapsHeroStats
            "agriculture" -> agricultureHeroStats
            "banking" -> bankingHeroStats
            "energy" -> energyHeroStats
            "smart_cities" -> smartCitiesHeroStats
            "startups" -> startupHeroStats
            "defence" -> defenceHeroStats
            "climate" -> climateHeroStats
            "digital_economy" -> digitalHeroStats
            "education" -> educationHeroStats
            "healthcare" -> healthcareHeroStats
            "real_estate" -> realEstateHeroStats
            "tourism" -> tourismHeroStats
            "space_tech" -> spaceTechHeroStats
            "geo_risk" -> geoRiskHeroStats
            "inequality" -> inequalityHeroStats
            "labour" -> labourHeroStats
            "logistics" -> logisticsHeroStats
            "media" -> mediaHeroStats
            "natural_resources" -> naturalResourcesHeroStats
            "soft_power" -> softPowerHeroStats
            else -> listOf(HeroStat("Metric", "42"), HeroStat("Index", "88%"), HeroStat("Delta", "▲ 5.2%"))
        }
    }

    val macroHeroStats = listOf(HeroStat("GDP", "$37T"), HeroStat("Inflation", "4.2%"), HeroStat("Growth", "8.1%"))
    val forecasterHeroStats = listOf(HeroStat("Scenarios", "4"), HeroStat("Horizon", "50yr"), HeroStat("Model", "TFLite"))
    val globeHeroStats = listOf(HeroStat("States", "36"), HeroStat("TradeArcs", "142"), HeroStat("FDI", "Live"))
    val hubHeroStats = listOf(HeroStat("Indicators", "12"), HeroStat("Alerts", "3"), HeroStat("Anomalies", "1"))
    val stateHeroStats = listOf(HeroStat("States", "36"), HeroStat("UTs", "8"), HeroStat("DataYears", "2000-35"))
    val marketHeroStats = listOf(HeroStat("Stocks", "500"), HeroStat("Sectors", "12"), HeroStat("Refresh", "5s"))
    val tradeHeroStats = listOf(HeroStat("Partners", "30"), HeroStat("TradeVol", "$1.2T"), HeroStat("Surplus", "8"))
    val sectorHeroStats = listOf(HeroStat("Sectors", "8"), HeroStat("KPIs", "850+"), HeroStat("Coverage", "100%"))
    val demographicsHeroStats = listOf(HeroStat("Population", "1.4B"), HeroStat("HDI", "0.644"), HeroStat("GrowthRate", "0.9%"))
    val mapsHeroStats = listOf(HeroStat("Styles", "30"), HeroStat("Districts", "766"), HeroStat("Layers", "12"))
    val agricultureHeroStats = listOf(HeroStat("Crops", "23"), HeroStat("States", "28"), HeroStat("MSP", "₹2183"))
    val bankingHeroStats = listOf(HeroStat("Banks", "156"), HeroStat("NPA", "3.9%"), HeroStat("CRAR", "16.8%"))
    val energyHeroStats = listOf(HeroStat("Capacity", "950GW"), HeroStat("Renewable", "46%"), HeroStat("Deficit", "0.4%"))
    val smartCitiesHeroStats = listOf(HeroStat("Cities", "100"), HeroStat("NHkm", "1.46L"), HeroStat("MetroKm", "945"))
    val startupHeroStats = listOf(HeroStat("Startups", "1.2L"), HeroStat("Unicorns", "113"), HeroStat("Funding", "$42B"))
    val defenceHeroStats = listOf(HeroStat("Budget", "₹6.2LCr"), HeroStat("Export", "$2.5B"), HeroStat("R&D", "25%"))
    val climateHeroStats = listOf(HeroStat("CO2", "2.8GT"), HeroStat("Forest", "24%"), HeroStat("NDC", "45%int"))
    val digitalHeroStats = listOf(HeroStat("UPITxn", "14B/mo"), HeroStat("Internet", "850M"), HeroStat("eComm", "$120B"))
    val educationHeroStats = listOf(HeroStat("GER", "28.4%"), HeroStat("Skilled", "51M/yr"), HeroStat("Inst", "56K+"))
    val healthcareHeroStats = listOf(HeroStat("Pharma", "$50B"), HeroStat("Hospitals", "71K"), HeroStat("ABDM", "540M"))
    val realEstateHeroStats = listOf(HeroStat("Index", "₹7200/sqft"), HeroStat("Launches", "4.35L"), HeroStat("Unsold", "6.5L"))
    val tourismHeroStats = listOf(HeroStat("Foreign", "9.2M"), HeroStat("Domestic", "2.5B"), HeroStat("Forex", "$28B"))
    val spaceTechHeroStats = listOf(HeroStat("Launches", "48/yr"), HeroStat("Startups", "180+"), HeroStat("Budget", "₹13KCr"))
    val geoRiskHeroStats = listOf(HeroStat("RiskScore", "42/100"), HeroStat("Borders", "7"), HeroStat("Conflicts", "3"))
    val inequalityHeroStats = listOf(HeroStat("Gini", "0.357"), HeroStat("Top1%", "22%"), HeroStat("Bottom50%", "3%"))
    val labourHeroStats = listOf(HeroStat("LFPRate", "42.6%"), HeroStat("Formal", "21%"), HeroStat("WageGrowth", "7.2%"))
    val logisticsHeroStats = listOf(HeroStat("LPI", "3.44/5"), HeroStat("ColdChain", "₹2100Cr"), HeroStat("PortTEU", "12.3M"))
    val mediaHeroStats = listOf(HeroStat("OTTSubs", "550M"), HeroStat("BoxOffice", "₹12KCr"), HeroStat("AdSpend", "₹1.1LCr"))
    val naturalResourcesHeroStats = listOf(HeroStat("WaterStress", "High"), HeroStat("Minerals", "95types"), HeroStat("Fisheries", "$7B"))
    val softPowerHeroStats = listOf(HeroStat("BrandValue", "$2.6T"), HeroStat("Diaspora", "32M"), HeroStat("Yoga", "300Mpractitioners"))
}

object ChartMockData {
    fun generateMockFor(type: ChartType): List<Float> {
        return when(type) {
            ChartType.AREA, ChartType.LINE, ChartType.STACKED, ChartType.STACKED_AREA, ChartType.WAVE, ChartType.WAVEFORM -> 
                List(12) { Random.nextFloat() * 35.5f + 1.5f }
            ChartType.BAR, ChartType.WATERFALL -> 
                List(12) { Random.nextFloat() * 14.5f - 2.5f }
            ChartType.DONUT, ChartType.POLAR, ChartType.POLAR_AREA, ChartType.TREEMAP -> 
                List(8) { Random.nextFloat() * 50f + 10f }
            ChartType.GAUGE, ChartType.GAUGE_HALF, ChartType.GAUGE_SPEEDO, ChartType.LIQUID, ChartType.LIQUID_GAUGE, ChartType.HALF_DONUT -> 
                listOf(Random.nextFloat() * 100f)
            ChartType.RADAR -> List(8) { Random.nextFloat() * 100f }
            ChartType.HEATMAP -> List(36) { Random.nextFloat() * 100f }
            ChartType.CANDLE -> List(40) { Random.nextFloat() * 100f } // 10 candles * 4 values
            ChartType.SANKEY, ChartType.BUBBLE, ChartType.RING, ChartType.RING_CLUSTER, ChartType.TIMELINE -> 
                List(12) { Random.nextFloat() * 100f }
            else -> List(12) { index -> 20f + index * 6.5f }
        }
    }

    fun forType(chartType: ChartType): Any {
        val mock = generateMockFor(chartType)
        return if (chartType == ChartType.AREA || chartType == ChartType.BAR || chartType == ChartType.RADAR) {
            mock
        } else {
            mock.firstOrNull() ?: 0f
        }
    }

    fun generateMockData(chartType: ChartType): List<Any> {
        return when(chartType) {
            ChartType.AREA, ChartType.LINE -> List(12) { Random.nextFloat() * 35.5f + 1.5f }
            ChartType.BAR -> List(12) { Random.nextFloat() * 14.5f - 2.5f }
            ChartType.MULTI_LINE -> listOf(
                List(12) { Random.nextFloat() * 11.5f + 0.5f },
                List(12) { Random.nextFloat() * 11.5f + 0.5f }
            )
            ChartType.CANDLE -> {
                var current = Random.nextFloat() * 75000f + 15000f
                List(12) {
                    val open = current
                    val close = open + (Random.nextFloat() * 2000f - 1000f)
                    val high = maxOf(open, close) + Random.nextFloat() * 500f
                    val low = minOf(open, close) - Random.nextFloat() * 500f
                    current = close
                    CandleData(high, low, open, close)
                }
            }
            ChartType.RADAR -> List(6) { Random.nextFloat() * 0.8f + 0.2f }
            ChartType.SCATTER, ChartType.SCATTER_TREND -> List(20) { Offset(Random.nextFloat(), Random.nextFloat()) }
            ChartType.BUBBLE -> List(12) { Triple(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 0.15f + 0.05f) }
            ChartType.HEATMAP -> List(6) { List(6) { Random.nextFloat() } }
            ChartType.TREEMAP -> listOf(100.0, 80.0, 60.0, 40.0, 20.0, 20.0, 10.0)
            ChartType.WATERFALL -> listOf(40f, 20f, -30f, 50f, -10f, 25f)
            ChartType.RING_CLUSTER, ChartType.RING_ORBITAL -> List(3) { RingData(Random.nextFloat() * 0.8f + 0.1f, "Metric", Color.Cyan) }
            ChartType.TIMELINE, ChartType.SPIRAL_TIMELINE -> List(4) { TimelineEvent("20${25 + it * 5}", "Milestone $it") }
            else -> List(12) { (it + 1) * 7f + 10f }
        }
    }
}
