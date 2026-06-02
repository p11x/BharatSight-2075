# BharatSight 2075 🇮🇳

BharatSight 2075 is a state-of-the-art Android application for immersive economic forecasting and high-fidelity data visualization. Designed as a futuristic "Economic Observatory," the app provides data-dense dashboards, interactive 3D environments, and AI-powered insights to project India's economic trajectory up to the year 2075.

![Banner](https://github.com/p11x/BharatSight-2075/raw/main/assets/banner.png) *(Placeholder)*

## 🚀 Key Features

### 1. **Immersive 3D Visualizations**
- **Interactive Globe**: Explore India's 36 states and UTs with animated trade flow arcs and GSDP projections.
- **GDP Terrain**: A 3D wireframe landscape where elevation represents sectoral economic output.
- **Economic Orrery**: A solar-system-style visualization where market sectors orbit a central Rupee Sun.
- **Cash Flow River**: Particle-based simulation of revenue streams merging into national GDP.

### 2. **AI & Predictive Analytics**
- **Gemini Nano Engine**: On-device natural language query console for complex economic scenario analysis.
- **Multi-Output TFLite Model**: Simultaneously forecasts GDP, Inflation, Unemployment, Gini Coefficient, and HDI.
- **Monte Carlo Simulations**: Calculates P10–P90 confidence intervals for high-precision projections.
- **What-If Sliders**: Instantly recalculate trajectories by adjusting Tax Rate, Infra Spend, Education, and Foreign Policy.

### 3. **Real-Time Market Telemetry**
- **Live HUD Ticker**: Persistent scrolling ticker for Sensex, Nifty 50, USD/INR, Gold, and Crude Oil.
- **Live Market Updates**: Real-time feeds from RBI, NSE/BSE, and World Bank via unified WebSockets.
- **Nifty 500 Heatmap**: professional squarified treemap with a 7-step diverging return scale.

### 4. **Macro & Trade Observatory**
- **Command Center**: Circular dashboard with 12 orbiting macro indicator panels (CPI, IIP, PMI, CAD, etc.).
- **Trade Network Flow**: Force-directed graph of India's top 30 trading partners with animated flow lines.
- **Sankey Corridors**: Visualizes source-to-destination flows for FDI and trade routes.

### 5. **State-Level Deep Dive**
- **Encrypted Database**: SQLCipher-protected Room DB containing 25+ years of data for all Indian states.
- **State Profile Cards**: Radar charts and trend sparklines for regional economic health.
- **Population Pyramid**: Animated demographic projection from 2001 to 2075.

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose (Advanced Canvas, Shaders, Projections)
- **Architecture**: MVVM + Hilt (Dependency Injection)
- **ML Engine**: TensorFlow Lite (Inference), Gemini Nano (AI Query)
- **3D Engine**: SceneView (Filament/OpenGL ES), ARCore
- **Data Persistence**: Room (Encrypted with SQLCipher), Paging 3, DataStore
- **Networking**: Retrofit, OkHttp WebSockets
- **Monitoring**: Firebase Crashlytics & Performance

## 🎨 Design System

BharatSight 2075 features a data-dense, sci-fi aesthetic inspired by HUD interfaces:
- **Themes**: 
  - 🟦 **Cyberpunk**: Primary Cyan (#00F5FF), Accent Orange (#FF6B35)
  - 🟪 **Hologram**: Primary Ice Blue (#4FC3F7), Accent Purple (#B39DDB)
- **Visuals**: Glowing neon borders, vertical area gradients, soft shadow halos, and staggered entrance animations.

## 📦 Project Structure

- `:app`: Main Android application module.
- `:visualizations`: Reusable library containing 25+ pure Compose Canvas chart components.
- `:prediction_engine`: Dynamic feature module for heavy ML inference and model hot-swapping.

## 🛠 Installation & Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/p11x/BharatSight-2075.git
   ```
2. Open in Android Studio **Ladybug** or newer.
3. Add your `google-services.json` to the `app/` directory.
4. Build and Run on a device with **API 26 (Android 8.0)** or higher.

## 📜 License

This project is licensed under the MIT License.

---
*BharatSight 2075 — Engineering the Future of Economic Intelligence.*
