package com.g2s.alpha.response.apartment

data class BuildingResponse(
    val buildingNumber: String,
    val unitResponses: List<UnitResponse>
)
