# Walkthrough - Map Rendering and ANR Fixes

I have implemented fixes for the ANR issues and map rendering artifacts in BharatSight 2075.

## Changes Made

### 1. ANR Fix: Background Path Building
- **[IndiaMapViewModel.kt](file:///C:/Users/p11x/Desktop/Android Apps/BharatSight 2075/app/src/main/java/com/bharatsight2075/viewmodel/IndiaMapViewModel.kt)**: Introduced `MapPathCache` and a `buildPathCache` function that uses `Dispatchers.Default` to build map paths off the UI thread.
- **[NeonIndiaMap.kt](file:///C:/Users/p11x/Desktop/Android Apps/BharatSight 2075/app/src/main/java/com/bharatsight2075/ui/maps/NeonIndiaMap.kt)**: Updated to consume pre-built paths from the ViewModel's `pathCache` StateFlow. Path rebuilding is now triggered by `onSizeChanged`.

### 2. Map Rendering: Official Boundary Cleanup
- **[NeonIndiaMap.kt](file:///C:/Users/p11x/Desktop/Android Apps/BharatSight 2075/app/src/main/java/com/bharatsight2075/ui/maps/NeonIndiaMap.kt)**: Removed the redundant `showOfficialBoundary` stroke drawing which was causing an extra top line artifact in the northern region. The state borders now provide the correct outline.

### 3. Territory Fix: Solid POK & Aksai Chin
- **[IndiaOfficialBoundary.kt](file:///C:/Users/p11x/Desktop/Android Apps/BharatSight 2075/app/src/main/java/com/bharatsight2075/ui/maps/IndiaOfficialBoundary.kt)**: Updated with `POK_SOLID` and `AKSAI_CHIN_SOLID` coordinates.
- **[NeonIndiaMap.kt](file:///C:/Users/p11x/Desktop/Android Apps/BharatSight 2075/app/src/main/java/com/bharatsight2075/ui/maps/NeonIndiaMap.kt)**: These regions are now drawn with the same fill and glowing border style as other states, ensuring they appear as integral parts of India's territory.

### 4. Integration
- Updated all call sites of `NeonIndiaMap` in `MapsScreen.kt` and `India3DGlobeScreen.kt` to pass the `IndiaMapViewModel`.

## Verification Results

### Manual Verification
- **Performance**: Verified that `buildPathCache` runs on a background thread, preventing UI freezes (ANRs) during map rendering.
- **Visuals**: Confirmed the "extra top line" is gone and POK/Aksai Chin render as solid, glowing territory.
- **Bounding Box**: Confirmed `IndiaGeoJsonParser.kt` uses `LON_MIN = 66.0` and `LAT_MAX = 37.6` to correctly frame the northern and western extents.
