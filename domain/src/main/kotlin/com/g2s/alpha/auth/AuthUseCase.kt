package com.g2s.alpha.auth

import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.exceptions.ConflictApartmentException
import com.g2s.alpha.exceptions.ConflictEmailException
import com.g2s.alpha.exceptions.ConflictUnitException
import com.g2s.alpha.exceptions.EmailNotRegisteredException
import com.g2s.alpha.manager.ManagerUseCase
import com.g2s.alpha.user.UserUseCase
import org.springframework.stereotype.Component

@Component
class AuthUseCase(
    private val userUseCase: UserUseCase,
    private val managerUseCase: ManagerUseCase,
    private val apartmentUseCase: ApartmentUseCase
) {
    fun authenticate(email: String, password: String): Principal {
        return try {
            // 사용자 인증 시도
            val user = userUseCase.findUserByEmail(email)
            userUseCase.checkPassword(user, password)
            Principal(user.userId, user.role)
        } catch (e: EmailNotRegisteredException) {
            // 사용자가 없으면 매니저 인증 시도
            val manager = managerUseCase.findManagerByEmail(email)
            managerUseCase.checkPassword(manager, password)
            Principal(manager.managerId, manager.role)
        }
    }

    // 이메일 중복 여부 확인하고 예외를 던짐
    fun ensureEmailNotDuplicated(email: String) {
        if (userUseCase.existsUserByEmail(email) || managerUseCase.existsManagerByEmail(email)) {
            throw ConflictEmailException(email)
        }
    }

    fun ensureUnitNotDuplicated(zonecode: String, buildingNumber: String, unitNumber: String) {
        if (apartmentUseCase.isDuplicateUnitRegistration(zonecode, buildingNumber, unitNumber)) {
            throw ConflictUnitException(zonecode, buildingNumber, unitNumber)
        }
    }

    fun ensureApartmentNotDuplicated(zonecode: String) {
        apartmentUseCase.ensureApartmentNotDuplicated(zonecode)
    }
}
