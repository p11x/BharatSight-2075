package com.bharatsight2075.ui.maps

import android.content.Context
import android.graphics.Path as AndroidPath
import org.json.JSONObject
import org.json.JSONArray
import com.bharatsight2075.R

data class StateShape(
  val name: String,
  // Each polygon ring is a list of [lon, lat] pairs
  // A state may have multiple polygons (MultiPolygon)
  val rings: List<List<Pair<Double, Double>>>
)

object IndiaGeoJsonParser {

  // Bounding box of India (WGS84)
  private const val LON_MIN = 66.0
  private const val LON_MAX = 97.5
  private const val LAT_MIN =  7.5
  private const val LAT_MAX = 37.6

  /**
   * Parse the bundled GeoJSON and return a list of StateShape objects.
   * Call this once from a ViewModel coroutine on Dispatchers.IO.
   */
  fun parse(context: Context): List<StateShape> {
    val json = context.resources.openRawResource(R.raw.india_states)
      .bufferedReader().use { it.readText() }
    val root = JSONObject(json)
    val features = root.getJSONArray("features")
    val states = mutableListOf<StateShape>()

    for (i in 0 until features.length()) {
      val feature = features.getJSONObject(i)
      val props = feature.getJSONObject("properties")
      // Try common property name variations
      val name = props.optString("NAME_1")
        .ifEmpty { props.optString("ST_NM") }
        .ifEmpty { props.optString("name") }
        .ifEmpty { props.optString("NAME") }
        .ifEmpty { "Unknown" }

      val geometry = feature.getJSONObject("geometry")
      val type = geometry.getString("type")
      val coords = geometry.getJSONArray("coordinates")

      val rings = mutableListOf<List<Pair<Double, Double>>>()

      when (type) {
        "Polygon" -> {
          // coords[0] is the outer ring; coords[1..] are holes (we skip holes)
          rings.add(parseRing(coords.getJSONArray(0)))
        }
        "MultiPolygon" -> {
          // coords[i][0] is the outer ring of each polygon
          for (p in 0 until coords.length()) {
            val polygon = coords.getJSONArray(p)
            rings.add(parseRing(polygon.getJSONArray(0)))
          }
        }
      }
      if (rings.isNotEmpty()) states.add(StateShape(name, rings))
    }
    return states
  }

  private fun parseRing(ring: JSONArray): List<Pair<Double, Double>> {
    val pts = mutableListOf<Pair<Double, Double>>()
    for (i in 0 until ring.length()) {
      val pt = ring.getJSONArray(i)
      val lon = pt.getDouble(0)
      val lat = pt.getDouble(1)
      pts.add(lon to lat)
    }
    return pts
  }

  /**
   * Project a (lon, lat) coordinate to canvas pixel space.
   * Uses simple equirectangular projection normalized to canvas size.
   *
   * @param lon  longitude in WGS84 degrees
   * @param lat  latitude  in WGS84 degrees
   * @param canvasW  canvas width in pixels
   * @param canvasH  canvas height in pixels
   * @param paddingFraction  fractional padding on each side (default 0.04 = 4%)
   */
  fun project(
    lon: Double, lat: Double,
    canvasW: Float, canvasH: Float,
    paddingFraction: Float = 0.04f
  ): Pair<Float, Float> {
    val padX = canvasW * paddingFraction
    val padY = canvasH * paddingFraction
    val drawW = canvasW - 2 * padX
    val drawH = canvasH - 2 * padY

    // Normalise lon/lat to 0..1
    val nx = ((lon - LON_MIN) / (LON_MAX - LON_MIN)).coerceIn(0.0, 1.0).toFloat()
    val ny = (1.0 - (lat - LAT_MIN) / (LAT_MAX - LAT_MIN)).coerceIn(0.0, 1.0).toFloat()

    // Scale to drawable area + padding offset
    val x = padX + nx * drawW
    val y = padY + ny * drawH
    return x to y
  }

  /**
   * Build an AndroidPath for a single ring, projected to canvas size.
   */
  fun ringToPath(
    ring: List<Pair<Double, Double>>,
    canvasW: Float, canvasH: Float
  ): AndroidPath {
    val path = AndroidPath()
    ring.forEachIndexed { i, (lon, lat) ->
      val (x, y) = project(lon, lat, canvasW, canvasH)
      if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    return path
  }
}
