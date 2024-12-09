package com.g2s.alpha.controllers.apartment

import com.g2s.alpha.apartment.Apartment
import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.apartment.Building
import com.g2s.alpha.apartment.Unit
import com.g2s.alpha.check.CheckPeriod
import com.g2s.alpha.check.CheckUseCase
import com.g2s.alpha.response.apartment.ApartmentResponse
import com.g2s.alpha.response.apartment.BuildingResponse
import com.g2s.alpha.response.apartment.UnitResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/apartments")
class ApartmentController(
    private val apartmentUseCase: ApartmentUseCase,
    private val checkUseCase: CheckUseCase
) {

    @GetMapping("/{zonecode}")
    fun getApartment(@PathVariable zonecode: String): ResponseEntity<ApartmentResponse> {
        val apartment = apartmentUseCase.findApartment(zonecode)
        val apartmentResponse = makeApartmentResponse(apartment)
        return ResponseEntity.ok(apartmentResponse)
    }

    private fun makeApartmentResponse(apartment: Apartment): ApartmentResponse {
        val buildingResponses = apartment.buildings.map { makeBuildingResponse(it) }

        val apartmentResponse = ApartmentResponse(
            zonecode = apartment.zonecode,
            name = apartment.name,
            address = apartment.address,
            checkPeriod = parseCheckPeriod(apartment.checkPeriod),
            buildingResponses = buildingResponses,
        )
        return apartmentResponse
    }

    private fun makeBuildingResponse(building: Building): BuildingResponse {
        val unitResponses = building.units.map { makeUnitResponse(it) }

        return BuildingResponse(
            buildingNumber = building.buildingNumber,
            unitResponses = unitResponses,
        )
    }

    private fun makeUnitResponse(unit: Unit): UnitResponse {
        val check = checkUseCase.findCheck(unit.checkId)

        return UnitResponse(
            unitNumber = unit.unitNumber,
            ownerId = unit.ownerId ?: "미등록",
            ownerName = unit.ownerName ?: "미등록",
            ownerContact = unit.ownerContact ?: "미등록",
            checkId = unit.checkId,
            checkCompletePeriod = check.checkCompletePeriod,
            checkStatus = check.status,
        )
    }

    private fun parseCheckPeriod(checkPeriod: CheckPeriod): String {
        val sb = StringBuilder()
        sb.append(checkPeriod.startDate)
        sb.append(" to ")
        sb.append(checkPeriod.endDate)

        return sb.toString()
    }
}
