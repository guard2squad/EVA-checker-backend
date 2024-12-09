package com.g2s.alpha.check

import com.g2s.alpha.enums.CheckStatus
import java.util.*

data class Check(
    val checkId: String = UUID.randomUUID().toString(),
    val mainItems: List<MainItem>,
    val status: CheckStatus,
    val checkPeriod: CheckPeriod,
    val zonecode: String,
    val buildingNumber: String,
    val unitNumber: String,
    var checkCompletePeriod: String,
    val checkerId: String? = null,
    val checkerName: String? = null,
    val checkerContact: String? = null,
)
