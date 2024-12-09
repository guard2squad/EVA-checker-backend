package com.g2s.alpha.controllers.apartment_with_check_detail

import com.g2s.alpha.apartment.Apartment
import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.apartment.Building
import com.g2s.alpha.apartment.Unit
import com.g2s.alpha.check.*
import com.g2s.alpha.enums.CheckSubItemStatus
import com.g2s.alpha.response.apartment_with_check_detail.ApartmentWithCheckDetailResponse
import com.g2s.alpha.response.apartment_with_check_detail.BuildingWithCheckDetailResponse
import com.g2s.alpha.response.apartment_with_check_detail.CheckDetailResponse
import com.g2s.alpha.response.apartment_with_check_detail.UnitWithCheckDetailResponse
import com.g2s.alpha.response.common.CheckPeriodResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/apartments/check_detail")
class ApartmentWithCheckDetailController(
    private val apartmentUseCase: ApartmentUseCase,
    private val checkUseCase: CheckUseCase
) {

    // for web manager page
    @GetMapping("/{zonecode}")
    fun getApartmentWithCheckDetail(@PathVariable zonecode: String): ResponseEntity<ApartmentWithCheckDetailResponse> {
        val apartment = apartmentUseCase.findApartment(zonecode)
        val apartmentWithCheckDetailResponse = makeApartmentWithCheckDetailResponse(apartment)
        return ResponseEntity.ok(apartmentWithCheckDetailResponse)
    }

    private fun makeApartmentWithCheckDetailResponse(apartment: Apartment): ApartmentWithCheckDetailResponse {
        val buildingWithCheckDetailResponses = apartment.buildings.map { makeBuildingWithCheckDetailResponse(it) }

        val apartmentWithCheckDetailResponse = ApartmentWithCheckDetailResponse(
            zonecode = apartment.zonecode,
            name = apartment.name,
            address = apartment.address,
            checkPeriod = parseCheckPeriod(apartment.checkPeriod),
            buildingWithCheckDetailResponses = buildingWithCheckDetailResponses,
            completionDate = apartment.completionDate,
        )
        return apartmentWithCheckDetailResponse
    }

    private fun makeBuildingWithCheckDetailResponse(building: Building): BuildingWithCheckDetailResponse {
        val unitWithCheckDetailResponses = building.units.map { makeUnitWithCheckDetailResponse(it) }

        return BuildingWithCheckDetailResponse(
            buildingNumber = building.buildingNumber,
            unitWithCheckDetailResponses = unitWithCheckDetailResponses,
        )
    }

    private fun makeUnitWithCheckDetailResponse(unit: Unit): UnitWithCheckDetailResponse {
        val check = checkUseCase.findCheck(unit.checkId)

        return UnitWithCheckDetailResponse(
            unitNumber = unit.unitNumber,
            ownerId = unit.ownerId ?: "미등록",
            ownerName = unit.ownerName ?: "미등록",
            ownerContact = unit.ownerContact ?: "미등록",
            checkId = unit.checkId,
            checkCompletePeriod = check.checkCompletePeriod,
            checkStatus = check.status,
            checkerName = check.checkerName ?: "미등록",
            checkDetailResponse = makeCheckDetailResponse(check),
        )
    }

    private fun makeCheckDetailResponse(check: Check): CheckDetailResponse {
        var good = 0
        var fault = 0
        var notChecked = 0
        var notApplicable = 0

        check.mainItems.forEach { mainItem: MainItem ->
            mainItem.subItems.forEach { subItem: SubItem ->
                when (subItem.status) {
                    CheckSubItemStatus.GOOD -> good++
                    CheckSubItemStatus.FAULT -> fault++
                    CheckSubItemStatus.NOT_CHECKED -> notChecked++
                    CheckSubItemStatus.NOT_APPLICABLE -> notApplicable++
                }
            }
        }

        return CheckDetailResponse(
            good = good,
            fault = fault,
            notChecked = notChecked,
            notApplicable = notApplicable,
        )
    }

    private fun parseCheckPeriod(checkPeriod: CheckPeriod): CheckPeriodResponse {
        return CheckPeriodResponse(
            startDate = checkPeriod.startDate,
            endDate = checkPeriod.endDate,
        )
    }
}
