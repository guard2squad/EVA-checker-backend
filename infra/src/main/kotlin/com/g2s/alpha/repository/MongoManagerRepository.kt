package com.g2s.alpha.repository

import com.g2s.alpha.manager.Manager
import com.g2s.alpha.manager.ManagerRepository
import org.springframework.data.mongodb.core.FindAndReplaceOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class MongoManagerRepository(
    val mongoTemplate: MongoTemplate
) : ManagerRepository {

    companion object {
        const val MANAGER_COLLECTION_NAME = "manager"
    }

    override fun save(manager: Manager): Manager {
        return mongoTemplate.save(manager, MANAGER_COLLECTION_NAME)
    }

    override fun update(manager: Manager): Manager? {
        val query = Query(Criteria.where("managerId").`is`(manager.managerId))
        val options = FindAndReplaceOptions().returnNew()
        return mongoTemplate.findAndReplace(query, manager, options, Manager::class.java, MANAGER_COLLECTION_NAME)
    }

    override fun findByEmail(email: String): Manager? {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))

        return mongoTemplate.findOne(query, Manager::class.java, MANAGER_COLLECTION_NAME)
    }

    override fun existsByEmail(email: String): Boolean {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))

        return mongoTemplate.exists(query, Manager::class.java, MANAGER_COLLECTION_NAME)
    }

    override fun findByManagerId(managerId: String): Manager? {
        val query = Query()
        query.addCriteria(Criteria.where("managerId").`is`(managerId))

        return mongoTemplate.findOne(query, Manager::class.java, MANAGER_COLLECTION_NAME)
    }

    override fun deleteManager(managerId: String): Boolean {
        val query = Query()
        query.addCriteria(Criteria.where("managerId").`is`(managerId))
        val result = mongoTemplate.remove(query, Manager::class.java, MANAGER_COLLECTION_NAME)

        return result.deletedCount > 0L
    }
}
