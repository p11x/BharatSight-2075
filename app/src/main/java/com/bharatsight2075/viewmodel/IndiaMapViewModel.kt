package com.bharatsight2075.viewmodel

import android.content.Context
import android.graphics.Path as AndroidPath
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatsight2075.ui.maps.IndiaGeoJsonParser
import com.bharatsight2075.ui.maps.StateShape
import com.bharatsight2075.ui.visualization.MockData
import com.bharatsight2075.ui.visualization.SectionDefaultData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapPathCache(
    val statePaths: List<Pair<StateShape, List<AndroidPath>>>,
    val canvasW: Float,
    val canvasH: Float
)

enum class MapStyle(val title: String, val description: String, val accentColor: Long) {
    GDP_CHOROPLETH("GDP Choropleth", "State & district GDP per capita heat", 0xFF00F5FF),
    GROWTH_PULSE("Growth Rate Pulse", "Animated pulsing rings by growth %", 0xFF00E676),
    POPULATION_DENSITY("Population Density", "Dot density map — 1 dot = 10,000 people", 0xFFFF6B35),
    AGRICULTURE_ZONES("Agriculture Zones", "Crop zone classification by color", 0xFF76C442),
    INDUSTRIAL_CLUSTERS("Industrial Clusters", "Manufacturing hub network graph", 0xFF00B0FF),
    DIGITAL_CONNECTIVITY("Digital Connectivity", "Internet coverage gradient per district", 0xFF7C4DFF),
    POVERTY_INDEX("Poverty Index", "Multidimensional poverty heat map", 0xFFFF5252),
    EMPLOYMENT_HEAT("Employment Heatmap", "LFPR by district — cool to warm", 0xFFFFD600),
    FDI_FLOW("FDI Flow Map", "FDI inflow animated arcs by state", 0xFFFF6B35),
    INFRASTRUCTURE("Infrastructure Score", "Roads+Rail+Power composite index", 0xFF4FC3F7),
    RENEWABLE_ENERGY("Renewable Energy", "Solar+Wind installation capacity bubbles", 0xFFFFD600),
    LITERACY_MAP("Literacy Rate", "Graduated symbol map — circle r=literacy%", 0xFFB39DDB),
    HEALTH_INDEX("Health Index", "District NHM performance choropleth", 0xFFF06292),
    BANKING_DENSITY("Banking Penetration", "Branches per 1000 pop heat", 0xFF00B0FF),
    STARTUP_HUBS("Startup Ecosystem", "Unicorn/startup cluster bubbles", 0xFF7C4DFF),
    TOURISM_FLOWS("Tourism Flows", "Domestic+foreign tourist flow arcs", 0xFFFFD54F),
    WATER_STRESS("Water Stress Index", "Groundwater depletion risk zones", 0xFF4DD0E1),
    URBANIZATION("Urbanization Rate", "Urban-rural gradient choropleth", 0xFFFF8A65),
    AGRI_PRODUCTIVITY("Agri Productivity", "Yield per hectare district heatmap", 0xFF76C442),
    TRANSPORT_NETWORK("Transport Network", "Rail+Road+Air animated network", 0xFF80CBC4),
    CLIMATE_RISK("Climate Risk Zones", "Flood+Drought+Cyclone composite", 0xFFFF5252),
    GENDER_INDEX("Gender Development", "GII district-level choropleth", 0xFFE040FB),
    EXPORT_INTENSITY("Export Intensity", "Export value per district bubble", 0xFFFF6B35),
    MINERALS_MAP("Mineral Resources", "Mine location + type symbol map", 0xFFFFD600),
    NIGHTLIGHT_ECONOMY("Night Light Index", "VIIRS satellite night light proxy GDP", 0xFFFFE082),
    EDUCATION_MAP("Education Index", "School density + GER composite", 0xFFFFC107),
    DEFENCE_ASSETS("Defence Assets", "Cantonments + bases classified map", 0xFFFF5252),
    DISASTER_RISK("Disaster Risk", "NDMA multi-hazard district score", 0xFFFF8A65),
    GINI_DISTRICT("Inequality Map", "District-level Gini coefficient heat", 0xFFE040FB),
    COMPOSITE_INDEX("Composite Development", "10-metric composite district index", 0xFF00F5FF)
}

@HiltViewModel
class IndiaMapViewModel @Inject constructor(
  @ApplicationContext private val context: Context
) : ViewModel() {

  private val _data = MutableStateFlow(SectionDefaultData(MockData.generateHeroStats("maps")))
  val data: StateFlow<SectionDefaultData> = _data.asStateFlow()

  private val _states = MutableStateFlow<List<StateShape>>(emptyList())
  val states: StateFlow<List<StateShape>> = _states.asStateFlow()

  private val _isLoading = MutableStateFlow(true)
  val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

  private val _selectedStyle = MutableStateFlow(MapStyle.GDP_CHOROPLETH)
  val selectedStyle: StateFlow<MapStyle> = _selectedStyle.asStateFlow()

  private val _selectedState = MutableStateFlow<String?>(null)
  val selectedState: StateFlow<String?> = _selectedState.asStateFlow()

  // Cached paths keyed by canvas size — built on Dispatchers.Default
  private val _pathCache = MutableStateFlow<MapPathCache?>(null)
  val pathCache: StateFlow<MapPathCache?> = _pathCache.asStateFlow()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      try {
        val parsed = IndiaGeoJsonParser.parse(context)
        _states.value = parsed
      } catch (e: Exception) {
        // Log but don't crash — map will show empty
        android.util.Log.e("IndiaMap", "GeoJSON parse failed", e)
      } finally {
        _isLoading.value = false
      }
    }
  }

  // Call this from NeonIndiaMap when canvas size is known
  fun buildPathCache(canvasW: Float, canvasH: Float) {
    if (canvasW <= 0f || canvasH <= 0f) return
    val current = _pathCache.value
    if (current != null && current.canvasW == canvasW && current.canvasH == canvasH) return
    
    viewModelScope.launch(Dispatchers.Default) {   // OFF MAIN THREAD
      val statesList = _states.value
      if (statesList.isEmpty()) return@launch

      val statePathsList = statesList.map { state ->
        state to state.rings.map { ring ->
          IndiaGeoJsonParser.ringToPath(ring, canvasW, canvasH)
        }
      }
      
      _pathCache.value = MapPathCache(
        statePaths   = statePathsList,
        canvasW      = canvasW,
        canvasH      = canvasH
      )
    }
  }

  fun selectStyle(style: MapStyle) { _selectedStyle.value = style }
  fun selectState(name: String?)   { _selectedState.value = name  }
}
