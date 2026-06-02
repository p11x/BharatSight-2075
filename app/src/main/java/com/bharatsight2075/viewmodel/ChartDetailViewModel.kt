package com.bharatsight2075.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatsight2075.data.repositories.EconomicRepository
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
    savedStateHandle: SavedStateHandle,
    private val economicRepository: EconomicRepository
) : ViewModel() {
    val chartId: String = savedStateHandle["chartId"] ?: ""
    val chartMeta: ChartMeta? = ChartRegistry.get(chartId)

    private val _chartData = MutableStateFlow<List<Any>>(emptyList())
    val chartData: StateFlow<List<Any>> = _chartData.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch(Dispatchers.IO) {
        val data = economicRepository.getDataForChart(chartId)
            .ifEmpty { ChartMockData.generateMockData(chartMeta?.chartType ?: ChartType.MULTI_LINE) }
        _chartData.value = data
    }
}
