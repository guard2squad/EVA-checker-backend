package com.g2s.alpha.repository

import com.g2s.alpha.check.Check
import com.g2s.alpha.check.CheckPeriod
import com.g2s.alpha.check.CheckRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class MongoCheckRepository(
    private val mongoTemplate: MongoTemplate
) : CheckRepository {
    companion object {
        const val CHECK_COLLECTION_NAME = "check"
    }

    override fun save(check: Check): Check {
        return mongoTemplate.save(check, CHECK_COLLECTION_NAME)
    }

    override fun findChecks(
        zonecode: String,
        buildingNumber: String?,
        unitNumber: String?,
        checkPeriod: CheckPeriod?,
        pageable: Pageable
    ): Page<Check> {
        val criteria = Criteria.where("zonecode").`is`(zonecode)

        buildingNumber?.let {
            criteria.and("buildingNumber").`is`(it)
        }

        unitNumber?.let {
            criteria.and("unitNumber").`is`(it)
        }

        checkPeriod?.let {
            criteria.and("checkPeriod.startDate").`is`(it.startDate)
            criteria.and("checkPeriod.endDate").`is`(it.endDate)
        }

        val query = Query(criteria).with(pageable)
        val total = mongoTemplate.count(Query(criteria), Check::class.java, CHECK_COLLECTION_NAME)
        val checks = mongoTemplate.find(query, Check::class.java, CHECK_COLLECTION_NAME)

        return PageImpl(checks, pageable, total)
    }

    override fun findByCheckId(checkId: String): Check? {
        val query = Query()
        query.addCriteria(Criteria.where("checkId").`is`(checkId))

        return mongoTemplate.findOne(query, Check::class.java, CHECK_COLLECTION_NAME)
    }

    override fun update(check: Check): Check? {
        val query = Query(Criteria.where("checkId").`is`(check.checkId))
        val update = Update()
            .set("mainItems", check.mainItems)
            .set("status", check.status)
            .set("checkCompletePeriod", check.checkCompletePeriod)
            .set("checkerId", check.checkerId)
            .set("checkerName", check.checkerName)
            .set("checkerContact", check.checkerContact)

        val options = FindAndModifyOptions().returnNew(true).upsert(false)

        return mongoTemplate.findAndModify(query, update, options, Check::class.java, CHECK_COLLECTION_NAME)
    }

    override fun deleteCheck(checkId: String): Boolean {
        val query = Query()
        query.addCriteria(Criteria.where("checkId").`is`(checkId))
        val result = mongoTemplate.remove(query, Check::class.java, CHECK_COLLECTION_NAME)

        return result.deletedCount > 0L
    }
}
