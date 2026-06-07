package com.bharatsight2075.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class UserProfile(
    val name: String,
    val rank: String,
    val accuracy: Float,
    val scenariosSaved: Int,
    val watchlistedStates: Int
)

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _profile = MutableStateFlow(UserProfile(
        name = "PAVAN_HUD_OPERATOR",
        rank = "SENIOR ANALYST (L-V7)",
        accuracy = 94.2f,
        scenariosSaved = 12,
        watchlistedStates = 8
    ))
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()
}
