package com.g2s.alpha.apartment_filter

import com.g2s.alpha.check.CheckPeriod

data class ApartmentFilter(
    val buildingNumbers: List<BuildingUnitFilter>,
    val checkPeriods: List<CheckPeriod>
)
