package com.bharatsight2075.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class CountryMetric(
    val name: String,
    val gdp: Float,
    val growth: Float,
    val inflation: Float,
    val hdi: Float,
    val isPrimary: Boolean = false
)

@HiltViewModel
class CompareViewModel @Inject constructor() : ViewModel() {
    private val _countries = MutableStateFlow(listOf(
        CountryMetric("INDIA", 37.2f, 8.1f, 4.2f, 0.882f, true),
        CountryMetric("USA", 42.1f, 2.3f, 2.1f, 0.926f),
        CountryMetric("CHINA", 39.5f, 4.2f, 1.8f, 0.812f),
        CountryMetric("GERMANY", 8.4f, 1.5f, 2.0f, 0.942f),
        CountryMetric("JAPAN", 7.2f, 1.2f, 1.5f, 0.925f),
        CountryMetric("UK", 6.8f, 1.8f, 2.2f, 0.932f)
    ))
    val countries: StateFlow<List<CountryMetric>> = _countries.asStateFlow()
}
