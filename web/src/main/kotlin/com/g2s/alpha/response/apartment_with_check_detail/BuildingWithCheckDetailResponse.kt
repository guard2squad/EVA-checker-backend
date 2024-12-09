package com.g2s.alpha.response.apartment_with_check_detail

data class BuildingWithCheckDetailResponse(
    val buildingNumber: String,
    val unitWithCheckDetailResponses: List<UnitWithCheckDetailResponse>
)
