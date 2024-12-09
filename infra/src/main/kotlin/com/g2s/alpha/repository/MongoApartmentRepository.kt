package com.g2s.alpha.repository

import com.g2s.alpha.apartment.Apartment
import com.g2s.alpha.apartment.ApartmentRepository
import com.g2s.alpha.apartment_filter.ApartmentFilter
import com.g2s.alpha.apartment_filter.BuildingUnitFilter
import com.g2s.alpha.check.CheckPeriod
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.match
import org.springframework.data.mongodb.core.aggregation.Aggregation.project
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class MongoApartmentRepository(
    val mongoTemplate: MongoTemplate
) : ApartmentRepository {

    companion object {
        const val APARTMENT_COLLECTION_NAME = "apartment"
    }

    override fun save(apartment: Apartment): Apartment {
        return mongoTemplate.save(apartment, APARTMENT_COLLECTION_NAME)
    }

    override fun findByZoneCode(zonecode: String): Apartment? {
        val query = Query()
        query.addCriteria(Criteria.where("zonecode").`is`(zonecode))

        return mongoTemplate.findOne(query, Apartment::class.java, APARTMENT_COLLECTION_NAME)
    }

    override fun findAll(): List<Apartment> {
        return mongoTemplate.findAll<Apartment>()
    }

    override fun update(apartment: Apartment): Apartment? {
        val query = Query()
        query.addCriteria(Criteria.where("zonecode").`is`(apartment.zonecode))

        return mongoTemplate.findAndReplace(query, apartment, APARTMENT_COLLECTION_NAME)
    }

    override fun existsByZoneCode(zonecode: String): Boolean {
        val query = Query()
        query.addCriteria(Criteria.where("zonecode").`is`(zonecode))

        return mongoTemplate.exists(query, Apartment::class.java, APARTMENT_COLLECTION_NAME)
    }

    override fun findApartmentFilter(zonecode: String): ApartmentFilter {
        val buildingAndUnit = findBuildingAndUnitByZonecode(zonecode)
        val checkPeriods = findCheckPeriodsByZonecode(zonecode)

        return ApartmentFilter(buildingNumbers = buildingAndUnit, checkPeriods = checkPeriods)
    }

    private fun findBuildingAndUnitByZonecode(zonecode: String): List<BuildingUnitFilter> {
        val criteria = Criteria.where("zonecode").`is`(zonecode)
        val aggregation = Aggregation.newAggregation(
            match(criteria),
            project()
                .and("buildings.buildingNumber").`as`("buildings.buildingNumber")
                .and("buildings.units.unitNumber").`as`("buildings.units.unitNumber")
        )

        val results =
            mongoTemplate.aggregate(aggregation, APARTMENT_COLLECTION_NAME, AggregationBuildingUnitResult::class.java)

        return results.mappedResults.flatMap { result ->
            result.buildings.map { building ->
                BuildingUnitFilter(
                    buildingNumber = building.buildingNumber,
                    unitNumbers = building.units.map { it.unitNumber }
                )
            }
        }
    }

    private data class AggregationBuildingUnitResult(
        val buildings: List<Building>
    )

    private data class Building(
        val buildingNumber: String,
        val units: List<Unit>
    )

    private data class Unit(
        val unitNumber: String
    )

    // 2. zonecode 기준으로 모든 checkPeriod 조회
    private fun findCheckPeriodsByZonecode(zonecode: String): List<CheckPeriod> {
        val criteria = Criteria.where("zonecode").`is`(zonecode)
        val aggregation = Aggregation.newAggregation(
            match(criteria),
            project("checkPeriod")
        )

        val results =
            mongoTemplate.aggregate(aggregation, APARTMENT_COLLECTION_NAME, CheckPeriodAggregationResult::class.java)

        return results.mappedResults.map { it.checkPeriod }
    }

    private data class CheckPeriodAggregationResult(
        val checkPeriod: CheckPeriod
    )
}
