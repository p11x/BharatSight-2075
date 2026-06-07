package com.bharatsight2075

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.bharatsight2075.service.LiveEconomyDataService
import com.bharatsight2075.ui.navigation.AppNavHost
import com.bharatsight2075.ui.theme.RetroDarkTheme
import com.bharatsight2075.ui.theme.GridBackgroundSurface
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var liveDataService: LiveEconomyDataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Prevent ANR on heavy screens by pre-warming coroutine dispatcher
        lifecycleScope.launch(Dispatchers.IO) {
            // Pre-warm IO thread pool
        }
        enableEdgeToEdge()
        setContent {
            RetroDarkTheme {
                GridBackgroundSurface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(liveDataService = liveDataService)
                }
            }
        }
    }
}
