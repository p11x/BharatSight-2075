package com.bharatsight2075.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharatsight2075.data.local.StateEconomyDao
import com.bharatsight2075.data.local.StateEconomyEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StateEconomyViewModel @Inject constructor(
    private val stateEconomyDao: StateEconomyDao
) : ViewModel() {

    private val _states = MutableStateFlow<List<StateEconomyEntity>>(emptyList())
    val states = _states.asStateFlow()

    init {
        loadStates()
    }

    private fun loadStates() {
        viewModelScope.launch {
            stateEconomyDao.getAllStates().collect {
                _states.value = it
            }
        }
    }
}
