package com.g2s.alpha.response.auth

data class CommonSignInResponse(
    val id: String,
    val token: String,
    val role: String,
)
