package com.bharatsight2075.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bharatsight2075.service.LiveEconomyDataService
import com.bharatsight2075.ui.components.BharatSightBottomNav
import com.bharatsight2075.ui.forecast.Forecaster2075Screen
import com.bharatsight2075.ui.screens.*
import com.bharatsight2075.ui.screens.three_d.India3DGlobeScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    liveDataService: LiveEconomyDataService
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val marketData by liveDataService.marketData.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    val bottomNavRoutes = listOf(
        Routes.HOME, Routes.COMPARE, Routes.UPDATES, Routes.ASK, Routes.PROFILE
    )
    val showBottomNav = currentRoute in bottomNavRoutes

    val navigateBottomNav = { route: String ->
        if (currentRoute != route) {
            navController.navigate(route) {
                popUpTo(Routes.HOME) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AdvancedTelemetryDrawer(
                selectedRoute = currentRoute ?: Routes.HOME,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomNav,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    BharatSightBottomNav(
                        currentRoute = currentRoute,
                        onNavigate = navigateBottomNav,
                        hasNewUpdates = true // Mocked
                    )
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.SPLASH,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                enterTransition = {
                    if (targetState.destination.route in bottomNavRoutes) {
                        fadeIn(tween(200))
                    } else {
                        slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(400)) + fadeIn(tween(400))
                    }
                },
                exitTransition = {
                    if (initialState.destination.route in bottomNavRoutes) {
                        fadeOut(tween(200))
                    } else {
                        slideOutHorizontally(targetOffsetX = { -it / 3 }, animationSpec = tween(400)) + fadeOut(tween(400))
                    }
                }
            ) {
                composable(Routes.SPLASH) {
                    SplashScreen(onFinishSplash = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    })
                }
                
                // Bottom Nav Destinations
                composable(Routes.HOME) {
                    HomeScreen(
                        onNavigate = { navController.navigate(it) },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onSettingsClick = { navController.navigate(Routes.SETTINGS) }
                    )
                }
                composable(Routes.COMPARE) {
                    CompareScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.UPDATES) {
                    UpdatesScreen(navController = navController)
                }
                composable(Routes.ASK) {
                    AskScreen(navController = navController)
                }
                composable(Routes.PROFILE) {
                    ProfileScreen(navController = navController)
                }

                // Section Screens
                composable(Routes.MACRO) {
                    MacroOverviewScreen(
                        navController = navController,
                        marketData = marketData,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Routes.SECTOR) {
                    SectorDeepDiveScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.DEMOGRAPHICS) {
                    DemographicsScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.FORECASTER) {
                    Forecaster2075Screen(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.SETTINGS) {
                    SettingsScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.GLOBE) {
                    India3DGlobeScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.QUERY) {
                    QueryConsoleScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.OBSERVATORY) {
                    MacroIndicatorObservatory(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.TRADE) {
                    TradeNetworkScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.MARKET) {
                    SectorStockHeatmapScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                
                // New Sector Routes
                composable(Routes.AGRICULTURE) { AgricultureScreen(navController) }
                composable(Routes.BANKING) { BankingScreen(navController) }
                composable(Routes.ENERGY) { EnergyScreen(navController) }
                composable(Routes.SMART_CITIES) { SmartCitiesScreen(navController) }
                composable(Routes.STARTUP) { StartupScreen(navController) }
                composable(Routes.DEFENCE) { DefenceScreen(navController) }
                composable(Routes.CLIMATE) { ClimateScreen(navController) }
                composable(Routes.DIGITAL_ECONOMY) { DigitalEconomyScreen(navController) }
                composable(Routes.EDUCATION) { EducationScreen(navController) }
                composable(Routes.HEALTHCARE) { HealthcareScreen(navController) }
                composable(Routes.REAL_ESTATE) { RealEstateScreen(navController) }
                composable(Routes.TOURISM) { TourismScreen(navController) }
                composable(Routes.SPACE_TECH) { SpaceTechScreen(navController) }
                composable(Routes.GEO_RISK) { GeoRiskScreen(navController) }
                composable(Routes.INEQUALITY) { InequalityScreen(navController) }
                composable(Routes.LABOUR) { LabourScreen(navController) }
                composable(Routes.LOGISTICS) { LogisticsScreen(navController) }
                composable(Routes.MEDIA) { MediaScreen(navController) }
                composable(Routes.NATURAL_RESOURCES) { NaturalResourcesScreen(navController) }
                composable(Routes.SOFT_POWER) { SoftPowerScreen(navController) }

                // DRILL-DOWN DETAIL SYSTEM
                composable(
                    route = Routes.CHART_DETAIL,
                    arguments = listOf(navArgument("chartId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val chartId = backStackEntry.arguments?.getString("chartId") ?: ""
                    ChartDetailScreen(navController = navController, chartId = chartId)
                }
            }
        }
    }
}
