# TFLite
-keep class org.tensorflow.** { *; }

# SceneView / ARCore
-keep class com.google.ar.** { *; }
-keep class io.github.sceneview.** { *; }
-keep class com.google.android.filament.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep class com.bharatsight2075.di.** { *; }

# Moshi
-keep class com.squareup.moshi.** { *; }
-keepclassmembers class * {
    @com.squareup.moshi.Json *;
}

# Room
-keep class androidx.room.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.coroutines.android.HandlerContext {
    val handler;
}
