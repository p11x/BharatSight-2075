package com.bharatsight2075.ui.maps

data class StatePolygon(
    val id: String,
    val name: String,
    val paths: List<List<Pair<Float, Float>>> // Normalized 0f..1f
)

object OfficialIndiaMapData {
    // Normalized coordinates (0f..1f) for India's 28 states and 8 UTs.
    // Unified J&K + POK, Ladakh + Aksai Chin.
    
    val states = listOf(
        // JAMMU & KASHMIR (Unified with POK)
        StatePolygon(
            id = "JK",
            name = "Jammu & Kashmir",
            paths = listOf(
                listOf(
                    0.25f to 0.12f, 0.28f to 0.10f, 0.32f to 0.08f, 0.35f to 0.10f,
                    0.38f to 0.15f, 0.35f to 0.20f, 0.30f to 0.22f, 0.26f to 0.18f
                )
            )
        ),
        // LADAKH (Unified with Aksai Chin)
        StatePolygon(
            id = "LA",
            name = "Ladakh",
            paths = listOf(
                listOf(
                    0.35f to 0.10f, 0.45f to 0.05f, 0.55f to 0.10f, 0.52f to 0.20f,
                    0.45f to 0.25f, 0.38f to 0.15f
                )
            )
        ),
        // RAJASTHAN
        StatePolygon(
            id = "RJ",
            name = "Rajasthan",
            paths = listOf(
                listOf(
                    0.10f to 0.35f, 0.25f to 0.30f, 0.30f to 0.45f, 0.15f to 0.50f,
                    0.05f to 0.45f
                )
            )
        ),
        // GUJARAT
        StatePolygon(
            id = "GJ",
            name = "Gujarat",
            paths = listOf(
                listOf(
                    0.05f to 0.50f, 0.15f to 0.50f, 0.20f to 0.65f, 0.10f to 0.70f,
                    0.02f to 0.60f
                )
            )
        ),
        // MAHARASHTRA
        StatePolygon(
            id = "MH",
            name = "Maharashtra",
            paths = listOf(
                listOf(
                    0.15f to 0.65f, 0.35f to 0.60f, 0.45f to 0.75f, 0.25f to 0.85f,
                    0.15f to 0.80f
                )
            )
        ),
        // TAMIL NADU
        StatePolygon(
            id = "TN",
            name = "Tamil Nadu",
            paths = listOf(
                listOf(
                    0.35f to 0.85f, 0.45f to 0.82f, 0.50f to 0.95f, 0.40f to 0.98f,
                    0.35f to 0.90f
                )
            )
        ),
        // KERALA
        StatePolygon(
            id = "KL",
            name = "Kerala",
            paths = listOf(
                listOf(
                    0.30f to 0.85f, 0.35f to 0.85f, 0.40f to 0.98f, 0.32f to 0.95f
                )
            )
        ),
        // UTTAR PRADESH
        StatePolygon(
            id = "UP",
            name = "Uttar Pradesh",
            paths = listOf(
                listOf(
                    0.35f to 0.35f, 0.55f to 0.30f, 0.60f to 0.45f, 0.40f to 0.50f
                )
            )
        ),
        // BIHAR
        StatePolygon(
            id = "BR",
            name = "Bihar",
            paths = listOf(
                listOf(
                    0.60f to 0.35f, 0.75f to 0.35f, 0.75f to 0.45f, 0.60f to 0.45f
                )
            )
        ),
        // WEST BENGAL
        StatePolygon(
            id = "WB",
            name = "West Bengal",
            paths = listOf(
                listOf(
                    0.75f to 0.35f, 0.80f to 0.35f, 0.85f to 0.60f, 0.75f to 0.60f
                )
            )
        ),
        // MADHYA PRADESH
        StatePolygon(
            id = "MP",
            name = "Madhya Pradesh",
            paths = listOf(
                listOf(
                    0.30f to 0.45f, 0.50f to 0.45f, 0.55f to 0.60f, 0.35f to 0.60f
                )
            )
        ),
        // KARNATAKA
        StatePolygon(
            id = "KA",
            name = "Karnataka",
            paths = listOf(
                listOf(
                    0.25f to 0.75f, 0.35f to 0.70f, 0.40f to 0.85f, 0.30f to 0.85f
                )
            )
        ),
        // ANDHRA PRADESH
        StatePolygon(
            id = "AP",
            name = "Andhra Pradesh",
            paths = listOf(
                listOf(
                    0.40f to 0.70f, 0.55f to 0.65f, 0.50f to 0.85f, 0.40f to 0.82f
                )
            )
        ),
        // TELANGANA
        StatePolygon(
            id = "TS",
            name = "Telangana",
            paths = listOf(
                listOf(
                    0.38f to 0.65f, 0.50f to 0.65f, 0.48f to 0.75f, 0.40f to 0.75f
                )
            )
        ),
        // ODISHA
        StatePolygon(
            id = "OR",
            name = "Odisha",
            paths = listOf(
                listOf(
                    0.55f to 0.55f, 0.70f to 0.50f, 0.75f to 0.65f, 0.60f to 0.70f
                )
            )
        ),
        // CHHATTISGARH
        StatePolygon(
            id = "CT",
            name = "Chhattisgarh",
            paths = listOf(
                listOf(
                    0.50f to 0.50f, 0.60f to 0.50f, 0.60f to 0.70f, 0.55f to 0.70f
                )
            )
        ),
        // JHARKHAND
        StatePolygon(
            id = "JH",
            name = "Jharkhand",
            paths = listOf(
                listOf(
                    0.60f to 0.45f, 0.75f to 0.45f, 0.75f to 0.55f, 0.60f to 0.55f
                )
            )
        ),
        // ASSAM
        StatePolygon(
            id = "AS",
            name = "Assam",
            paths = listOf(
                listOf(
                    0.85f to 0.35f, 0.95f to 0.35f, 0.95f to 0.45f, 0.85f to 0.45f
                )
            )
        ),
        // ARUNACHAL PRADESH
        StatePolygon(
            id = "AR",
            name = "Arunachal Pradesh",
            paths = listOf(
                listOf(
                    0.88f to 0.25f, 0.98f to 0.25f, 0.98f to 0.35f, 0.85f to 0.35f
                )
            )
        ),
        // PUNJAB
        StatePolygon(
            id = "PB",
            name = "Punjab",
            paths = listOf(
                listOf(
                    0.20f to 0.22f, 0.30f to 0.22f, 0.30f to 0.30f, 0.20f to 0.30f
                )
            )
        ),
        // HARYANA
        StatePolygon(
            id = "HR",
            name = "Haryana",
            paths = listOf(
                listOf(
                    0.25f to 0.30f, 0.35f to 0.30f, 0.35f to 0.38f, 0.25f to 0.38f
                )
            )
        ),
        // HIMACHAL PRADESH
        StatePolygon(
            id = "HP",
            name = "Himachal Pradesh",
            paths = listOf(
                listOf(
                    0.30f to 0.20f, 0.40f to 0.18f, 0.42f to 0.25f, 0.32f to 0.28f
                )
            )
        ),
        // UTTARAKHAND
        StatePolygon(
            id = "UK",
            name = "Uttarakhand",
            paths = listOf(
                listOf(
                    0.40f to 0.25f, 0.50f to 0.25f, 0.48f to 0.32f, 0.38f to 0.32f
                )
            )
        ),
        // SIKKIM
        StatePolygon(
            id = "SK",
            name = "Sikkim",
            paths = listOf(
                listOf(
                    0.78f to 0.30f, 0.82f to 0.30f, 0.82f to 0.35f, 0.78f to 0.35f
                )
            )
        ),
        // GOA
        StatePolygon(
            id = "GA",
            name = "Goa",
            paths = listOf(
                listOf(
                    0.22f to 0.78f, 0.25f to 0.78f, 0.25f to 0.82f, 0.22f to 0.82f
                )
            )
        ),
        // TRIPURA
        StatePolygon(
            id = "TR",
            name = "Tripura",
            paths = listOf(
                listOf(
                    0.88f to 0.50f, 0.92f to 0.50f, 0.92f to 0.55f, 0.88f to 0.55f
                )
            )
        ),
        // MIZORAM
        StatePolygon(
            id = "MZ",
            name = "Mizoram",
            paths = listOf(
                listOf(
                    0.92f to 0.50f, 0.96f to 0.50f, 0.96f to 0.60f, 0.92f to 0.60f
                )
            )
        ),
        // MANIPUR
        StatePolygon(
            id = "MN",
            name = "Manipur",
            paths = listOf(
                listOf(
                    0.92f to 0.42f, 0.98f to 0.42f, 0.98f to 0.50f, 0.92f to 0.50f
                )
            )
        ),
        // NAGALAND
        StatePolygon(
            id = "NL",
            name = "Nagaland",
            paths = listOf(
                listOf(
                    0.94f to 0.35f, 0.98f to 0.35f, 0.98f to 0.42f, 0.94f to 0.42f
                )
            )
        ),
        // MEGHALAYA
        StatePolygon(
            id = "ML",
            name = "Meghalaya",
            paths = listOf(
                listOf(
                    0.85f to 0.42f, 0.92f to 0.42f, 0.92f to 0.48f, 0.85f to 0.48f
                )
            )
        ),
        // DELHI
        StatePolygon(
            id = "DL",
            name = "Delhi",
            paths = listOf(
                listOf(
                    0.32f to 0.35f, 0.35f to 0.35f, 0.35f to 0.38f, 0.32f to 0.38f
                )
            )
        ),
        // CHANDIGARH
        StatePolygon(
            id = "CH",
            name = "Chandigarh",
            paths = listOf(
                listOf(
                    0.28f to 0.28f, 0.30f to 0.28f, 0.30f to 0.30f, 0.28f to 0.30f
                )
            )
        ),
        // PUDUCHERRY
        StatePolygon(
            id = "PY",
            name = "Puducherry",
            paths = listOf(
                listOf(
                    0.45f to 0.88f, 0.47f to 0.88f, 0.47f to 0.90f, 0.45f to 0.90f
                )
            )
        ),
        // LAKSHADWEEP
        StatePolygon(
            id = "LD",
            name = "Lakshadweep",
            paths = listOf(
                listOf(
                    0.15f to 0.85f, 0.20f to 0.85f, 0.20f to 0.90f, 0.15f to 0.90f
                )
            )
        ),
        // ANDAMAN & NICOBAR
        StatePolygon(
            id = "AN",
            name = "Andaman & Nicobar",
            paths = listOf(
                listOf(
                    0.85f to 0.80f, 0.90f to 0.80f, 0.90f to 0.95f, 0.85f to 0.95f
                )
            )
        ),
        // DADRA & NAGAR HAVELI AND DAMAN & DIU
        StatePolygon(
            id = "DN",
            name = "Dadra & Nagar Haveli and Daman & Diu",
            paths = listOf(
                listOf(
                    0.10f to 0.65f, 0.14f to 0.65f, 0.14f to 0.70f, 0.10f to 0.70f
                )
            )
        )
    )

    val cities = listOf(
        CityMarker("Delhi", 0.33f, 0.36f),
        CityMarker("Mumbai", 0.18f, 0.68f),
        CityMarker("Chennai", 0.44f, 0.88f),
        CityMarker("Kolkata", 0.82f, 0.55f),
        CityMarker("Bengaluru", 0.34f, 0.82f),
        CityMarker("Hyderabad", 0.44f, 0.72f),
        CityMarker("Ahmedabad", 0.14f, 0.58f)
    )

    data class CityMarker(val name: String, val x: Float, val y: Float)
}
