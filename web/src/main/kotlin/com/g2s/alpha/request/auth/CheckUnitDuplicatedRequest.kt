package com.g2s.alpha.request.auth

data class CheckUnitDuplicatedRequest(
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
)
