package com.g2s.alpha.manager

import com.g2s.alpha.enums.Role
import java.util.*

data class Manager(
    val managerId: String = UUID.randomUUID().toString(),
    val role: Role = Role.MANAGER,
    val email: String,
    var password: String,
    val name: String,
    val registrationNumber: String,
    val contact: String,
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
    val verificationStatus: Boolean,
)