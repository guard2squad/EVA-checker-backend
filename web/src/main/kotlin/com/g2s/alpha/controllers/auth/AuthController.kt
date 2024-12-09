package com.g2s.alpha.controllers.auth

import com.g2s.alpha.auth.AuthUseCase
import com.g2s.alpha.auth.JwtTokenProvider
import com.g2s.alpha.manager.ManagerUseCase
import com.g2s.alpha.request.auth.CheckUnitDuplicatedRequest
import com.g2s.alpha.request.auth.CommonSignInRequest
import com.g2s.alpha.request.auth.ManagerSingUpRequest
import com.g2s.alpha.request.auth.UserSignUpRequest
import com.g2s.alpha.response.auth.CommonSignInResponse
import com.g2s.alpha.response.auth.ManagerSignUpResponse
import com.g2s.alpha.response.auth.UserSignUpResponse
import com.g2s.alpha.user.UserUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userUseCase: UserUseCase,
    private val managerUseCase: ManagerUseCase,
    private val authUseCase: AuthUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @PostMapping("/user/sign_up")
    fun signUpUser(@RequestBody userSignUpRequest: UserSignUpRequest): ResponseEntity<UserSignUpResponse> {
        val user = userUseCase.registerUser(
            email = userSignUpRequest.email,
            rawPassword = userSignUpRequest.password,
            name = userSignUpRequest.name,
            registrationNumber = userSignUpRequest.registrationNumber,
            contact = userSignUpRequest.contact,
            zonecode = userSignUpRequest.zonecode,
            buildingNumber = userSignUpRequest.buildingNumber,
            unitNumber = userSignUpRequest.unitNumber,
        )

        val response = UserSignUpResponse(
            userId = user.userId
        )

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/user/withdraw/{userId}")
    fun withdrawUser(@PathVariable userId: String): ResponseEntity<Any> {
        userUseCase.deleteUser(userId)
        return ResponseEntity.ok("user successfully deleted")
    }

    @PostMapping("/manager/sign_up")
    fun signUpManager(@RequestBody managerSignUpRequest: ManagerSingUpRequest): ResponseEntity<ManagerSignUpResponse> {
        val manager = managerUseCase.registerManager(
            email = managerSignUpRequest.email,
            rawPassword = managerSignUpRequest.password,
            name = managerSignUpRequest.name,
            registrationNumber = managerSignUpRequest.registrationNumber,
            contact = managerSignUpRequest.contact,
            zonecode = managerSignUpRequest.zonecode,
        )

        val response = ManagerSignUpResponse(
            managerId = manager.managerId,
        )

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/manager/withdraw/{managerId}")
    fun withdrawManager(@PathVariable managerId: String): ResponseEntity<Any> {
        managerUseCase.deleteManager(managerId)
        return ResponseEntity.ok("manager successfully deleted")
    }

    @PostMapping("/common/sign_in")
    fun singInCommon(@RequestBody commonSignInRequest: CommonSignInRequest): ResponseEntity<Any> {
        val principal = authUseCase.authenticate(commonSignInRequest.email, commonSignInRequest.password)
        val token = jwtTokenProvider.createToken(principal)
        val response = CommonSignInResponse(
            id = principal.id,
            token = token,
            role = principal.role.name
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/common/check-email/{email}")
    fun checkEmailDuplicate(@PathVariable email: String): ResponseEntity<Any> {
        authUseCase.ensureEmailNotDuplicated(email)

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val response = mapOf("email" to email, "message" to "Available email")
        return ResponseEntity
            .ok()
            .headers(headers)
            .body(response)
    }

    @PostMapping("/user/check-duplicated-unit")
    fun checkUnitDuplicate(@RequestBody checkUnitDuplicatedRequest: CheckUnitDuplicatedRequest): ResponseEntity<Any> {
        authUseCase.ensureUnitNotDuplicated(
            checkUnitDuplicatedRequest.zonecode,
            checkUnitDuplicatedRequest.buildingNumber,
            checkUnitDuplicatedRequest.unitNumber
        )

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val response = mapOf("message" to "Available unit")
        return ResponseEntity
            .ok()
            .headers(headers)
            .body(response)
    }

    @GetMapping("/manager/check-duplicated-apartment/{zonecode}")
    fun checkApartmentDuplicate(@PathVariable zonecode: String): ResponseEntity<Any> {
        authUseCase.ensureApartmentNotDuplicated(zonecode)

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
        val response = mapOf("message" to "Available apartment")
        return ResponseEntity
            .ok()
            .headers(headers)
            .body(response)
    }
}
