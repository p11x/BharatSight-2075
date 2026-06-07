# TFLite
-keep class org.tensorflow.** { *; }
-keep class org.tensorflow.lite.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao interface *

# Moshi / Retrofit
-keep class com.squareup.moshi.** { *; }
-keepclassmembers class ** { @com.squareup.moshi.FromJson *; @com.squareup.moshi.ToJson *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}

# SceneView / ARCore (if used)
-keep class com.google.ar.** { *; }
-keep class io.github.sceneview.** { *; }

# App models
-keep class com.bharatsight2075.data.** { *; }
-keep class com.bharatsight2075.ui.maps.** { *; }

# GeoJSON parsing
-keep class org.json.** { *; }

# Generative AI
-keep class com.google.ai.** { *; }