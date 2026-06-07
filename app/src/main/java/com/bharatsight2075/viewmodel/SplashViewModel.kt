package com.bharatsight2075.viewmodel

import androidx.lifecycle.ViewModel
import com.bharatsight2075.util.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    val isLoggedIn: Flow<Boolean> = preferenceManager.isLoggedIn
}
