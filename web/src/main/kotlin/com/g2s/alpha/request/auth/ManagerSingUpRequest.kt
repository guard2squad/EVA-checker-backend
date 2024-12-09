package com.g2s.alpha.request.auth

data class ManagerSingUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val registrationNumber: String,
    val contact: String,
    val zonecode: String,
)