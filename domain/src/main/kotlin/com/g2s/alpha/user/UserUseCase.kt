package com.g2s.alpha.user

import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.auth.PasswordEncoder
import com.g2s.alpha.exceptions.*
import org.springframework.stereotype.Component

@Component
class UserUseCase(
    private val apartmentUseCase: ApartmentUseCase,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(
        email: String,
        rawPassword: String,
        name: String,
        registrationNumber: String,
        contact: String,
        zonecode: String,
        buildingNumber: String,
        unitNumber: String
    ): User {

        if (userRepository.existsByEmail(email)) {
            throw ConflictEmailException(email)
        }

        val user = User(
            email = email,
            password = passwordEncoder.encode(rawPassword),
            name = name,
            registrationNumber = registrationNumber,
            contact = contact,
            zonecode = zonecode,
            buildingNumber = buildingNumber,
            unitNumber = unitNumber,
            checkId = apartmentUseCase.findCheckIdByBuildingAndUnit(zonecode, buildingNumber, unitNumber),
            verificationStatus = false,
        )

        syncApartmentWithUser(user)

        userRepository.save(user)

        return user
    }

    fun updateUser(
        userId: String,
        password: String,
        name: String,
        contact: String,
        zonecode: String,
        buildingNumber: String,
        unitNumber: String,
        isAddressChange: Boolean
    ): User {
        // 유저 존재 여부 확인
        val user = findUserById(userId)
        // 비밀번호 확인
        checkPassword(user, password)
        // 주소 변경
        val updatedUser = if (isAddressChange) {
            if (apartmentUseCase.isDuplicateUnitRegistration(zonecode, buildingNumber, unitNumber)) {
                throw ConflictUnitException(zonecode, buildingNumber, unitNumber)
            }
            apartmentUseCase.clearUnit(user.zonecode, user.buildingNumber, user.unitNumber)
            // 새로운 정보로 유저 객체 생성
            user.copy(
                zonecode = zonecode,
                name = name,
                contact = contact,
                buildingNumber = buildingNumber,
                unitNumber = unitNumber,
                checkId = apartmentUseCase.findCheckIdByBuildingAndUnit(zonecode, buildingNumber, unitNumber),
                verificationStatus = false
            )
        } else {
            user
        }
        // 유저 정보 업데이트
        val savedUser = userRepository.update(updatedUser)
            ?: throw UserNotFoundException(userId = userId, email = null)
        // 아파트와 유저 동기화
        syncApartmentWithUser(updatedUser)

        return savedUser
    }

    fun findUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw EmailNotRegisteredException(email = email)
    }

    fun findUserById(userId: String): User {
        return userRepository.findByUserId(userId)
            ?: throw UserNotFoundException(userId = userId, email = null)
    }

    fun checkPassword(user: User, rawPassword: String) {
        val matched = passwordEncoder.matches(rawPassword, user.password)
        if (!matched) {
            throw InvalidPasswordException()
        }
    }

    fun deleteUser(userId: String) {
        val user = findUserById(userId)
        apartmentUseCase.clearUnit(user.zonecode, user.buildingNumber, user.unitNumber)

        val hasDeleted = userRepository.deleteUser(userId)
        if (!hasDeleted) {
            throw UserDeleteFailedException(userId)
        }
    }

    fun existsUserByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    // 아파트 정보와 유저 정보 동기화
    private fun syncApartmentWithUser(user: User) {
        apartmentUseCase.updateApartment(
            user.zonecode,
            user.buildingNumber,
            user.unitNumber,
            user.userId,
            user.name,
            user.contact
        )
    }
}
