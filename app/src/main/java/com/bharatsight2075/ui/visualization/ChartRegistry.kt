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
    fun get(chartId: String): ChartMeta? = registry[chartId]
    fun getRelated(chartId: String): List<ChartMeta> =
        registry[chartId]?.relatedChartIds?.mapNotNull { registry[it] } ?: emptyList()

    init {
        // MacroOverview
        register(ChartMeta("macro_gdp_hero", "Estimated 2075 GDP", "LIVE", Color(0xFF00F5FF), "World Bank / RBI",
            listOf("GDP projected at $37T by 2075", "8.1% YoY growth rate"), emptyList(), ChartType.NUMBER_TICKER, Routes.MACRO))
        
        // Agriculture
        register(ChartMeta("agri_hero", "Key Agriculture Metrics", "KHARIF", Color(0xFF76C442), "MOSPI",
            listOf("Resilient crop production", "MSP hikes supporting income"), emptyList(), ChartType.NUMBER_TICKER, Routes.AGRICULTURE))
        register(ChartMeta("agri_liquid_fill", "Wheat Reservoir Pulse", null, Color(0xFF76C442), "CWC",
            listOf("Reservoir levels at 78%", "Sufficient for rabi season"), emptyList(), ChartType.LIQUID_GAUGE, Routes.AGRICULTURE))

        // Banking
        register(ChartMeta("banking_hero", "Banking Health Stats", "RBI", Color(0xFF00B0FF), "RBI",
            listOf("Net NPAs at record low", "Digital lending up 40%"), emptyList(), ChartType.NUMBER_TICKER, Routes.BANKING))
        register(ChartMeta("banking_liquid_credit", "Credit-to-GDP Pulse", null, Color(0xFF00B0FF), "RBI",
            listOf("Healthy credit absorption", "Expansion in MSME sector"), emptyList(), ChartType.LIQUID_GAUGE, Routes.BANKING))

        // Energy
        register(ChartMeta("energy_hero", "Grid Telemetry", "LIVE", Color(0xFFFFD600), "POSOCO",
            listOf("Peak demand met successfully", "Green energy share rising"), emptyList(), ChartType.NUMBER_TICKER, Routes.ENERGY))
        register(ChartMeta("energy_ev_liquid", "EV Adoption Rate", null, Color(0xFFFFD600), "Vahan",
            listOf("EV sales growth at 200%", "Battery prices dropping"), emptyList(), ChartType.LIQUID_GAUGE, Routes.ENERGY))

        // Infrastructure
        register(ChartMeta("smart_cities_hero", "Infrastructure Summary", "100 CITIES", Color(0xFF4FC3F7), "MoHUA",
            listOf("Smart city projects 92% complete", "Metro network expanded"), emptyList(), ChartType.NUMBER_TICKER, Routes.SMART_CITIES))
        
        // Startup
        register(ChartMeta("startup_hero", "Ecosystem Scorecard", "113 UNICORNS", Color(0xFF7C4DFF), "DPIIT",
            listOf("India #3 global ecosystem", "Deeptech funding surging"), emptyList(), ChartType.NUMBER_TICKER, Routes.STARTUP))

        // Defence
        register(ChartMeta("defence_hero", "Strategic Budget", "CLASSIFIED", Color(0xFFFF5252), "MOD",
            listOf("Indigenisation targets met", "Defence exports record high"), emptyList(), ChartType.NUMBER_TICKER, Routes.DEFENCE))

        // Climate
        register(ChartMeta("climate_hero", "Net Zero Pulse", "NET ZERO", Color(0xFF00E676), "COP28",
            listOf("India on track for 2070", "Carbon intensity reduced"), emptyList(), ChartType.NUMBER_TICKER, Routes.CLIMATE))

        // Register other IDs to prevent empty detail screens
        listOf("agri_msp_trend", "agri_export_polar", "agri_income_mirror", "agri_soil_radar", "agri_spiral_timeline", "agri_orbital_insurance",
               "banking_npa_trend", "banking_portfolio_orbital", "banking_recovery_waterfall", "banking_digital_spiral",
               "energy_grid_wave", "energy_mix_orbital", "energy_source_stacked", "energy_carbon_spiral",
               "cities_housing_liquid", "cities_infra_radar", "cities_land_use", "cities_port_orbital", "cities_capex_waterfall",
               "startup_funding_race", "startup_exit_waterfall", "startup_innovation_radar", "startup_patent_spiral", "startup_founder_venn",
               "defence_strategic_reserve", "defence_budget_orbital", "defence_modernization_funnel", "defence_capability_radar", "defence_conflict_spiral",
               "climate_budget_liquid", "climate_risk_radar", "climate_forest_hex", "climate_temp_spiral", "climate_sink_orbital",
               "digital_hero", "digital_upi_wave", "digital_consumption_liquid", "digital_security_radar", "digital_locker_spiral", "digital_cbdc_orbital",
               "education_hero", "education_literacy_spiral", "education_skill_radar", "education_skill_liquid", "education_rank_orbital", "education_spend_waterfall",
               "health_hero", "health_spend_liquid", "health_life_spiral", "health_outcome_radar", "health_insurance_orbital", "health_telemedicine_wave",
               "realestate_hero", "realestate_housing_liquid", "realestate_inventory_race", "realestate_permit_spiral", "realestate_reits_orbital",
               "tourism_hero", "tourism_visa_liquid", "tourism_pilgrimage_spiral", "tourism_state_radar", "tourism_hotel_candle",
               "space_hero", "space_launch_orbital", "space_mission_spiral", "space_launch_race", "space_satellite_bonds",
               "georisk_hero", "georisk_asset_liquid", "georisk_conflict_spiral", "georisk_dependency_mirror", "georisk_risk_heatmap",
               "inequality_hero", "inequality_lorenz_area", "inequality_consumption_mirror", "inequality_poverty_spiral", "inequality_safety_net_liquid",
               "labour_hero", "labour_sector_race", "labour_remote_liquid", "labour_quality_radar", "labour_youth_unemployment_spiral", "labour_gender_mirror",
               "logistics_hero", "logistics_port_candle", "logistics_cold_liquid", "logistics_cost_mirror", "logistics_customs_spiral", "logistics_modal_split",
               "media_hero", "media_content_wave", "media_podcast_spiral", "media_streaming_mirror", "media_genre_radar", "media_ipl_orbital",
               "resources_hero", "resources_water_liquid", "resources_groundwater_spiral", "resources_fisheries_orbital", "resources_curse_radar", "resources_pollution_mirror",
               "softpower_hero", "softpower_index_orbital", "softpower_sanskrit_spiral", "softpower_perception_radar", "softpower_cricket_pulse", "softpower_brand_mirror"
        ).forEach { id ->
            register(ChartMeta(id, id.replace("_", " ").uppercase(), null, Color.Cyan, "Govt Sources", listOf("Detailed analytical view", "High fidelity simulation"), emptyList(), ChartType.AREA, Routes.HOME))
        }
    }
}
