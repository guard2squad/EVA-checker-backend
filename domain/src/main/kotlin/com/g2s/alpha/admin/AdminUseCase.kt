package com.g2s.alpha.admin

import com.g2s.alpha.auth.PasswordEncoder
import com.g2s.alpha.auth.Principal
import com.g2s.alpha.exceptions.AdminNotRegisteredException
import com.g2s.alpha.exceptions.InvalidPasswordException
import org.springframework.stereotype.Component

@Component
class AdminUseCase(
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun registerAdmin(
        name: String,
        rawPassword: String,
    ) {
        val admin = Admin(
            name = name,
            password = passwordEncoder.encode(rawPassword),
        )

        adminRepository.save(admin)
    }

    fun authenticate(name: String, password: String): Principal {
        val admin: Admin = findByName(name)
        checkPassword(admin, password)
        return Principal(admin.adminId, admin.role)
    }

    private fun findByName(name: String): Admin {
        return adminRepository.find(name) ?: throw AdminNotRegisteredException(name)
    }

    private fun checkPassword(admin: Admin, rawPassword: String) {
        val matched = passwordEncoder.matches(rawPassword, admin.password)
        if (!matched) {
            throw InvalidPasswordException()
        }
    }
}
