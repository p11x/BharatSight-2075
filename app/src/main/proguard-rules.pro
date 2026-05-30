# R8 / ProGuard Rules for Production Release

# TensorFlow Lite - Keep all TFLite classes
-keep class org.tensorflow.lite.** { *; }
-keep class org.tensorflow.lite.support.** { *; }
-dontwarn org.tensorflow.lite.**
-dontwarn org.tensorflow.lite.support.**

# Room Database - Keep entities, DAOs, and database classes
-keep @androidx.room.Database class *
-keep @androidx.room.Dao class *
-keep @androidx.room.Entity class *
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# MVI State and Intent classes - Keep from obfuscation
-keep class com.bharatsight2075.**$State { *; }
-keep class com.bharatsight2075.**$Intent { *; }
-keep class com.bharatsight2075.**$ForecastState { *; }
-keep class com.bharatsight2075.**$ForecastIntent { *; }

# Visualization data models
-keep class com.bharatsight2075.ui.visualization.** { *; }
-keep class com.bharatsight2075.ui.visualization.**$* { *; }

# Hilt dependency injection
-keep class dagger.hilt.** { *; }
-keep class androidx.hilt.** { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }
-dontwarn dagger.hilt.**

# Kotlin Coroutines
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# Moshi JSON
-keep class com.squareup.moshi.** { *; }
-keep class com.squareup.moshi.kotlin.** { *; }
-dontwarn com.squareup.moshi.**

# Disable logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# Native method bindings
-keepclasseswithmembernames class * {
    native <methods>;
}

# Generic Android support
-keep class android.** { *; }
-keep class androidx.** { *; }