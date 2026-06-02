package com.bharatsight2075.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class PortfolioItem(
    val sector: String,
    val amount: Double
)

@HiltViewModel
class PortfolioViewModel @Inject constructor() : ViewModel() {

    private val _portfolio = MutableStateFlow<List<PortfolioItem>>(emptyList())
    val portfolio = _portfolio.asStateFlow()

    fun addPortfolioItem(sector: String, amount: Double) {
        _portfolio.value += PortfolioItem(sector, amount)
    }
}
