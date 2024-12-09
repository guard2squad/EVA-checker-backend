package com.g2s.alpha.manager

import org.springframework.stereotype.Repository

@Repository
interface ManagerRepository {
    fun save(manager: Manager): Manager
    fun update(manager: Manager): Manager?
    fun findByEmail(email: String): Manager?
    fun existsByEmail(email: String): Boolean
    fun findByManagerId(managerId: String): Manager?
    fun deleteManager(managerId: String): Boolean
}
