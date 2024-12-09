package com.g2s.alpha.admin

import org.springframework.stereotype.Repository

@Repository
interface AdminRepository {
    fun save(admin: Admin)
    fun find(name: String): Admin?
    fun findByAdminId(adminId: String): Admin?
}