package com.g2s.alpha.response.apartment_filter

data class BuildingUnitFilterResponse(
    val buildingNumber: String,
    val unitNumbers: List<String>
)
