package com.g2s.alpha.request.auth

data class UserSignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val registrationNumber: String,
    val contact: String,
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
)