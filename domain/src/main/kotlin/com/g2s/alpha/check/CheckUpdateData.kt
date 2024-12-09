package com.g2s.alpha.check

import com.g2s.alpha.enums.CheckStatus

data class CheckUpdateData(
    val checkId: String,
    val mainItems: List<MainItem>,
    val status: CheckStatus,
    val checkCompletePeriod: String,
    val checkerId: String,
    val checkerName: String,
    val checkerContact: String,
)
