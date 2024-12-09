package com.g2s.alpha.response.apartment_filter

import com.g2s.alpha.response.common.CheckPeriodResponse

data class ApartmentFilterDataResponse(
    val buildingNumbers: List<BuildingUnitFilterResponse>,
    val checkPeriods: List<CheckPeriodResponse>
)
