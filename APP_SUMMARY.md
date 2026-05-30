# BharatSight 2075 - Comprehensive Application Summary (Updated)

## Overview
BharatSight 2075 is an elite Android application for economic forecasting and visualization, featuring a futuristic holographic UI and a state-of-the-art predictive engine. It leverages machine learning and real-time data to project India's economic trajectory up to 2075.

## Purpose
The application serves as an immersive economic observatory that:
- **Predicts Multi-Variable Trajectories**: GDP, Inflation, Unemployment, Gini Coefficient, and HDI (2026-2075).
- **Visualizes 3D Economic Data**: Interactive globes, terrain landscapes, and orbital orreries.
- **Provides Real-Time Insights**: Live NSE/BSE and RBI data feeds with a persistent HUD ticker.
- **Enables AI Interaction**: Gemini Nano-powered natural language query engine for scenario analysis.
- **Deep-Drills into States**: Comprehensive historical and predicted data for all 36 Indian States/UTs.

## Technologies Used

### Core Framework & UI
- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose (Advanced Canvas & Projections)
- **3D Engine**: SceneView / OpenGL ES (Interactive Globe & Landscapes)
- **Architecture**: MVVM with Hilt (Flow-exclusive data streams)

### Key Libraries & Dependencies
- **Machine Learning**: TensorFlow Lite (Multi-output prediction), Gemini Nano (AI Query Engine)
- **Data Persistence**: Room Database (Encrypted with SQLCipher)
- **Networking**: Retrofit & OkHttp WebSockets (Real-time market data)
- **Security**: BiometricPrompt API (Scenario locking)
- **Monitoring**: Firebase Crashlytics & Performance Monitoring

## Application Components

### 1. 3D Visualization Suite
- **India3DGlobeScreen.kt**: Interactive 3D globe with trade arc animations and state node projection.
- **GDP3DLandscapeView.kt**: 3D elevation terrain where height corresponds to sector growth.
- **SectorOrbitModel.kt**: Orbital orrery system representing market cap and growth momentum.
- **CashFlowRiverAnimation.kt**: Particle-system river visualizing revenue flows.

### 2. AI & Query Engine
- **EconomyQueryViewModel.kt**: Manages on-device Gemini Nano inference.
- **QueryConsoleScreen.kt**: Terminal-style interface for natural language economic queries.
- **VoiceQueryFab.kt**: Speech-to-text integration for AI queries.

### 3. Real-Time Observatories
- **LiveEconomyDataService.kt**: WebSocket service for live financial feeds with exponential backoff.
- **TickerTapeOverlay.kt**: Persistent scrolling HUD for market indices (Sensex, Nifty, etc.).
- **MacroIndicatorObservatory.kt**: Circular "command center" with 12 orbiting indicator panels.

### 4. Advanced 2D Visualizations
- **SankeyFlowChart.kt**: GDP expenditure flow visualization.
- **PyramidPopulationChart.kt**: Animated demographic pyramid (2001–2075 projection).
- **SectorStockHeatmapScreen.kt**: Nifty 500 treemap with live performance-based coloring.
- **ConfidenceIntervalRenderer.kt**: Shaded uncertainty bands for ML predictions.

### 5. Data & Security Layer
- **StateEconomyDatabase.kt**: Encrypted Room database for state-level drill-down.
- **DataFreshnessManager.kt**: DataStore-based tracking of dataset timestamps and stale warnings.
- **AuthInterceptor.kt**: Secure networking with automated token management.

## Key Features

### 1. Multi-Output Forecasting
- Predicts 5 distinct economic indicators simultaneously using an upgraded TFLite engine.
- Supports Monte Carlo simulations for calculating P10-P90 confidence intervals.

### 2. State-Level Analytics
- Profile cards for each state with mini radar charts, sparklines, and peer ranking.
- Lightweight state-specific forecasting for GSDP projections.

### 3. Geopolitical Risk & Trade
- **TradeNetworkScreen.kt**: Force-directed graph showing trade volume and surplus/deficit with global partners.
- **GeopoliticalRiskOverlay.kt**: Pulsing risk event markers integrated with global trade views.

### 4. User Personalization
- Watchlists for macro indicators and sectors.
- Portfolio Overlap Analyzer: Computes sectoral exposure risks based on macro forecasts.

## Conclusion
BharatSight 2075 represents the pinnacle of Android economic visualization, combining cutting-edge AI, real-time telemetry, and interactive 3D environments into a cohesive futuristic experience.
