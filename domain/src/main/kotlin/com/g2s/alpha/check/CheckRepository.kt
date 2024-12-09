package com.g2s.alpha.check

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface CheckRepository {
    fun save(check: Check): Check
    fun findChecks(
        zonecode: String,
        buildingNumber: String?,
        unitNumber: String?,
        checkPeriod: CheckPeriod?,
        pageable: Pageable
    ): Page<Check>
    fun findByCheckId(checkId: String): Check?
    fun update(check: Check): Check?
    fun deleteCheck(checkId: String): Boolean
}
