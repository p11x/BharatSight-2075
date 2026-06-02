package com.bharatsight2075.ui.forecast

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.bharatsight2075.domain.usecase.CalculateTrajectoryUseCase
import com.bharatsight2075.domain.usecase.GetHistoricalEconomyUseCase
import com.bharatsight2075.ui.forecast.DashboardUiState
import com.bharatsight2075.ui.base.BaseViewModel
import com.bharatsight2075.ui.base.Intent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class ForecastUiState(
    val taxRate: Float = 0.5f,
    val infrastructure: Float = 0.5f,
    val education: Float = 0.5f,
    val foreignPolicy: Float = 0.5f,
    val predictedGdp: List<Float> = emptyList()
) : DashboardUiState.Success(
    data = com.bharatsight2075.ui.visualization.VisualizationData.TimeSeries(
        points = emptyList()
    )
)

@HiltViewModel
class ForecastEngineViewModel @Inject constructor(
    private val getHistoricalEconomyUseCase: GetHistoricalEconomyUseCase,
    private val calculateTrajectoryUseCase: CalculateTrajectoryUseCase
) : BaseViewModel<ForecastIntent, DashboardUiState>() {
    
    private val policyFlow = MutableStateFlow(com.bharatsight2075.data.local.PolicyImpact())
    
    init {
        setupDebouncing()
        loadInitialData()
    }
    
    @OptIn(FlowPreview::class)
    private fun setupDebouncing() {
        policyFlow
            .debounce(150)
            .distinctUntilChanged()
            .onEach { policy ->
                calculateTrajectory(policy)
            }
            .launchIn(viewModelScope)
    }
    
    private fun loadInitialData() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val historicalData = getHistoricalEconomyUseCase.execute()
                // In a real implementation, we would process historical data here
                // For now, we'll just emit loading then success with empty data
                setState { DashboardUiState.Loading }
                setState { ForecastUiState() }
            } catch (e: Exception) {
                setState { DashboardUiState.Error("Failed to load historical data: ${e.message}") }
            }
        }
    }
    
    override fun createInitialState(): DashboardUiState = DashboardUiState.Loading
    
    override fun handleIntent(intent: ForecastIntent) {
        when (intent) {
            is ForecastIntent.UpdateTaxRate -> setState { 
                (this as? ForecastUiState)?.copy(taxRate = intent.rate) ?: this 
            }
            is ForecastIntent.UpdateInfrastructure -> setState { 
                (this as? ForecastUiState)?.copy(infrastructure = intent.rate) ?: this 
            }
            is ForecastIntent.UpdateEducation -> setState { 
                (this as? ForecastUiState)?.copy(education = intent.rate) ?: this 
            }
            is ForecastIntent.UpdateForeignPolicy -> setState { 
                (this as? ForecastUiState)?.copy(foreignPolicy = intent.rate) ?: this 
            }
            is ForecastIntent.GeneratePrediction -> {
                policyFlow.value = intent.policyImpact
            }
        }
    }
    
    private fun calculateTrajectory(policy: com.bharatsight2075.data.local.PolicyImpact) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val prediction = calculateTrajectoryUseCase.execute(policy)
                val years = (2026..2075).toList()
                
                val points = prediction.mapIndexed { index, value ->
                    com.bharatsight2075.ui.visualization.Point(
                        x = years.getOrElse(index) { 2026 + index }.toFloat(),
                        y = value
                    )
                }

                val visualizationData = com.bharatsight2075.ui.visualization.VisualizationData.TimeSeries(points)
                
                setState {
                    (this as? ForecastUiState)?.copy(
                        predictedGdp = prediction.toList()
                    ) ?: DashboardUiState.Success(visualizationData)
                }
            } catch (e: Exception) {
                setState { DashboardUiState.Error("Failed to calculate trajectory: ${e.message}") }
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        // Note: Use cases don't need to be closed as they don't hold resources directly
        // The EconomyPredictorTFLite is managed inside CalculateTrajectoryUseCase
    }
}