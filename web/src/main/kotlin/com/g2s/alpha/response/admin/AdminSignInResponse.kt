package com.g2s.alpha.response.admin

data class AdminSignInResponse(
    val adminId: String,
    val role: String,
    val token: String,
)
