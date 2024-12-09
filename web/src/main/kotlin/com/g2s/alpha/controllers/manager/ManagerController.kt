package com.g2s.alpha.controllers.manager

import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.manager.Manager
import com.g2s.alpha.manager.ManagerUseCase
import com.g2s.alpha.request.manager.ManagerUpdateRequest
import com.g2s.alpha.response.manager.ManagerResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/managers")
class ManagerController(
    private val managerUseCase: ManagerUseCase,
    private val apartmentUseCase: ApartmentUseCase,
) {

    @GetMapping("/{managerId}")
    fun getManager(@PathVariable managerId: String): ResponseEntity<ManagerResponse> {
        val manager = managerUseCase.findManagerById(managerId)
        val managerResponse = makeManagerResponse(manager)
        return ResponseEntity.ok(managerResponse)
    }

    @PutMapping("/info/{managerId}")
    fun updaterManager(
        @PathVariable managerId: String,
        @RequestBody managerUpdateRequest: ManagerUpdateRequest
    ): ResponseEntity<Any> {
        val updatedManager = managerUseCase.updateManager(
            managerUpdateRequest.managerId,
            managerUpdateRequest.password,
            managerUpdateRequest.name,
            managerUpdateRequest.contact,
            managerUpdateRequest.zonecode,
            managerUpdateRequest.isAddressChange
        )

        val mangerResponse = makeManagerResponse(updatedManager)
        return ResponseEntity.ok(mangerResponse)
    }

    private fun makeManagerResponse(manager: Manager): ManagerResponse {
        val apartment = apartmentUseCase.findApartment(manager.zonecode)
        val managerResponse = ManagerResponse(
            managerId = manager.managerId,
            email = manager.email,
            name = manager.name,
            registrationNumber = manager.registrationNumber,
            contact = manager.contact,
            address = apartment.address,
            apartmentName = apartment.name,
            zonecode = manager.zonecode,
            buildingNumber = manager.buildingNumber,
            unitNumber = manager.unitNumber,
            verificationStatus = true,
        )
        return managerResponse
    }
}
