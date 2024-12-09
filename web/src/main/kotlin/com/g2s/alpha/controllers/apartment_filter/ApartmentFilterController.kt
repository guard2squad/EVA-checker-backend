package com.g2s.alpha.controllers.apartment_filter

import com.g2s.alpha.apartment_filter.ApartmentFilterUseCase
import com.g2s.alpha.response.apartment_filter.ApartmentFilterDataResponse
import com.g2s.alpha.response.apartment_filter.ApartmentFilterMapper.toResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/apartment-filter")
class ApartmentFilterController(
    private val apartmentFilterUseCase: ApartmentFilterUseCase
) {
    @GetMapping("/{zonecode}")
    fun getApartment(@PathVariable zonecode: String): ResponseEntity<ApartmentFilterDataResponse> {
        return ResponseEntity.ok(apartmentFilterUseCase.findApartmentFilter(zonecode).toResponse())
    }
}
