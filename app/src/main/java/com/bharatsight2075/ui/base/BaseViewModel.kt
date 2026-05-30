package com.bharatsight2075.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

interface Intent

interface State

abstract class BaseViewModel<Intention : Intent, UiState : State> : ViewModel() {
    private val intentChannel = Channel<Intention>(Channel.UNLIMITED)
    
    private val _state = MutableStateFlow(createInitialState())
    val state: StateFlow<UiState> = _state
    
    init {
        handleIntents()
    }
    
    protected abstract fun createInitialState(): UiState
    abstract fun handleIntent(intent: Intention)
    
    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                handleIntent(intent)
            }
        }
    }
    
    fun setState(reducer: UiState.() -> UiState) {
        _state.value = reducer(_state.value)
    }
    
    fun postIntent(intent: Intention) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }
}