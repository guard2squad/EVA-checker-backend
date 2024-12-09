package com.g2s.alpha.controllers.admin

import com.g2s.alpha.admin.AdminUseCase
import com.g2s.alpha.apartment.ApartmentUseCase
import com.g2s.alpha.auth.JwtTokenProvider
import com.g2s.alpha.request.admin.AdminSignInRequest
import com.g2s.alpha.response.admin.AdminSignInResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminUseCase: AdminUseCase,
    private val apartmentUseCase: ApartmentUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @PostMapping("/sign_in")
    fun adminSignIn(@RequestBody request: AdminSignInRequest): ResponseEntity<Any> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val principal = adminUseCase.authenticate(request.name, request.password)
        val token = jwtTokenProvider.createToken(principal)
        val response = AdminSignInResponse(
            adminId = principal.id,
            role = principal.role.name,
            token = token
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    fun createAdmin(@RequestBody request: AdminSignInRequest): ResponseEntity<Any> {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }

        adminUseCase.registerAdmin(request.name, request.password)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(headers)
            .body("Admin account created successfully")
    }

    @PostMapping("apartment/{zonecode}")
    fun createApartment(@PathVariable zonecode: String) {
        apartmentUseCase.createApartment(zonecode)
    }
}
