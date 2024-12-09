package com.g2s.alpha.repository

import com.g2s.alpha.admin.Admin
import com.g2s.alpha.admin.AdminRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class MongoAdminRepository(
    private val mongoTemplate: MongoTemplate
) : AdminRepository {

    companion object {
        const val ADMIN_COLLECTION_NAME = "admin"
    }

    override fun save(admin: Admin) {
        mongoTemplate.save(admin)
    }

    override fun find(name: String): Admin? {
        val query = Query()
        query.addCriteria(Criteria.where("name").`is`(name))

        return mongoTemplate.findOne(query, Admin::class.java, ADMIN_COLLECTION_NAME)
    }

    override fun findByAdminId(adminId: String): Admin? {
        val query = Query()
        query.addCriteria(Criteria.where("adminId").`is`(adminId))

        return mongoTemplate.findOne(query, Admin::class.java, ADMIN_COLLECTION_NAME)
    }
}