package com.bharatsight2075.ui.maps

import android.content.Context
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import com.bharatsight2075.R
import org.json.JSONObject

object IndiaMapGeoParser {
    private const val INDIA_MIN_LAT = 7.5f
    private const val INDIA_MAX_LAT = 37.6f
    private const val INDIA_MIN_LNG = 66.0f
    private const val INDIA_MAX_LNG = 97.5f

    private var cachedPaths: Map<String, Path>? = null
    private var lastWidth = 0f
    private var lastHeight = 0f

    fun lngToX(lng: Float, w: Float) = ((lng - INDIA_MIN_LNG) / (INDIA_MAX_LNG - INDIA_MIN_LNG)) * w
    fun latToY(lat: Float, h: Float) = ((INDIA_MAX_LAT - lat) / (INDIA_MAX_LAT - INDIA_MIN_LAT)) * h

    fun parseStatePaths(context: Context, canvasWidth: Float, canvasHeight: Float): Map<String, Path> {
        if (cachedPaths != null && lastWidth == canvasWidth && lastHeight == canvasHeight) {
            return cachedPaths!!
        }

        val pathsMap = mutableMapOf<String, android.graphics.Path>()
        
        try {
            val jsonString = context.resources.openRawResource(R.raw.india_states).bufferedReader().use { it.readText() }
            val geoJson = JSONObject(jsonString)
            val features = geoJson.getJSONArray("features")

            for (i in 0 until features.length()) {
                val feature = features.getJSONObject(i)
                val properties = feature.getJSONObject("properties")
                var stateId = properties.optString("ST_NM", "UNKNOWN") // GeoJSON ST_NM is usually state name
                
                // Map state names to IDs used in the app if necessary
                stateId = mapStateNameToId(stateId)

                val geometry = feature.getJSONObject("geometry")
                val type = geometry.getString("type")
                val coordinates = geometry.getJSONArray("coordinates")

                val statePath = pathsMap.getOrPut(stateId) { android.graphics.Path() }

                when (type) {
                    "Polygon" -> {
                        addPolygonToPath(coordinates, statePath, canvasWidth, canvasHeight)
                    }
                    "MultiPolygon" -> {
                        for (j in 0 until coordinates.length()) {
                            addPolygonToPath(coordinates.getJSONArray(j), statePath, canvasWidth, canvasHeight)
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val composePaths = pathsMap.mapValues { it.value.asComposePath() }
        cachedPaths = composePaths
        lastWidth = canvasWidth
        lastHeight = canvasHeight
        return composePaths
    }

    private fun addPolygonToPath(polygon: org.json.JSONArray, path: android.graphics.Path, w: Float, h: Float) {
        for (i in 0 until polygon.length()) {
            val ring = polygon.getJSONArray(i)
            for (j in 0 until ring.length()) {
                val coord = ring.getJSONArray(j)
                val lng = coord.getDouble(0).toFloat()
                val lat = coord.getDouble(1).toFloat()
                val x = lngToX(lng, w)
                val y = latToY(lat, h)

                if (j == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
        }
    }

    fun mapStateNameToId(name: String): String {
        return when (name.uppercase()) {
            "JAMMU & KASHMIR" -> "JK"
            "LADAKH" -> "LA"
            "RAJASTHAN" -> "RJ"
            "MAHARASHTRA" -> "MH"
            "GUJARAT" -> "GJ"
            "KARNATAKA" -> "KA"
            "ANDHRA PRADESH" -> "AP"
            "TAMIL NADU" -> "TN"
            "TELANGANA" -> "TG"
            "MADHYA PRADESH" -> "MP"
            "UTTAR PRADESH" -> "UP"
            "BIHAR" -> "BR"
            "WEST BENGAL" -> "WB"
            "ODISHA" -> "OR"
            "KERALA" -> "KL"
            "ASSAM" -> "AS"
            "PUNJAB" -> "PB"
            "HARYANA" -> "HR"
            "CHHATTISGARH" -> "CT"
            "JHARKHAND" -> "JH"
            "UTTARAKHAND" -> "UT"
            "HIMACHAL PRADESH" -> "HP"
            "TRIPURA" -> "TR"
            "MEGHALAYA" -> "ML"
            "MANIPUR" -> "MN"
            "NAGALAND" -> "NL"
            "GOA" -> "GA"
            "ARUNACHAL PRADESH" -> "AR"
            "MIZORAM" -> "MZ"
            "SIKKIM" -> "SK"
            "DELHI" -> "DL"
            "PUDUCHERRY" -> "PY"
            "CHANDIGARH" -> "CH"
            "ANDAMAN & NICOBAR ISLANDS" -> "AN"
            "DADRA & NAGAR HAVELI AND DAMAN & DIU" -> "DN"
            "LAKSHADWEEP" -> "LD"
            else -> name
        }
    }
}
