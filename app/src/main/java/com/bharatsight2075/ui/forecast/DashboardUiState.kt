package com.bharatsight2075.ui.forecast

import com.bharatsight2075.ui.visualization.VisualizationData

/**
 * Sealed interface representing the possible states of the dashboard UI.
 * This replaces the generic ForecastState with a more precise state model.
 */
sealed interface DashboardUiState : com.bharatsight2075.ui.base.State {
    /** State when data is being loaded */
    object Loading : DashboardUiState
    
    /** State when data has been successfully loaded */
    open class Success(open val data: VisualizationData) : DashboardUiState
    
    /** State when an error occurred */
    data class Error(val message: String) : DashboardUiState
}