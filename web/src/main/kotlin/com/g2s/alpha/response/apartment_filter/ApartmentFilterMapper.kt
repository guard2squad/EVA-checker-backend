package com.g2s.alpha.response.apartment_filter

import com.g2s.alpha.apartment_filter.ApartmentFilter
import com.g2s.alpha.apartment_filter.BuildingUnitFilter
import com.g2s.alpha.check.CheckPeriod
import com.g2s.alpha.response.common.CheckPeriodResponse

object ApartmentFilterMapper {
    fun ApartmentFilter.toResponse(): ApartmentFilterDataResponse {
        return ApartmentFilterDataResponse(
            buildingNumbers = this.buildingNumbers.map { it.toResponse() },
            checkPeriods = this.checkPeriods.map { it.toResponse() }
        )
    }

    private fun BuildingUnitFilter.toResponse(): BuildingUnitFilterResponse {
        return BuildingUnitFilterResponse(
            buildingNumber = this.buildingNumber,
            unitNumbers = this.unitNumbers
        )
    }

    private fun CheckPeriod.toResponse(): CheckPeriodResponse {
        return CheckPeriodResponse(
            startDate = this.startDate,
            endDate = this.endDate
        )
    }
}