package com.g2s.alpha.apartment_filter

import com.g2s.alpha.apartment.ApartmentRepository
import org.springframework.stereotype.Component

@Component
class ApartmentFilterUseCase(
    private val apartmentRepository: ApartmentRepository
) {
    fun findApartmentFilter(zonecode: String): ApartmentFilter {
        return apartmentRepository.findApartmentFilter(zonecode)
    }
}