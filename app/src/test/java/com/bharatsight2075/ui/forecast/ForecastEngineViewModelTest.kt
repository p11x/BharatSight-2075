package com.bharatsight2075.ui.forecast

import androidx.compose.runtime.test.apply
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.bharatsight2075.data.local.PolicyImpact
import com.bharatsight2075.ml.EconomyPredictorTFLite
import com.bharatsight2075.ui.base.Intent
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

/**
 * Unit tests for ForecastEngineViewModel.
 * Tests the ViewModel's handling of intents and state updates.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ForecastEngineViewModelTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<Forecaster2075Screen>()

    @Mock
    private lateinit var mockRepository: com.bharatsight2075.data.repositories.EconomicRepository

    @Mock
    private lateinit var mockEconomyPredictor: EconomyPredictorTFLite

    private lateinit var viewModel: ForecastEngineViewModel
    private lateinit var testDispatcher: StandardTestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)
        
        viewModel = ForecastEngineViewModel(mockRepository, mockEconomyPredictor)
    }

    @Test
    fun `tax rate update is reflected in state`() = runTest {
        // Arrange
        val newTaxRate = 0.7f
        
        // Act
        viewModel.handleIntent(ForecastIntent.UpdateTaxRate(newTaxRate))
        
        // Assert
        assertThat(viewModel.state.taxRate).isEqualTo(newTaxRate)
        assertThat(viewModel.state.infrastructure).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.education).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.foreignPolicy).isEqualTo(0.5f) // unchanged
    }

    @Test
    fun `infrastructure update is reflected in state`() = runTest {
        // Arrange
        val newInfrastructure = 0.8f
        
        // Act
        viewModel.handleIntent(ForecastIntent.UpdateInfrastructure(newInfrastructure))
        
        // Assert
        assertThat(viewModel.state.infrastructure).isEqualTo(newInfrastructure)
        assertThat(viewModel.state.taxRate).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.education).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.foreignPolicy).isEqualTo(0.5f) // unchanged
    }

    @Test
    fun `education update is reflected in state`() = runTest {
        // Arrange
        val newEducation = 0.3f
        
        // Act
        viewModel.handleIntent(ForecastIntent.UpdateEducation(newEducation))
        
        // Assert
        assertThat(viewModel.state.education).isEqualTo(newEducation)
        assertThat(viewModel.state.taxRate).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.infrastructure).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.foreignPolicy).isEqualTo(0.5f) // unchanged
    }

    @Test
    fun `foreign policy update is reflected in state`() = runTest {
        // Arrange
        val newForeignPolicy = 0.9f
        
        // Act
        viewModel.handleIntent(ForecastIntent.UpdateForeignPolicy(newForeignPolicy))
        
        // Assert
        assertThat(viewModel.state.foreignPolicy).isEqualTo(newForeignPolicy)
        assertThat(viewModel.state.taxRate).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.infrastructure).isEqualTo(0.5f) // unchanged
        assertThat(viewModel.state.education).isEqualTo(0.5f) // unchanged
    }

    @Test
    fun `generate prediction triggers predictor and updates state`() = runTest {
        // Arrange
        val policyImpact = PolicyImpact(taxRate = 0.6, infrastructure = 0.7, education = 0.8, foreignPolicy = 0.9)
        val mockPrediction = FloatArray(50) { 100.0f } // Mock prediction data
        
        `when`(mockEconomyPredictor.predictTrajectory(
            policyImpact.taxRate.toFloat(),
            policyImpact.infrastructure.toFloat(),
            policyImpact.education.toFloat(),
            policyImpact.foreignPolicy.toFloat()
        )).thenReturn(mockPrediction)
        
        // Act
        viewModel.handleIntent(ForecastIntent.GeneratePrediction(policyImpact))
        
        // Assert
        // Verify that the predictor was called with correct parameters
        verify(mockEconomyPredictor).predictTrajectory(
            policyImpact.taxRate.toFloat(),
            policyImpact.infrastructure.toFloat(),
            policyImpact.education.toFloat(),
            policyImpact.foreignPolicy.toFloat()
        )
        
        // Verify that state was updated with prediction
        assertThat(viewModel.state.predictedGdp).hasSize(50)
        assertThat(viewModel.state.predictedGdp[0]).isEqualTo(100.0f)
    }

    @Test
    fun `policy changes are debounced for 150ms`() = runTest {
        // Arrange
        val policyImpact1 = PolicyImpact(taxRate = 0.6, infrastructure = 0.5, education = 0.5, foreignPolicy = 0.5)
        val policyImpact2 = PolicyImpact(taxRate = 0.7, infrastructure = 0.5, education = 0.5, foreignPolicy = 0.5)
        val mockPrediction = FloatArray(50) { 150.0f }
        
        `when`(mockEconomyPredictor.predictTrajectory(
            policyImpact2.taxRate.toFloat(),
            policyImpact2.infrastructure.toFloat(),
            policyImpact2.education.toFloat(),
            policyImpact2.foreignPolicy.toFloat()
        )).thenReturn(mockPrediction)
        
        // Act - Send first policy change
        viewModel.handleIntent(ForecastIntent.GeneratePrediction(policyImpact1))
        
        // Immediately send second policy change (should be debounced)
        viewModel.handleIntent(ForecastIntent.GeneratePrediction(policyImpact2))
        
        // Advance time by 100ms (less than debounce time)
        advanceTimeBy(100)
        
        // Assert - Predictor should not have been called yet
        verify(mockEconomyPredictor, times(0)).predictTrajectory(
            policyImpact2.taxRate.toFloat(),
            policyImpact2.infrastructure.toFloat(),
            policyImpact2.education.toFloat(),
            policyImpact2.foreignPolicy.toFloat()
        )
        
        // Advance time by another 100ms (total 200ms, more than debounce time)
        advanceTimeBy(100)
        
        // Assert - Predictor should have been called with the latest policy
        verify(mockEconomyPredictor).predictTrajectory(
            policyImpact2.taxRate.toFloat(),
            policyImpact2.infrastructure.toFloat(),
            policyImpact2.education.toFloat(),
            policyImpact2.foreignPolicy.toFloat()
        )
        
        // And state should be updated
        assertThat(viewModel.state.predictedGdp).hasSize(50)
        assertThat(viewModel.state.predictedGdp[0]).isEqualTo(150.0f)
    }

    @Test
    fun `duplicate policy changes are filtered out by distinctUntilChanged`() = runTest {
        // Arrange
        val policyImpact = PolicyImpact(taxRate = 0.6, infrastructure = 0.7, education = 0.8, foreignPolicy = 0.9)
        val mockPrediction = FloatArray(50) { 200.0f }
        
        `when`(mockEconomyPredictor.predictTrajectory(
            policyImpact.taxRate.toFloat(),
            policyImpact.infrastructure.toFloat(),
            policyImpact.education.toFloat(),
            policyImpact.foreignPolicy.toFloat()
        )).thenReturn(mockPrediction)
        
        // Act - Send same policy change twice
        viewModel.handleIntent(ForecastIntent.GeneratePrediction(policyImpact))
        viewModel.handleIntent(ForecastIntent.GeneratePrediction(policyImpact)) // Duplicate
        
        // Advance time to trigger debounce
        advanceTimeBy(150)
        
        // Assert - Predictor should have been called only once due to distinctUntilChanged
        verify(mockEconomyPredictor, times(1)).predictTrajectory(
            policyImpact.taxRate.toFloat(),
            policyImpact.infrastructure.toFloat(),
            policyImpact.education.toFloat(),
            policyImpact.foreignPolicy.toFloat()
        )
    }
}