package com.g2s.alpha.manager

import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.auth.PasswordEncoder
import com.g2s.alpha.exceptions.EmailNotRegisteredException
import com.g2s.alpha.exceptions.InvalidPasswordException
import com.g2s.alpha.exceptions.ManagerDeleteFailedException
import com.g2s.alpha.exceptions.ManagerNotFoundException
import org.springframework.stereotype.Component

@Component
class ManagerUseCase(
    private val apartmentUseCase: ApartmentUseCase,
    private val managerRepository: ManagerRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun registerManager(
        email: String,
        rawPassword: String,
        name: String,
        registrationNumber: String,
        contact: String,
        zonecode: String,
    ): Manager {

        if (managerRepository.existsByEmail(email)) {
            throw RuntimeException("User with email $email already registered!")
        }

        val manager = Manager(
            email = email,
            password = passwordEncoder.encode(rawPassword),
            name = name,
            registrationNumber = registrationNumber,
            contact = contact,
            zonecode = zonecode,
            buildingNumber = "0",
            unitNumber = "0",
            verificationStatus = true   // TODO: 인증 절차 추가
        )

        managerRepository.save(manager)

        return manager
    }

    fun updateManager(
        managerId: String,
        password: String,
        name: String,
        contact: String,
        zonecode: String,
        isAddressChange: Boolean
    ): Manager {
        // 관리실 존재 여부 확인
        val manager = findManagerById(managerId)
        // 비밀번호 확인
        checkPassword(manager, password)
        // 주소 변경
        val updatedManager = if (isAddressChange) {
            apartmentUseCase.ensureApartmentNotDuplicated(zonecode)
            manager.copy(
                zonecode = zonecode,
                name = name,
                contact = contact,
                verificationStatus = false
            )
        } else {
            manager
        }
        // 관리실 정보 업데이트
        val savedManager = managerRepository.update(updatedManager)
            ?: throw ManagerNotFoundException(managerId)

        return savedManager
    }

    fun findManagerByEmail(email: String): Manager {
        return managerRepository.findByEmail(email)
            ?: throw EmailNotRegisteredException(email)
    }

    fun findManagerById(managerId: String): Manager {
        return managerRepository.findByManagerId(managerId)
            ?: throw ManagerNotFoundException(managerId)
    }

    fun checkPassword(manager: Manager, rawPassword: String) {
        val matched = passwordEncoder.matches(rawPassword, manager.password)
        if (!matched) {
            throw InvalidPasswordException()
        }
    }

    fun deleteManager(managerId: String) {
        findManagerById(managerId)

        val hadDeleted = managerRepository.deleteManager(managerId)
        if (!hadDeleted) {
            throw ManagerDeleteFailedException(managerId)
        }
    }

    fun existsManagerByEmail(email: String): Boolean {
        return managerRepository.existsByEmail(email)
    }
}
