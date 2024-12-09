package com.g2s.alpha.controllers.user

import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.check.CheckUseCase
import com.g2s.alpha.request.user.UserUpdateRequest
import com.g2s.alpha.response.user.UserResponse
import com.g2s.alpha.user.User
import com.g2s.alpha.user.UserUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userUseCase: UserUseCase,
    private val apartmentUseCase: ApartmentUseCase,
    private val checkUseCase: CheckUseCase
) {
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<UserResponse> {
        val user = userUseCase.findUserById(userId)
        val userResponse = makeUserResponse(user)
        return ResponseEntity.ok(userResponse)
    }

    @PutMapping("/info/{userId}")
    fun updaterUser(
        @PathVariable userId: String,
        @RequestBody userUpdateRequest: UserUpdateRequest
    ): ResponseEntity<Any> {
        val updatedUser = userUseCase.updateUser(
            userId = userUpdateRequest.userId,
            password = userUpdateRequest.password,
            name = userUpdateRequest.name,
            contact = userUpdateRequest.contact,
            zonecode = userUpdateRequest.zonecode,
            buildingNumber = userUpdateRequest.buildingNumber,
            unitNumber = userUpdateRequest.unitNumber,
            isAddressChange = userUpdateRequest.isAddressChange
        )
        val userResponse = makeUserResponse(updatedUser)
        return ResponseEntity.ok(userResponse)
    }

    private fun makeUserResponse(user: User): UserResponse {
        val apartment = apartmentUseCase.findApartment(user.zonecode)
        val check = checkUseCase.findCheck(user.checkId)
        val userResponse = UserResponse(
            userId = user.userId,
            email = user.email,
            name = user.name,
            registrationNumber = user.registrationNumber,
            contact = user.contact,
            address = apartment.address,
            apartmentName = apartment.name,
            zonecode = user.zonecode,
            buildingNumber = user.buildingNumber,
            unitNumber = user.unitNumber,
            verificationStatus = user.verificationStatus,
            checkId = user.checkId,
            checkPeriod = apartment.checkPeriod.toString(),
            checkStatus = check.status.toString(),
        )
        return userResponse
    }
}
