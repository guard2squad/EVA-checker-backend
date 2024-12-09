package com.g2s.alpha.request.check

import com.g2s.alpha.check.MainItem
import com.g2s.alpha.enums.CheckStatus

data class CheckUpdateRequest(
    val checkId: String,
    val mainItems: List<MainItem>,
    val status: CheckStatus,
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
    val checkerId: String,
    val checkerName: String,
    val checkerContact: String,
)
