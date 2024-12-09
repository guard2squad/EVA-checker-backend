package com.g2s.alpha.response.manager

data class ManagerResponse(
    val managerId: String,
    val email: String,
    val name: String,
    val registrationNumber: String,
    val contact: String,
    val address: String,
    val apartmentName: String,
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
    val verificationStatus: Boolean,
)