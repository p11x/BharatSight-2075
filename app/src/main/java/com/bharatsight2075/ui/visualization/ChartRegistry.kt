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

    fun register(meta: ChartMeta) {
        registry[meta.chartId] = meta
    }

    fun get(chartId: String): ChartMeta? = registry[chartId]

    fun getRelated(chartId: String): List<ChartMeta> =
        registry[chartId]?.relatedChartIds?.mapNotNull { registry[it] } ?: emptyList()

    init {
        // --- SECTION 1: MACRO OVERVIEW ---
        register(ChartMeta("macro_gdp_hero", "Estimated 2075 GDP", "LIVE", Color(0xFF00F5FF), "World Bank / RBI",
            listOf("GDP projected at $37T by 2075", "8.1% YoY growth rate", "Services sector lead at 58% share"),
            listOf("macro_gdp_projection", "macro_sector_donut", "macro_growth_bar"), ChartType.NUMBER_TICKER, Routes.MACRO))
        
        register(ChartMeta("macro_gdp_projection", "GDP 50-Year Projection", null, Color(0xFF00F5FF), "IMF / World Bank",
            listOf("Linear growth 2025-2040", "Exponential phase 2040-2060", "Plateau risk if reforms stall"),
            listOf("macro_gdp_hero", "macro_trade_mirror"), ChartType.AREA, Routes.MACRO))

        register(ChartMeta("macro_indicator_rings", "Stability Rings", null, Color(0xFF00F5FF), "RBI",
            listOf("Banking sector resilient", "Capital buffers adequate", "Liquidity within norms"),
            listOf("macro_gdp_hero", "macro_hdi_gauge"), ChartType.RING_CLUSTER, Routes.MACRO))

        register(ChartMeta("macro_hdi_gauge", "HDI Gauge", null, Color(0xFF00F5FF), "NITI Aayog",
            listOf("India enters High HDI category", "Educational attainment rising", "Health outcomes improving"),
            listOf("macro_indicator_rings"), ChartType.GAUGE_HALF, Routes.MACRO))

        register(ChartMeta("macro_sector_donut", "Sector Composition", null, Color(0xFF00F5FF), "MOSPI",
            listOf("Services remains dominant", "Industrial growth accelerating", "Agri tech boosting yields"),
            listOf("macro_gdp_hero", "macro_growth_bar"), ChartType.DONUT, Routes.MACRO))

        register(ChartMeta("macro_growth_bar", "Historical Growth (2015-25)", null, Color(0xFF00F5FF), "RBI",
            listOf("Steady average growth of 7%+", "Post-pandemic rebound strong", "Consistent manufacturing gains"),
            listOf("macro_gdp_projection", "macro_sector_donut"), ChartType.BAR, Routes.MACRO))

        register(ChartMeta("macro_trade_mirror", "Trade Balance", null, Color(0xFF00F5FF), "Ministry of Commerce",
            listOf("Exports hit record highs", "Services surplus offsets goods deficit", "FDI inflows supporting balance"),
            listOf("macro_gdp_hero", "macro_indicator_rings"), ChartType.MIRROR_BAR, Routes.MACRO))

        register(ChartMeta("macro_inflation_multi", "Inflation Variance", null, Color(0xFF00F5FF), "MOSPI",
            listOf("Core inflation remains sticky", "Food prices showing seasonality", "CPI-WPI divergence narrowing"),
            listOf("macro_indicator_rings", "macro_repo_candles"), ChartType.MULTI_LINE, Routes.MACRO))

        register(ChartMeta("macro_unemployment_speedo", "Unemployment Rate", null, Color(0xFF00F5FF), "PLFS",
            listOf("Urban unemployment steady", "Job creation in GIG economy", "Skill gap remains a challenge"),
            listOf("macro_indicator_rings", "macro_recovery_bubbles"), ChartType.GAUGE_SPEEDO, Routes.MACRO))

        register(ChartMeta("macro_credit_wave", "Credit Wave", null, Color(0xFF00F5FF), "RBI",
            listOf("Credit to MSMEs increasing", "Housing loans driving retail growth", "NPA ratios at multi-year lows"),
            listOf("macro_money_stacked", "macro_repo_candles"), ChartType.WAVEFORM, Routes.MACRO))

        register(ChartMeta("macro_repo_candles", "Repo Rate History", null, Color(0xFF00F5FF), "RBI MPC",
            listOf("Interest rate cycle peaking", "MPC stance neutral", "Real interest rates positive"),
            listOf("macro_inflation_multi", "macro_credit_wave"), ChartType.CANDLE, Routes.MACRO))

        register(ChartMeta("macro_rank_heatmap", "Global Macro Ranking", null, Color(0xFF00F5FF), "IMF",
            listOf("India fastest growing economy", "Global competitiveness improving", "Logistics rank milestone"),
            listOf("macro_gdp_hero", "macro_trade_mirror"), ChartType.HEATMAP, Routes.MACRO))

        register(ChartMeta("macro_remit_sankey", "Remittance Source Flow", null, Color(0xFF00F5FF), "World Bank",
            listOf("$125B inflow projected", "UAE and USA major sources", "IT services lead invisibles"),
            listOf("macro_trade_mirror", "macro_sector_donut"), ChartType.SANKEY, Routes.MACRO))

        register(ChartMeta("macro_money_stacked", "M3 Money Supply", null, Color(0xFF00F5FF), "RBI",
            listOf("Broad money growth steady", "Digital payments replacing M1", "Deposit growth outpacing inflation"),
            listOf("macro_credit_wave", "macro_indicator_rings"), ChartType.STACKED_AREA, Routes.MACRO))

        register(ChartMeta("macro_recovery_bubbles", "Sector Recovery Dynamics", null, Color(0xFF00F5FF), "NITI Aayog",
            listOf("Tech and Finance lead recovery", "Hospitality showing strong gains", "Real estate on multi-year high"),
            listOf("macro_unemployment_speedo", "macro_sector_donut"), ChartType.BUBBLE, Routes.MACRO))

        register(ChartMeta("macro_fiscal_progress", "Fiscal Reform Progress", null, Color(0xFF00F5FF), "Ministry of Finance",
            listOf("Fiscal deficit target 4.5%", "Direct tax collection up 20%", "GST compliance at all-time high"),
            listOf("macro_repo_candles", "macro_indicator_rings"), ChartType.PROGRESS_HORIZONTAL, Routes.MACRO))

        register(ChartMeta("macro_sector_venn", "Sectoral Synergy Map", null, Color(0xFF00F5FF), "NITI Aayog",
            listOf("IT-Manufacturing integration", "Agri-Processing synergies", "E-commerce-Logistics link"),
            listOf("macro_sector_donut", "macro_recovery_bubbles"), ChartType.VENN, Routes.MACRO))

        register(ChartMeta("macro_milestones_timeline", "Macro Milestones", null, Color(0xFF00F5FF), "Govt of India",
            listOf("2024: Top 5 Global Economy", "2030: $10T Target", "2047: Developed India Goal"),
            listOf("macro_gdp_projection", "macro_hdi_gauge"), ChartType.TIMELINE, Routes.MACRO))


        // --- SECTION 2: FORECASTER ---
        register(ChartMeta("forecast_trajectory", "Live Scenario Projection", "AI", Color(0xFF7C4DFF), "TFLite / Gemini",
            listOf("Based on deep learning inference", "Includes policy-adjusted weights", "Real-time recalculation"),
            listOf("forecast_variance", "forecast_impact"), ChartType.MULTI_LINE, Routes.FORECASTER))

        register(ChartMeta("forecast_variance", "Statistical Variance", null, Color(0xFF7C4DFF), "Monte Carlo Engine",
            listOf("95% confidence interval", "Volatility risk assessed", "Scenario-weighted distribution"),
            listOf("forecast_trajectory"), ChartType.AREA, Routes.FORECASTER))

        register(ChartMeta("forecast_impact", "Policy Configuration Impact", null, Color(0xFF7C4DFF), "Policy Simulator",
            listOf("Tax rate elasticity calculated", "Infra multiplier effect: 2.5x", "Education ROI over 20 years"),
            listOf("forecast_trajectory", "forecast_waterfall"), ChartType.RADAR, Routes.FORECASTER))

        register(ChartMeta("forecast_waterfall", "Decadal GDP Delta", null, Color(0xFF7C4DFF), "Projection Engine",
            listOf("Projected $5T added by 2030", "$10T milestone in 2038", "Peak growth phase 2040-2055"),
            listOf("forecast_trajectory", "forecast_composition"), ChartType.WATERFALL, Routes.FORECASTER))

        register(ChartMeta("forecast_hdi", "HDI Speedo", null, Color(0xFF7C4DFF), "UN Projections",
            listOf("Projected parity with G20 avg", "Urban health outcomes lead", "Digital literacy at 98%"),
            listOf("forecast_gini"), ChartType.GAUGE_SPEEDO, Routes.FORECASTER))

        register(ChartMeta("forecast_gini", "Gini Donut", null, Color(0xFF7C4DFF), "World Bank / AI",
            listOf("Wealth distribution trends", "Middle class expanding to 70%", "Rural poverty elimination"),
            listOf("forecast_hdi"), ChartType.GAUGE_HALF, Routes.FORECASTER))

        register(ChartMeta("forecast_composition", "GDP Composition Shift", null, Color(0xFF7C4DFF), "Projection Engine",
            listOf("Manufacturing reaching 25% share", "Digital services dominance", "Energy transition costs integrated"),
            listOf("forecast_trajectory", "forecast_energy"), ChartType.STACKED_AREA, Routes.FORECASTER))

        register(ChartMeta("forecast_energy", "Energy Source Forecast", null, Color(0xFF7C4DFF), "MNRE / IEA",
            listOf("Renewables hit 80% share", "Solar and Wind primary drivers", "Nuclear providing baseline power"),
            listOf("forecast_composition", "forecast_urbanization"), ChartType.DONUT, Routes.FORECASTER))

        register(ChartMeta("forecast_urbanization", "Urbanization Saturation", null, Color(0xFF7C4DFF), "UN Habitat",
            listOf("600M new urban dwellers", "Smart city network fully active", "Urban-rural productivity gap closed"),
            listOf("forecast_energy", "forecast_export_polar"), ChartType.WAVEFORM, Routes.FORECASTER))

        register(ChartMeta("forecast_export_polar", "Export Category Dynamics", null, Color(0xFF7C4DFF), "Commerce Ministry",
            listOf("High-tech exports at 40%", "Global market share milestone", "Services trade surplus record"),
            listOf("forecast_urbanization", "forecast_income_bubbles"), ChartType.POLAR_AREA, Routes.FORECASTER))

        register(ChartMeta("forecast_income_bubbles", "Income vs Growth Density", null, Color(0xFF7C4DFF), "World Bank",
            listOf("Per capita income hits $15k", "Regional disparity reduced", "Consumption-led growth cycle"),
            listOf("forecast_export_polar", "forecast_preset_rings"), ChartType.BUBBLE, Routes.FORECASTER))

        register(ChartMeta("forecast_preset_rings", "Policy Preset Scorecard", null, Color(0xFF7C4DFF), "AI Simulator",
            listOf("Optimal policy mix identified", "Balanced growth vs welfare", "Fiscal sustainability focus"),
            listOf("forecast_income_bubbles", "forecast_milestones"), ChartType.RING_CLUSTER, Routes.FORECASTER))

        register(ChartMeta("forecast_milestones", "Projected Milestones", null, Color(0xFF7C4DFF), "NITI Aayog",
            listOf("2030: $10T GDP", "2047: Developed Nation", "2075: $37T Maturity"),
            listOf("forecast_preset_rings", "forecast_trajectory"), ChartType.TIMELINE, Routes.FORECASTER))

        register(ChartMeta("forecast_heatmap", "Scenario Attribute Matrix", null, Color(0xFF7C4DFF), "AI Multi-Agent",
            listOf("Sensitivity analysis complete", "Black swan risk assessment", "Parameter optimization"),
            listOf("forecast_trajectory", "forecast_impact"), ChartType.HEATMAP, Routes.FORECASTER))

        register(ChartMeta("forecast_stability", "Simulation Stability Index", null, Color(0xFF7C4DFF), "System Monitor",
            listOf("Convergence achieved in 12ms", "Error margin below 0.5%", "Model version: 2075.v4"),
            listOf("forecast_trajectory"), ChartType.PROGRESS_HORIZONTAL, Routes.FORECASTER))

        register(ChartMeta("forecast_synergy", "Global Trade Synergy", null, Color(0xFF7C4DFF), "Trade Engine",
            listOf("Cross-border integration peak", "Digital trade corridors active", "Supply chain resilience high"),
            listOf("forecast_export_polar"), ChartType.VENN, Routes.FORECASTER))

        register(ChartMeta("forecast_config", "Policy Configuration", null, Color(0xFF7C4DFF), "User Input",
            listOf("Interactive parameter tuning", "Real-time trajectory update", "Policy multiplier enabled"),
            listOf("forecast_impact"), ChartType.PROGRESS_HORIZONTAL, Routes.FORECASTER))


        // --- SECTION 3: INDIA 3D GLOBE ---
        register(ChartMeta("globe_map", "India Economic Map", "36 STATES", Color(0xFF00E676), "NITI Aayog",
            listOf("State-wise FDI tracking active", "GSDP heatmap updated", "Logistics corridors mapped"),
            listOf("globe_gsdp_ranking", "globe_trade_particles"), ChartType.HEATMAP, Routes.GLOBE))

        register(ChartMeta("globe_gsdp_ranking", "Top States by GSDP", null, Color(0xFF00E676), "MOSPI",
            listOf("Maharashtra leading at $500B+", "Tamil Nadu and Karnataka close", "UP joining the $1T race"),
            listOf("globe_fdi_flow", "globe_urban_mirror"), ChartType.BAR, Routes.GLOBE))

        register(ChartMeta("globe_fdi_flow", "FDI Source Sector Flow", null, Color(0xFF00E676), "DPIIT",
            listOf("Tech and Services lead FDI", "Green energy inflows up 40%", "Manufacturing FDI hubs emerging"),
            listOf("globe_gsdp_ranking", "globe_trade_particles"), ChartType.SANKEY, Routes.GLOBE))

        register(ChartMeta("globe_trade_particles", "Real-time Export Volume", null, Color(0xFF00E676), "Customs Data",
            listOf("Maritime trade leads volume", "Air cargo value density rising", "Port digitization impact"),
            listOf("globe_fdi_flow"), ChartType.PARTICLE_FLOW, Routes.GLOBE))

        register(ChartMeta("globe_growth_pop", "GSDP Growth vs Population", null, Color(0xFF00E676), "Census / MOSPI",
            listOf("Productivity gains in South", "Growth potential in North", "Demographic dividend peak"),
            listOf("globe_gsdp_ranking"), ChartType.SCATTER_TREND, Routes.GLOBE))

        register(ChartMeta("globe_regional_donut", "Regional GDP %", null, Color(0xFF00E676), "RBI",
            listOf("West and South lead contribution", "North industrializing fast", "East port-led growth"),
            listOf("globe_infra_radar"), ChartType.DONUT, Routes.GLOBE))

        register(ChartMeta("globe_infra_radar", "Infrastructure Radar", null, Color(0xFF00E676), "MoRTH / Railways",
            listOf("Expressway network maturity", "Dedicated freight corridors", "Aviation connectivity peak"),
            listOf("globe_regional_donut"), ChartType.RADAR, Routes.GLOBE))

        register(ChartMeta("globe_urban_mirror", "Urban vs Rural Share", null, Color(0xFF00E676), "NSSO",
            listOf("Urban contribution hitting 70%", "Rural productivity rising", "Secondary city growth hubs"),
            listOf("globe_gsdp_ranking"), ChartType.MIRROR_BAR, Routes.GLOBE))

        register(ChartMeta("globe_literacy_heatmap", "State Literacy & HDI", null, Color(0xFF00E676), "Ministry of Edu",
            listOf("Universal primary literacy goal", "Vocational skills focus", "Digital literacy at parity"),
            listOf("globe_infra_progress"), ChartType.HEATMAP, Routes.GLOBE))

        register(ChartMeta("globe_gsdp_waterfall", "Revenue Sources Waterfall", null, Color(0xFF00E676), "Fin Commission",
            listOf("GST collection by state", "Own-tax revenue increasing", "Central devolution status"),
            listOf("globe_gsdp_ranking"), ChartType.WATERFALL, Routes.GLOBE))

        register(ChartMeta("globe_pop_wave", "Population Density Wave", null, Color(0xFF00E676), "Census",
            listOf("Migration to tier-2 hubs", "Coastal density stable", "Fertility rate normalization"),
            listOf("globe_growth_pop"), ChartType.WAVEFORM, Routes.GLOBE))

        register(ChartMeta("globe_coastal_rings", "Coastal Trade Rings", null, Color(0xFF00E676), "Shipping Min",
            listOf("Port led development (Sagarmala)", "Maritime exports record", "Blue economy contribution"),
            listOf("globe_trade_particles"), ChartType.RING_CLUSTER, Routes.GLOBE))

        register(ChartMeta("globe_industry_polar", "State Industrial Polar", null, Color(0xFF00E676), "DPIIT",
            listOf("Manufacturing clusters active", "Service sector penetration", "Agri-processing hubs"),
            listOf("globe_gsdp_ranking"), ChartType.POLAR_AREA, Routes.GLOBE))

        register(ChartMeta("globe_formation_timeline", "Economic Reorg Timeline", null, Color(0xFF00E676), "Govt Records",
            listOf("State bifurcation impacts", "Special economic zones", "Ind-Industrial corridors"),
            listOf("globe_map"), ChartType.TIMELINE, Routes.GLOBE))

        register(ChartMeta("globe_gsdp_bubbles", "GSDP Concentration", null, Color(0xFF00E676), "NITI Aayog",
            listOf("Mega city contribution", "Agglomeration benefits", "De-centralization trends"),
            listOf("globe_map"), ChartType.BUBBLE, Routes.GLOBE))

        register(ChartMeta("globe_infra_progress", "Infrastructure Index", null, Color(0xFF00E676), "NHAI / PM GatiShakti",
            listOf("Multimodal connectivity", "Last-mile reach index", "Project completion rate"),
            listOf("globe_map"), ChartType.PROGRESS_HORIZONTAL, Routes.GLOBE))


        // --- SECTION 4: MACRO INDICATOR OBSERVATORY ---
        register(ChartMeta("observatory_cpi", "CPI Inflation Trend", null, Color(0xFF00E676), "MOSPI",
            listOf("Food inflation cooling", "Core inflation steady", "Target 4% on track"),
            listOf("observatory_wpi_cpi"), ChartType.AREA, Routes.OBSERVATORY))

        register(ChartMeta("observatory_wpi_cpi", "CPI vs WPI Divergence", null, Color(0xFF00E676), "Ministry of Finance",
            listOf("Input cost pressure low", "Consumer demand stable", "Supply chain efficiency"),
            listOf("observatory_cpi"), ChartType.MULTI_LINE, Routes.OBSERVATORY))

        register(ChartMeta("observatory_iip", "IIP Sectoral Growth", null, Color(0xFF00E676), "MOSPI",
            listOf("Manufacturing leads IIP", "Electricity generation up", "Mining output expansion"),
            listOf("observatory_cpi"), ChartType.BAR, Routes.OBSERVATORY))

        register(ChartMeta("observatory_pmi", "PMI Gauge", null, Color(0xFF00E676), "S&P Global",
            listOf("Manufacturing PMI at 58.5", "Services PMI strong at 60.1", "New order growth robust"),
            listOf("observatory_iip"), ChartType.GAUGE_SPEEDO, Routes.OBSERVATORY))

        register(ChartMeta("observatory_deficit", "Fiscal Deficit", null, Color(0xFF00E676), "RBI / Govt",
            listOf("Glide path to 4.5% adhered", "Revenue receipts up 15%", "Capital expenditure focus"),
            listOf("observatory_pmi"), ChartType.GAUGE_HALF, Routes.OBSERVATORY))

        register(ChartMeta("observatory_reserves", "FX Reserves", null, Color(0xFF00E676), "RBI",
            listOf("Import cover at 12 months", "Currency stability focus", "Diversified asset base"),
            listOf("observatory_cpi"), ChartType.NUMBER_TICKER, Routes.OBSERVATORY))

        register(ChartMeta("observatory_yield", "G-Sec Yield Curve", null, Color(0xFF00E676), "CCIL",
            listOf("Yields softening slightly", "Liquidity support pausing", "Bond inclusion inflows"),
            listOf("observatory_cpi"), ChartType.MULTI_LINE, Routes.OBSERVATORY))

        register(ChartMeta("observatory_anomalies", "Indicator Anomaly Matrix", null, Color(0xFF00E676), "AI Watchdog",
            listOf("No systemic risks detected", "Seasonal spikes analyzed", "Correlation drift low"),
            listOf("observatory_cpi"), ChartType.HEATMAP, Routes.OBSERVATORY))

        register(ChartMeta("observatory_liquidity", "Interbank Liquidity", null, Color(0xFF00E676), "RBI",
            listOf("System liquidity surplus", "LAF absorption active", "Call money rates stable"),
            listOf("observatory_yield"), ChartType.WAVEFORM, Routes.OBSERVATORY))

        register(ChartMeta("observatory_fdi", "FDI by Source Sector", null, Color(0xFF00E676), "DPIIT",
            listOf("Financial services top share", "Computer hardware boom", "Pharma FDI rising"),
            listOf("observatory_reserves"), ChartType.POLAR_AREA, Routes.OBSERVATORY))

        register(ChartMeta("observatory_alerts", "System Alerts Monitor", null, Color(0xFF00E676), "Control Center",
            listOf("3 active alerts (Low)", "No high severity warnings", "Simulation heartbeat healthy"),
            listOf("observatory_cpi"), ChartType.RING_CLUSTER, Routes.OBSERVATORY))


        // --- SECTION 5: STATE COMPARISON ---
        register(ChartMeta("state_gsdp_ranking", "GSDP Comparative Ranking", null, Color(0xFFB39DDB), "MOSPI",
            listOf("Regional growth hubs", "Diversified state economies", "Contribution milestones"),
            listOf("state_growth_scatter"), ChartType.BAR, Routes.STATES))

        register(ChartMeta("state_growth_scatter", "Growth vs Base Economy", null, Color(0xFFB39DDB), "NITI Aayog",
            listOf("High growth in low base states", "Stability in large economies", "Productivity convergence"),
            listOf("state_gsdp_ranking"), ChartType.SCATTER_TREND, Routes.STATES))

        register(ChartMeta("state_hdi_matrix", "State HDI Parameters", null, Color(0xFFB39DDB), "UNDP India",
            listOf("Social infra benchmarking", "Health & Education metrics", "Life quality index"),
            listOf("state_gsdp_ranking"), ChartType.HEATMAP, Routes.STATES))

        register(ChartMeta("state_per_capita", "Per-capita Distribution", null, Color(0xFFB39DDB), "MOSPI",
            listOf("Wealth distribution analysis", "Disposable income rising", "Quintile share tracking"),
            listOf("state_gsdp_ranking"), ChartType.DONUT, Routes.STATES))

        register(ChartMeta("state_radar", "State vs National Avg", null, Color(0xFFB39DDB), "Benchmarking",
            listOf("8-axis performance view", "Competitiveness analysis", "Target vs Actual gaps"),
            listOf("state_gsdp_ranking"), ChartType.RADAR, Routes.STATES))

        register(ChartMeta("state_emp_polar", "Sectoral Employment", null, Color(0xFFB39DDB), "PLFS",
            listOf("Workforce shift to services", "Agri-labor absorption", "Industrial job creation"),
            listOf("state_gsdp_ranking"), ChartType.POLAR_AREA, Routes.STATES))

        register(ChartMeta("state_urban_mirror", "Urban vs Rural GSDP", null, Color(0xFFB39DDB), "MOSPI",
            listOf("Urban-rural divide narrowing", "Rural digital economy", "Tier-2 city contribution"),
            listOf("state_gsdp_ranking"), ChartType.MIRROR_BAR, Routes.STATES))

        register(ChartMeta("state_revenue_waterfall", "Revenue & Expenditure", null, Color(0xFFB39DDB), "RBI State Fin",
            listOf("Fiscal health assessment", "Capital vs Revenue spend", "Grant dependency trend"),
            listOf("state_gsdp_ranking"), ChartType.WATERFALL, Routes.STATES))

        register(ChartMeta("state_infra_wave", "Infrastructure Saturation", null, Color(0xFFB39DDB), "PM GatiShakti",
            listOf("Physical vs Digital infra", "Connectivity reach index", "Project delay impact"),
            listOf("state_gsdp_ranking"), ChartType.WAVEFORM, Routes.STATES))

        register(ChartMeta("state_fdi_bubbles", "FDI vs Ease of Business", null, Color(0xFFB39DDB), "DPIIT",
            listOf("Policy attractiveness index", "Inflow concentration", "Business environment score"),
            listOf("state_gsdp_ranking"), ChartType.BUBBLE, Routes.STATES))

        register(ChartMeta("state_milestones", "State Economic Milestones", null, Color(0xFFB39DDB), "Govt Records",
            listOf("Major project completions", "Policy reform launches", "GSDP target achievements"),
            listOf("state_gsdp_ranking"), ChartType.TIMELINE, Routes.STATES))

        register(ChartMeta("state_gsdp_trends", "Top 5 States Trend", null, Color(0xFFB39DDB), "Historical Data",
            listOf("Long-term growth trajectories", "Resilience during crises", "Relative rank stability"),
            listOf("state_gsdp_ranking"), ChartType.MULTI_LINE, Routes.STATES))

        register(ChartMeta("state_workforce_venn", "Workforce Overlap", null, Color(0xFFB39DDB), "Labor Ministry",
            listOf("Multi-sectoral skills", "Gig economy integration", "Formalization trends"),
            listOf("state_gsdp_ranking"), ChartType.VENN, Routes.STATES))

        register(ChartMeta("state_literacy_speedo", "State Literacy Performance", null, Color(0xFFB39DDB), "Edu Ministry",
            listOf("Education outcome tracking", "Skill development reach", "Literacy rate milestones"),
            listOf("state_gsdp_ranking"), ChartType.GAUGE_SPEEDO, Routes.STATES))

        register(ChartMeta("state_growth_area", "GSDP Growth Composition", null, Color(0xFFB39DDB), "NITI Aayog",
            listOf("Sectoral growth contribution", "New growth engines", "Structural shift analysis"),
            listOf("state_gsdp_ranking"), ChartType.STACKED_AREA, Routes.STATES))


        // --- SECTION 6: STOCK HEATMAP ---
        register(ChartMeta("stock_treemap", "Nifty 500 Market Cap", "LIVE", Color(0xFF4FC3F7), "NSE",
            listOf("HDFC and Reliance lead cap", "Financials have largest weight", "IT sector valuations steady"),
            listOf("stock_sector_returns", "stock_pe_scatter"), ChartType.TREEMAP, Routes.MARKET))

        register(ChartMeta("stock_sector_returns", "Sectoral Performance", null, Color(0xFF4FC3F7), "NSE / BSE",
            listOf("Banking leads today's gains", "Metal sector under pressure", "Auto stocks hitting 52w high"),
            listOf("stock_treemap", "stock_fii_mirror"), ChartType.BAR, Routes.MARKET))

        register(ChartMeta("stock_fii_mirror", "FII vs DII Daily Buying", null, Color(0xFF4FC3F7), "NSDL / CDSL",
            listOf("DII support continues", "FII turned net buyers", "Retail participation at 40%"),
            listOf("stock_vix_wave"), ChartType.MIRROR_BAR, Routes.MARKET))

        register(ChartMeta("stock_vix_wave", "India VIX Volatility", null, Color(0xFF4FC3F7), "NSE",
            listOf("VIX below 15 (Low risk)", "Volatility spikes expected in MPC", "Option premiums normalizing"),
            listOf("stock_nifty_candle"), ChartType.WAVEFORM, Routes.MARKET))

        register(ChartMeta("stock_nifty_candle", "Nifty 50 OHLCV", null, Color(0xFF4FC3F7), "NSE",
            listOf("Bullish engulfing pattern", "Support at 24,000", "Volume break-out observed"),
            listOf("stock_vix_wave"), ChartType.CANDLE, Routes.MARKET))

        register(ChartMeta("stock_pe_scatter", "Valuation Radar", null, Color(0xFF4FC3F7), "Value Research",
            listOf("Nifty PE at 22x (Neutral)", "Mid-caps showing premium", "Small-cap growth potential"),
            listOf("stock_treemap"), ChartType.SCATTER_TREND, Routes.MARKET))

        register(ChartMeta("stock_pcr_gauge", "Put-Call Ratio", null, Color(0xFF4FC3F7), "NSE Derivatives",
            listOf("PCR at 1.05 (Neutral)", "Option pain point identified", "Expiry volatility expected"),
            listOf("stock_nifty_candle"), ChartType.GAUGE_HALF, Routes.MARKET))

        register(ChartMeta("stock_breadth_donut", "Market Breadth", null, Color(0xFF4FC3F7), "BSE",
            listOf("Advances outpacing declines", "Broad based rally", "Small-cap participation high"),
            listOf("stock_sector_returns"), ChartType.DONUT, Routes.MARKET))

        register(ChartMeta("stock_fii_waterfall", "Cumulative FII Flow", null, Color(0xFF4FC3F7), "NSDL",
            listOf("Net inflows of $10B YTD", "Sectoral rotation observed", "Index weight adjustments"),
            listOf("stock_fii_mirror"), ChartType.WATERFALL, Routes.MARKET))

        register(ChartMeta("stock_alpha_bubbles", "Alpha Opportunity Map", null, Color(0xFF4FC3F7), "Quant Engine",
            listOf("Momentum vs Value screen", "Earnings quality filter", "Risk-adjusted return view"),
            listOf("stock_treemap"), ChartType.BUBBLE, Routes.MARKET))


        // --- SECTION 10: SECTOR DEEP DIVE ---
        register(ChartMeta("sector_treemap", "Sub-sector Market Density", null, Color(0xFFB39DDB), "MOSPI / DPIIT",
            listOf("Niche sub-sector growth", "Market fragmentation analysis", "Leading player share"),
            listOf("sector_area_trend"), ChartType.TREEMAP, Routes.SECTOR))

        register(ChartMeta("sector_area_trend", "GVA Contribution Trend", null, Color(0xFFB39DDB), "MOSPI",
            listOf("Sectoral resilience trend", "Shift towards services", "Manufacturing resurgence"),
            listOf("sector_treemap"), ChartType.STACKED_AREA, Routes.SECTOR))

        register(ChartMeta("sector_radar", "Core Performance Radar", null, Color(0xFFB39DDB), "Sector Analytics",
            listOf("Multi-factor benchmarking", "Global parity assessment", "Strategic gap analysis"),
            listOf("sector_treemap"), ChartType.RADAR, Routes.SECTOR))

        register(ChartMeta("sector_productivity", "Productivity Dynamics", null, Color(0xFFB39DDB), "Labor Ministry",
            listOf("Output per worker trends", "Automation impact mapped", "Wage-Productivity balance"),
            listOf("sector_treemap"), ChartType.SCATTER_TREND, Routes.SECTOR))

        register(ChartMeta("sector_market_share", "Market Share", null, Color(0xFFB39DDB), "Industry Data",
            listOf("Dominance in sub-sectors", "Market entry barriers", "Competitive landscape"),
            listOf("sector_treemap"), ChartType.GAUGE_HALF, Routes.SECTOR))

        register(ChartMeta("sector_utilization", "Capacity Utilization", null, Color(0xFFB39DDB), "RBI OBICUS",
            listOf("Investment cycle indicator", "Supply side constraints", "Efficiency benchmarks"),
            listOf("sector_treemap"), ChartType.GAUGE_SPEEDO, Routes.SECTOR))

        register(ChartMeta("sector_subsector_growth", "Sub-sector Growth", null, Color(0xFFB39DDB), "MOSPI",
            listOf("Fastest growing niches", "Structural growth drivers", "Investment opportunities"),
            listOf("sector_treemap"), ChartType.BAR, Routes.SECTOR))

        register(ChartMeta("sector_revenue_mix", "Domestic vs Export Revenue", null, Color(0xFFB39DDB), "Ministry of Commerce",
            listOf("Global market reach", "Export oriented growth", "Domestic demand resilience"),
            listOf("sector_subsector_growth"), ChartType.MIRROR_BAR, Routes.SECTOR))

        register(ChartMeta("sector_price_wave", "Sectoral WPI Volatility", null, Color(0xFFB39DDB), "MOSPI",
            listOf("Input cost trends", "Pricing power analysis", "Margin pressure monitor"),
            listOf("sector_treemap"), ChartType.WAVEFORM, Routes.SECTOR))

        register(ChartMeta("sector_revenue_waterfall", "Revenue Decomposition", null, Color(0xFFB39DDB), "Company Reports",
            listOf("Cost structure analysis", "EBITDA margin drivers", "Profitability waterfall"),
            listOf("sector_treemap"), ChartType.WATERFALL, Routes.SECTOR))

        register(ChartMeta("sector_index_candles", "Sector Index Fluctuation", null, Color(0xFFB39DDB), "NSE Indices",
            listOf("Sectoral momentum view", "Technical breakout alert", "Relative strength index"),
            listOf("sector_treemap"), ChartType.CANDLE, Routes.SECTOR))

        register(ChartMeta("sector_fdi_matrix", "Historical FDI Inflow", null, Color(0xFFB39DDB), "DPIIT",
            listOf("Foreign investor interest", "Policy impact on FDI", "Investment hub emerging"),
            listOf("sector_treemap"), ChartType.HEATMAP, Routes.SECTOR))

        register(ChartMeta("sector_employment_polar", "Workforce Distribution", null, Color(0xFFB39DDB), "PLFS",
            listOf("Skill level distribution", "Job creation potential", "Gender diversity index"),
            listOf("sector_treemap"), ChartType.POLAR_AREA, Routes.SECTOR))

        register(ChartMeta("sector_strategic_investments", "Strategic Investments", null, Color(0xFFB39DDB), "News/Govt",
            listOf("Large scale infra projects", "Joint venture milestones", "Tech transfer agreements"),
            listOf("sector_treemap"), ChartType.TIMELINE, Routes.SECTOR))

        register(ChartMeta("sector_global_rankings", "Global Sub-sector Rankings", null, Color(0xFFB39DDB), "Global Benchmarks",
            listOf("India vs Global peers", "Export competitiveness", "Innovation rank index"),
            listOf("sector_treemap"), ChartType.RING_CLUSTER, Routes.SECTOR))

        register(ChartMeta("sector_synergy_venn", "Inter-sectoral Synergy", null, Color(0xFFB39DDB), "NITI Aayog",
            listOf("Cross-sectoral linkages", "Value chain integration", "Ecosystem multiplier"),
            listOf("sector_treemap"), ChartType.VENN, Routes.SECTOR))


        // --- SECTION 9: COMPARE SCREEN ---
        register(ChartMeta("compare_gdp", "World GDP Comparison", "LIVE", Color(0xFF00F5FF), "IMF / World Bank",
            listOf("India reaching $10T parity", "Fastest growing major economy", "Global share milestone"),
            listOf("compare_growth_scatter", "compare_radar"), ChartType.BAR, Routes.COMPARE))

        register(ChartMeta("compare_growth_scatter", "Growth vs Inflation", null, Color(0xFF00F5FF), "IMF",
            listOf("Optimized growth band", "Inflation resilience index", "Macro stability peer view"),
            listOf("compare_gdp"), ChartType.SCATTER_TREND, Routes.COMPARE))

        register(ChartMeta("compare_radar", "Global Macro Performance", null, Color(0xFF00F5FF), "Global Rankings",
            listOf("Competitiveness score high", "Innovation vs Peers", "Fiscal prudence rank"),
            listOf("compare_gdp"), ChartType.RADAR, Routes.COMPARE))

        register(ChartMeta("compare_inflation_mirror", "Growth vs Inflation Mirror", null, Color(0xFF00F5FF), "Benchmarking",
            listOf("Balanced economic cycles", "Policy response effectiveness", "Real rate comparison"),
            listOf("compare_gdp"), ChartType.MIRROR_BAR, Routes.COMPARE))

        register(ChartMeta("compare_hdi_rings", "HDI Peer Rings", null, Color(0xFF00F5FF), "UNDP",
            listOf("Standard of living metrics", "Education access parity", "Life expectancy milestones"),
            listOf("compare_gdp"), ChartType.RING_CLUSTER, Routes.COMPARE))

        register(ChartMeta("compare_fx_polar", "FX Reserve Composition", null, Color(0xFF00F5FF), "RBI / Central Banks",
            listOf("Diversification strategy", "Liquidity buffer safety", "Currency peg resilience"),
            listOf("compare_gdp"), ChartType.POLAR_AREA, Routes.COMPARE))

        register(ChartMeta("compare_mkt_cap_treemap", "Stock Exchange Market Cap", null, Color(0xFF00F5FF), "WFE",
            listOf("NSE rank hitting top 5", "Liquidity depth analysis", "Investor base growth"),
            listOf("compare_gdp"), ChartType.TREEMAP, Routes.COMPARE))

        register(ChartMeta("compare_comp_speedo", "Competitors Index", null, Color(0xFF00F5FF), "WEF",
            listOf("Global ease of business", "Infrastructure score", "Human capital rank"),
            listOf("compare_gdp"), ChartType.GAUGE_SPEEDO, Routes.COMPARE))

        register(ChartMeta("compare_recovery_waterfall", "Post-pandemic Recovery", null, Color(0xFF00F5FF), "IMF",
            listOf("Resilient growth rebound", "Structural reform impact", "Output gap closure"),
            listOf("compare_gdp"), ChartType.WATERFALL, Routes.COMPARE))

        register(ChartMeta("compare_export_venn", "Product Category Overlap", null, Color(0xFF00F5FF), "WTO",
            listOf("Competitive advantage niches", "Trade specialization", "Global supply chain role"),
            listOf("compare_gdp"), ChartType.VENN, Routes.COMPARE))

        register(ChartMeta("compare_g20_timeline", "G20 Economic Summits", null, Color(0xFF00F5FF), "G20 Secretariat",
            listOf("Policy coordination milestones", "Digital public infra push", "Sustainable growth goals"),
            listOf("compare_gdp"), ChartType.TIMELINE, Routes.COMPARE))

        register(ChartMeta("compare_sentiment_wave", "Market Sentiment", null, Color(0xFF00F5FF), "Global Sentiment",
            listOf("Investor confidence high", "FDI interest stable", "Consumer sentiment peak"),
            listOf("compare_gdp"), ChartType.WAVEFORM, Routes.COMPARE))


        // --- SECTION 8: DEMOGRAPHICS ---
        register(ChartMeta("demo_population_pyramid", "Population Pyramid", null, Color(0xFFF06292), "Census / UN",
            listOf("Demographic dividend peak", "Median age stability", "Working-age population rise"),
            listOf("demo_age_dist", "demo_hdi_multi"), ChartType.MIRROR_BAR, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_age_dist", "Age Cohort Distribution", null, Color(0xFFF06292), "Census",
            listOf("Young India dominance", "Urban migration patterns", "Dependency ratio status"),
            listOf("demo_population_pyramid"), ChartType.POLAR_AREA, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_urban_wave", "Urbanization Wave", null, Color(0xFFF06292), "UN Habitat",
            listOf("Mega city evolution", "Tier-3 hub growth", "Smart city saturation"),
            listOf("demo_population_pyramid"), ChartType.WAVEFORM, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_literacy_heatmap", "State Literacy Dynamics", null, Color(0xFFF06292), "Min of Education",
            listOf("Digital literacy milestones", "Female education parity", "Skill gap reduction"),
            listOf("demo_population_pyramid"), ChartType.HEATMAP, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_fertility_area", "Fertility Rate Projection", null, Color(0xFFF06292), "NFHS",
            listOf("TFR stabilization at 1.9", "Replacement level milestone", "Regional TFR variance"),
            listOf("demo_population_pyramid"), ChartType.AREA, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_life_expectancy", "Life Expectancy", null, Color(0xFFF06292), "Health Ministry",
            listOf("Improvement in longevity", "Health infrastructure impact", "Nutrition index gain"),
            listOf("demo_population_pyramid"), ChartType.GAUGE_SPEEDO, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_dep_ratio", "Dependency Ratio", null, Color(0xFFF06292), "World Bank",
            listOf("Lowest dependency in G20", "Golden demographic window", "Workforce expansion"),
            listOf("demo_population_pyramid"), ChartType.GAUGE_HALF, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_dividend_area", "Demographic Dividend", null, Color(0xFFF06292), "NITI Aayog",
            listOf("Peak growth contribution", "Skill development needs", "Job market readiness"),
            listOf("demo_population_pyramid"), ChartType.STACKED_AREA, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_language_treemap", "Language Distribution", null, Color(0xFFF06292), "Census",
            listOf("Linguistic diversity analysis", "Regional language growth", "Bilingualism trends"),
            listOf("demo_population_pyramid"), ChartType.TREEMAP, Routes.DEMOGRAPHICS))

        register(ChartMeta("demo_hdi_multi", "HDI Trajectory vs Peers", null, Color(0xFFF06292), "UNDP India",
            listOf("India hitting High HDI", "Peer comparison stable", "Multi-factor improvement"),
            listOf("demo_population_pyramid"), ChartType.MULTI_LINE, Routes.DEMOGRAPHICS))
    }
}
