package com.g2s.alpha.auth

import com.g2s.alpha.enums.Role

data class Principal(
    val id: String,
    val role: Role,
)
