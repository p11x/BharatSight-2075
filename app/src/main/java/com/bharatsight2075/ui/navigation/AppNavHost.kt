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
            BharatSightSideDrawer(
                selectedRoute = currentRoute ?: Routes.HOME,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch { drawerState.close() }
                },
                drawerState = drawerState,
                scope = scope
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
                    SplashScreen(onComplete = { isLoggedIn ->
                        val dest = if (isLoggedIn) Routes.HOME else Routes.LOGIN
                        navController.navigate(dest) {
                            popUpTo(Routes.SPLASH) { inclusive = true }
                        }
                    })
                }

                composable(Routes.LOGIN) {
                    LoginScreen(navController)
                }

                composable(Routes.WELCOME) {
                    WelcomeScreen(navController)
                }
                
                // Bottom Nav Destinations
                composable(Routes.HOME) {
                    HomeScreen(
                        navController = navController,
                        onNavigate = { navController.navigate(it) },
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onSettingsClick = { navController.navigate(Routes.SETTINGS) }
                    )
                }
                composable(Routes.COMPARE) {
                    CompareScreen(navController = navController, onBack = { navController.popBackStack() })
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
                composable(Routes.MACRO_OVERVIEW) {
                    MacroOverviewScreen(
                        navController = navController,
                        marketData = marketData,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(Routes.SECTOR_DIVE) {
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
                composable(Routes.MAPS) {
                    MapsScreen(navController = navController)
                }
                composable(Routes.INDIA_GLOBE) {
                    India3DGlobeScreen(navController = navController)
                }
                composable(Routes.QUERY) {
                    QueryConsoleScreen(onBack = { navController.popBackStack() })
                }
                composable(Routes.MACRO_HUB) {
                    MacroIndicatorObservatory(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.TRADE_NETWORK) {
                    TradeNetworkScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.STOCK_HEATMAP) {
                    SectorStockHeatmapScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                composable(Routes.STATE_DIVE) {
                    StateComparisonScreen(navController = navController, onBack = { navController.popBackStack() })
                }
                
                // New Sector Routes
                composable(Routes.AGRICULTURE) { AgricultureScreen(navController = navController) }
                composable(Routes.BANKING) { BankingScreen(navController = navController) }
                composable(Routes.ENERGY) { EnergyScreen(navController = navController) }
                composable(Routes.SMART_CITIES) { SmartCitiesScreen(navController = navController) }
                composable(Routes.STARTUPS) { StartupScreen(navController = navController) }
                composable(Routes.DEFENCE) { DefenceScreen(navController = navController) }
                composable(Routes.CLIMATE) { ClimateScreen(navController = navController) }
                composable(Routes.DIGITAL_ECONOMY) { DigitalEconomyScreen(navController = navController) }
                composable(Routes.EDUCATION) { EducationScreen(navController = navController) }
                composable(Routes.HEALTHCARE) { HealthcareScreen(navController = navController) }
                composable(Routes.REAL_ESTATE) { RealEstateScreen(navController = navController) }
                composable(Routes.TOURISM) { TourismScreen(navController = navController) }
                composable(Routes.SPACE_TECH) { SpaceTechScreen(navController = navController) }
                composable(Routes.GEO_RISK) { GeoRiskScreen(navController = navController) }
                composable(Routes.INEQUALITY) { InequalityScreen(navController = navController) }
                composable(Routes.LABOUR) { LabourScreen(navController = navController) }
                composable(Routes.LOGISTICS) { LogisticsScreen(navController = navController) }
                composable(Routes.MEDIA) { MediaScreen(navController = navController) }
                composable(Routes.NATURAL_RESOURCES) { NaturalResourcesScreen(navController = navController) }
                composable(Routes.SOFT_POWER) { SoftPowerScreen(navController = navController) }

                // DRILL-DOWN DETAIL SYSTEM
                composable(
                    route = Routes.CHART_DETAIL,
                    arguments = listOf(navArgument("chartId") { type = NavType.StringType; defaultValue = "" })
                ) {
                    ChartDetailScreen(navController = navController)
                }
            }
        }
    }
}
