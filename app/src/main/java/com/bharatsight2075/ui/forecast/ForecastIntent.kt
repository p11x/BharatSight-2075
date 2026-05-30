package com.bharatsight2075.ui.forecast

import com.bharatsight2075.data.local.PolicyImpact
import com.bharatsight2075.ui.base.Intent

sealed class ForecastIntent : Intent {
    data class UpdateTaxRate(val rate: Float) : ForecastIntent()
    data class UpdateInfrastructure(val rate: Float) : ForecastIntent()
    data class UpdateEducation(val rate: Float) : ForecastIntent()
    data class UpdateForeignPolicy(val rate: Float) : ForecastIntent()
    data class GeneratePrediction(val policyImpact: PolicyImpact) : ForecastIntent()
}
