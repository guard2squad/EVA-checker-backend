package com.g2s.alpha.admin

import com.g2s.alpha.enums.Role
import java.util.*

data class Admin(
    val adminId: String = UUID.randomUUID().toString(),
    val name: String, // 로그인에 사용하는 ID
    val password: String,
    val role: Role = Role.ADMIN
)
