package com.g2s.alpha.user

import com.g2s.alpha.enums.Role
import java.util.*

data class User(
    val userId: String = UUID.randomUUID().toString(),
    val role: Role = Role.USER,
    val email: String,
    var password: String,
    val name: String,
    val registrationNumber: String,
    val contact: String,
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
    val verificationStatus: Boolean,
    val checkId: String,
)