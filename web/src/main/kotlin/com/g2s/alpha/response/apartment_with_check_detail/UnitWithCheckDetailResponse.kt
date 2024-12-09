package com.g2s.alpha.response.apartment_with_check_detail

import com.g2s.alpha.enums.CheckStatus

data class UnitWithCheckDetailResponse(
    val unitNumber: String,
    val ownerId: String,
    val ownerName: String,
    val ownerContact: String,
    val checkId: String,
    val checkCompletePeriod: String,
    val checkStatus: CheckStatus,
    val checkerName: String,
    val checkDetailResponse: CheckDetailResponse,
)
