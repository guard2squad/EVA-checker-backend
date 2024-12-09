package com.g2s.alpha.response.apartment_with_check_detail

import com.g2s.alpha.response.common.CheckPeriodResponse

data class ApartmentWithCheckDetailResponse(
    val zonecode: String,
    val name: String,
    val address: String,
    val checkPeriod: CheckPeriodResponse,
    val buildingWithCheckDetailResponses: List<BuildingWithCheckDetailResponse>,
    val completionDate: String,
)
