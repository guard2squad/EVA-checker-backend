package com.g2s.alpha.repository

import com.g2s.alpha.user.User
import com.g2s.alpha.user.UserRepository
import org.springframework.data.mongodb.core.FindAndReplaceOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component


@Component
class MongoUserRepository(
    val mongoTemplate: MongoTemplate
) : UserRepository {

    companion object {
        const val USER_COLLECTION_NAME = "user"
    }

    override fun save(user: User): User {
        return mongoTemplate.save(user, USER_COLLECTION_NAME)
    }

    override fun update(user: User): User? {
        val query = Query(Criteria.where("userId").`is`(user.userId))
        val options = FindAndReplaceOptions().returnNew()
        return mongoTemplate.findAndReplace(query, user, options, User::class.java, USER_COLLECTION_NAME)
    }

    override fun findByEmail(email: String): User? {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))

        return mongoTemplate.findOne(query, User::class.java, USER_COLLECTION_NAME)
    }

    override fun existsByEmail(email: String): Boolean {
        val query = Query()
        query.addCriteria(Criteria.where("email").`is`(email))

        return mongoTemplate.exists(query, User::class.java, USER_COLLECTION_NAME)
    }

    override fun findByUserId(userId: String): User? {
        val query = Query()
        query.addCriteria(Criteria.where("userId").`is`(userId))

        return mongoTemplate.findOne(query, User::class.java, USER_COLLECTION_NAME)
    }

    override fun deleteUser(userId: String): Boolean {
        val query = Query()
        query.addCriteria(Criteria.where("userId").`is`(userId))
        val result = mongoTemplate.remove(query, User::class.java, USER_COLLECTION_NAME)

        return result.deletedCount > 0L
    }
}
