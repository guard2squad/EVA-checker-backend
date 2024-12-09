package com.g2s.alpha.auth

import com.g2s.alpha.admin.AdminRepository
import com.g2s.alpha.enums.Role
import com.g2s.alpha.manager.ManagerRepository
import com.g2s.alpha.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val managerRepository: ManagerRepository,
    private val adminRepository: AdminRepository,
) : UserDetailsService {

    fun loadUserByTypeAndUsername(role: Role, userId: String): UserDetails {
        return when (role) {
            Role.USER -> loadUserByUsername(userId)
            Role.MANAGER -> loadManagerByUsername(userId)
            Role.ADMIN -> loadAdminByUsername(userId)
            else -> throw IllegalArgumentException("Invalid user type: $role")
        }
    }

    override fun loadUserByUsername(id: String): UserDetails {
        userRepository.findByUserId(id)?.let {
            val authorities = SimpleGrantedAuthority(it.role.name)
            return User(
                it.userId,
                it.password,
                listOf(authorities)
            )
        } ?: throw UsernameNotFoundException("User with id $id not found!")
    }

    private fun loadManagerByUsername(managerId: String): UserDetails {
        managerRepository.findByManagerId(managerId)?.let {
            val authorities = SimpleGrantedAuthority(it.role.name)
            return User(
                it.managerId,
                it.password,
                listOf(authorities)
            )
        } ?: throw UsernameNotFoundException("Manager with id $managerId not found!")
    }

    private fun loadAdminByUsername(adminId: String): UserDetails {
        adminRepository.findByAdminId(adminId)?.let {
            val authorities = SimpleGrantedAuthority(it.role.name)
            return User(
                it.adminId,
                it.password,
                listOf(authorities)
            )
        } ?: throw UsernameNotFoundException("Admin with name: $adminId not found!")
    }
}