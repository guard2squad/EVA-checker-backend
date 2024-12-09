package com.g2s.alpha.apartment

import com.g2s.alpha.apartment_filter.ApartmentFilter
import org.springframework.stereotype.Repository

@Repository
interface ApartmentRepository {
    fun save(apartment: Apartment): Apartment
    fun findByZoneCode(zonecode: String): Apartment?
    fun findAll(): List<Apartment>
    fun update(apartment: Apartment): Apartment?
    fun existsByZoneCode(zonecode: String): Boolean
    fun findApartmentFilter(zonecode: String): ApartmentFilter
}
