package com.g2s.alpha.response.check

import com.g2s.alpha.check.Check
import com.g2s.alpha.check.CheckPeriod
import com.g2s.alpha.check.MainItem
import com.g2s.alpha.enums.CheckStatus

data class CheckResponse(
    val checkId: String,
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
) {
    companion object {
        fun from(check: Check): CheckResponse {
            return CheckResponse(
                checkId = check.checkId,
                mainItems = check.mainItems,
                status = check.status,
                checkPeriod = check.checkPeriod,
                zonecode = check.zonecode,
                buildingNumber = check.buildingNumber,
                unitNumber = check.unitNumber,
                checkCompletePeriod = check.checkCompletePeriod,
                checkerId = check.checkerId,
                checkerName = check.checkerName,
                checkerContact = check.checkerContact
            )
        }
    }
}
