package com.bharatsight2075.domain.usecase

import com.bharatsight2075.data.local.PolicyImpact
import com.bharatsight2075.ml.EconomyPredictorTFLite
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Use case for calculating economic trajectory based on policy inputs.
 * This encapsulates the business logic for generating economic predictions
 * using the TFLite model, separating it from the ViewModel layer.
 */
@Singleton
class CalculateTrajectoryUseCase @Inject constructor(
    private val economyPredictor: EconomyPredictorTFLite
) {

    /**
     * Executes the trajectory calculation with the given policy impact.
     *
     * @param policyImpact The policy parameters to use for prediction
     * @return FloatArray containing the predicted GDP trajectory
     */
    fun execute(policyImpact: PolicyImpact): FloatArray {
        return economyPredictor.predictMultiOutput(
            policyImpact.taxRate.toFloat(),
            policyImpact.infrastructure.toFloat(),
            policyImpact.education.toFloat(),
            policyImpact.foreignPolicy.toFloat()
        ).gdp
    }
}

/**
 * Use case for retrieving historical economic data.
 * This would typically interact with a repository to get stored historical data.
 */
@Singleton
class GetHistoricalEconomyUseCase @Inject constructor(
    // In a real implementation, this would inject a repository
    // private val repository: EconomicRepository
) {

    /**
     * Executes the retrieval of historical economic data.
     *
     * @return List of HistoricalEconomicData
     */
    fun execute(): List<com.bharatsight2075.data.local.HistoricalEconomicData> {
        // Placeholder implementation - in reality this would call the repository
        // return repository.getHistoricalData()
        return com.bharatsight2075.data.local.MockDataGenerator.generateHistoricalData()
    }
}