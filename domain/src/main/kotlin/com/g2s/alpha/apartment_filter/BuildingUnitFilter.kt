package com.g2s.alpha.apartment_filter

data class BuildingUnitFilter(
    val buildingNumber: String,
    val unitNumbers: List<String>
)