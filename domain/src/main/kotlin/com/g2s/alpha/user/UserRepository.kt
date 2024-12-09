package com.g2s.alpha.user

import org.springframework.stereotype.Repository

@Repository
interface UserRepository {
    fun save(user: User): User
    fun update(user: User): User?
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun findByUserId(userId: String): User?
    fun deleteUser(userId: String): Boolean
}
