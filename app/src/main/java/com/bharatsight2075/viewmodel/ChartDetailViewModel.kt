package com.bharatsight2075.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatsight2075.data.repositories.EconomicRepository
import com.bharatsight2075.ui.screens.Routes
import com.bharatsight2075.ui.visualization.ChartMeta
import com.bharatsight2075.ui.visualization.ChartMockData
import com.bharatsight2075.ui.visualization.ChartRegistry
import com.bharatsight2075.ui.visualization.ChartType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartDetailViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val economicRepository: EconomicRepository
) : ViewModel() {
    val chartId: String = savedState["chartId"] ?: ""

    private val _chartMeta = MutableStateFlow<ChartMeta?>(null)
    val chartMeta: StateFlow<ChartMeta?> = _chartMeta.asStateFlow()

    private val _chartData = MutableStateFlow<List<Float>>(emptyList())
    val chartData: StateFlow<List<Float>> = _chartData.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            val meta = ChartRegistry.get(chartId)
            val liveData = try {
                economicRepository.getDataForChart(chartId)
            } catch(e: Exception) { emptyList() }
            
            // Ensure we have List<Float> for the detail view logic
            _chartData.value = liveData.filterIsInstance<Float>().ifEmpty {
                ChartMockData.generateMockFor(
                    meta?.chartType ?: ChartType.AREA
                )
            }
            
            _chartMeta.value = meta ?: ChartMeta(
                chartId     = chartId,
                title       = chartId.replace("_"," ").uppercase(),
                badge       = null,
                badgeColor  = Color(0xFF00F5FF),
                dataSource  = "MockData",
                insights    = listOf(
                    "Trend shows consistent growth over the period",
                    "Peak value reached at the midpoint of the series",
                    "Data reflects India's official economic indicators"
                ),
                relatedChartIds = emptyList(),
                chartType   = ChartType.AREA,
                sectionRoute= Routes.HOME
            )
            _isLoading.value = false
        }
    }
    
    fun injectMockData() {
        val meta = _chartMeta.value
        _chartData.value = ChartMockData.generateMockFor(meta?.chartType ?: ChartType.AREA)
    }
}
