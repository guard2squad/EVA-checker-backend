package com.g2s.alpha.request.manager

data class ManagerUpdateRequest(
    val managerId: String,
    val password: String,
    val name: String,
    val contact: String,
    val zonecode: String,
    val isAddressChange: Boolean,
)
