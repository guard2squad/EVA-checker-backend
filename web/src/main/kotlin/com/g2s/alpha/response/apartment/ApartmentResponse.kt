package com.g2s.alpha.response.apartment

data class ApartmentResponse(
    val zonecode: String,
    val name: String,
    val address: String,
    val checkPeriod: String,
    val buildingResponses: List<BuildingResponse>,
)
