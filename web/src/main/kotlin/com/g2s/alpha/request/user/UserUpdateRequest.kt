package com.g2s.alpha.request.user

data class UserUpdateRequest(
    val userId: String,
    val password: String,
    val name: String,
    val contact: String,
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
    val isAddressChange: Boolean,
)
