package com.g2s.alpha.response.apartment

import com.g2s.alpha.enums.CheckStatus

data class UnitResponse(
    val unitNumber: String,
    val ownerId: String,
    val ownerName: String,
    val ownerContact: String,
    val checkId: String,
    val checkCompletePeriod: String,
    val checkStatus: CheckStatus,
)