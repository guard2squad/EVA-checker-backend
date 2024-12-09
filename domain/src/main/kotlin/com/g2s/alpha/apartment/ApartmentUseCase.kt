package com.g2s.alpha.apartment

import com.g2s.alpha.check.CheckPeriod
import com.g2s.alpha.check.CheckUseCase
import com.g2s.alpha.exceptions.ApartmentNotFoundException
import com.g2s.alpha.exceptions.BuildingNotFoundException
import com.g2s.alpha.exceptions.ConflictApartmentException
import com.g2s.alpha.exceptions.UnitNotFoundException
import org.springframework.stereotype.Component


@Component
class ApartmentUseCase(
    private val checkUseCase: CheckUseCase,
    private val apartmentRepository: ApartmentRepository
) {
    fun createApartment(zonecode: String): Apartment {
        val checkPeriod = CheckPeriod(
            "2023-01-01",
            "2024-12-31",
        )
        // TODO: CSV 값에 따라 생성
        val buildings = listOf(
            Building(
                buildingNumber = "201",
                units = listOf(
                    Unit(
                        unitNumber = "101",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "101"),
                    ),
                    Unit(
                        unitNumber = "102",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "102"),
                    ),
                    Unit(
                        unitNumber = "103",
                        ownerId = "87bdeee8-1757-4700-a381-4b2e0ed884ac",
                        ownerName = "전영주",
                        ownerContact = "010-1234-1234",
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "103"),
                    ),
                    Unit(
                        unitNumber = "104",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "104"),
                    ), Unit(
                        unitNumber = "201",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "201"),
                    ),
                    Unit(
                        unitNumber = "202",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "202"),
                    ),
                    Unit(
                        unitNumber = "203",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "203"),
                    ),
                    Unit(
                        unitNumber = "204",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "204"),
                    ),
                    Unit(
                        unitNumber = "301",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "301"),
                    ),
                    Unit(
                        unitNumber = "302",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "302"),
                    ),
                    Unit(
                        unitNumber = "303",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "303"),
                    ),
                    Unit(
                        unitNumber = "304",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "201", "304"),
                    )
                )
            ),
            Building(
                buildingNumber = "202",
                units = listOf(
                    Unit(
                        unitNumber = "101",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "101"),
                    ),
                    Unit(
                        unitNumber = "102",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "102"),
                    ),
                    Unit(
                        unitNumber = "103",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "103"),
                    ),
                    Unit(
                        unitNumber = "104",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "104"),
                    ), Unit(
                        unitNumber = "201",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "201"),
                    ),
                    Unit(
                        unitNumber = "202",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "202"),
                    ),
                    Unit(
                        unitNumber = "203",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "203"),
                    ),
                    Unit(
                        unitNumber = "204",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "204"),
                    ),
                    Unit(
                        unitNumber = "301",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "301"),
                    ),
                    Unit(
                        unitNumber = "302",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "302"),
                    ),
                    Unit(
                        unitNumber = "303",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "303"),
                    ),
                    Unit(
                        unitNumber = "304",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "202", "304"),
                    )
                )
            ),
            Building(
                buildingNumber = "203",
                units = listOf(
                    Unit(
                        unitNumber = "101",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "101"),
                    ),
                    Unit(
                        unitNumber = "102",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "102"),
                    ),
                    Unit(
                        unitNumber = "103",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "103"),
                    ),
                    Unit(
                        unitNumber = "104",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "104"),
                    ), Unit(
                        unitNumber = "201",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "201"),
                    ),
                    Unit(
                        unitNumber = "202",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "202"),
                    ),
                    Unit(
                        unitNumber = "203",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "203"),
                    ),
                    Unit(
                        unitNumber = "204",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "204"),
                    ),
                    Unit(
                        unitNumber = "301",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "301"),
                    ),
                    Unit(
                        unitNumber = "302",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "302"),
                    ),
                    Unit(
                        unitNumber = "303",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "303"),
                    ),
                    Unit(
                        unitNumber = "304",
                        ownerId = null,
                        ownerName = null,
                        ownerContact = null,
                        checkId = checkUseCase.createCheck(checkPeriod, zonecode, "203", "304"),
                    )
                )
            )
        )

        val createdApartment = Apartment(
            zonecode = zonecode,
            name = "수목원호정포레스트",
            address = "대구 달서구 갈밭남로 17",
            checkPeriod = checkPeriod,
            buildings = buildings,
            completionDate = "2019-01-07",
        )

        return apartmentRepository.save(createdApartment)
    }

    fun findCheckIdByBuildingAndUnit(zonecode: String, buildingNumber: String, unitNumber: String): String {
        val unit = findUnitByZonecodeAndBuilding(zonecode, buildingNumber, unitNumber)
        return unit.checkId
    }

    fun findApartment(zonecode: String): Apartment {
        return apartmentRepository.findByZoneCode(zonecode)
            ?: throw ApartmentNotFoundException(zonecode)
    }

    private fun findBuildingByZonecodeAndBuilding(zonecode: String, buildingNumber: String): Building {
        val apartment = findApartment(zonecode)
        return apartment.buildings.find { it.buildingNumber == buildingNumber }
            ?: throw BuildingNotFoundException(zonecode = zonecode, buildingNumber = buildingNumber)
    }

    private fun findUnitByZonecodeAndBuilding(zonecode: String, buildingNumber: String, unitNumber: String): Unit {
        val building = findBuildingByZonecodeAndBuilding(zonecode, buildingNumber)
        return building.units.find { it.unitNumber == unitNumber }
            ?: throw UnitNotFoundException(
                zonecode = zonecode,
                buildingNumber = buildingNumber,
                unitNumber = unitNumber
            )
    }

    fun updateApartment(
        zonecode: String,
        buildingNumber: String,
        unitNumber: String,
        ownerId: String,
        ownerName: String,
        ownerContact: String
    ): Apartment {
        val unit = findUnitByZonecodeAndBuilding(
            zonecode,
            buildingNumber,
            unitNumber
        )

        val updatedUnit = unit.copy(
            ownerId = ownerId,
            ownerName = ownerName,
            ownerContact = ownerContact
        )

        return updateUnitInApartment(zonecode, buildingNumber, updatedUnit)
    }

    fun clearUnit(zonecode: String, buildingNumber: String, unitNumber: String) {
        val unit = findUnitByZonecodeAndBuilding(zonecode, buildingNumber, unitNumber)

        val updatedUnit = unit.copy(
            ownerId = null,
            ownerName = null,
            ownerContact = null
        )

        updateUnitInApartment(zonecode, buildingNumber, updatedUnit)
    }

    private fun updateUnitInApartment(zonecode: String, buildingNumber: String, updatedUnit: Unit): Apartment {
        val apartment = findApartment(zonecode)
        val building = apartment.buildings.find { it.buildingNumber == buildingNumber }
            ?: throw IllegalArgumentException("동을 찾을 수 없습니다.")

        val updatedUnits = building.units.map { if (it.unitNumber == updatedUnit.unitNumber) updatedUnit else it }
        val updatedBuilding = building.copy(units = updatedUnits)

        val updatedBuildings =
            apartment.buildings.map { if (it.buildingNumber == buildingNumber) updatedBuilding else it }
        val updatedApartment = apartment.copy(buildings = updatedBuildings)

        return apartmentRepository.update(updatedApartment)
            ?: throw ApartmentNotFoundException(zonecode)
    }

    fun isDuplicateUnitRegistration(zonecode: String, buildingNumber: String, unitNumber: String): Boolean {
        val unit = findUnitByZonecodeAndBuilding(zonecode, buildingNumber, unitNumber)
        return unit.ownerId != null
    }

    fun ensureApartmentNotDuplicated(zonecode: String) {
        val found = apartmentRepository.existsByZoneCode(zonecode)
        if (!found) {
            throw ConflictApartmentException(zonecode = zonecode)
        }
    }
}
