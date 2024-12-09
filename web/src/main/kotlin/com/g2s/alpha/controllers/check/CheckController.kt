package com.g2s.alpha.controllers.check

import com.g2s.alpha.check.CheckPeriod
import com.g2s.alpha.check.CheckUseCase
import com.g2s.alpha.request.check.CheckUpdateRequest
import com.g2s.alpha.response.check.CheckResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/checks")
class CheckController(
    private val checkUseCase: CheckUseCase,
) {
    @GetMapping("/{checkId}")
    fun getCheck(@PathVariable checkId: String): ResponseEntity<CheckResponse> {
        val check = checkUseCase.findCheck(checkId)
        val checkResponse = CheckResponse.from(check)
        return ResponseEntity.ok(checkResponse)
    }

    @GetMapping
    fun getChecks(
        @RequestParam zonecode: String,
        @RequestParam(required = false) buildingNumber: String?,
        @RequestParam(required = false) unitNumber: String?,
        @RequestParam(required = false) startDate: String?,
        @RequestParam(required = false) endDate: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<CheckResponse>> {
        if ((startDate == null) != (endDate == null)) {
            throw IllegalArgumentException("startDate and endDate must both be present or both be null")
        }

        val checkPeriod = if (startDate != null && endDate != null) {
            CheckPeriod(startDate, endDate)
        } else {
            null
        }

        val pageable: Pageable = PageRequest.of(page, size)
        val checks = checkUseCase.getChecks(
            zonecode = zonecode,
            buildingNumber = buildingNumber,
            unitNumber = unitNumber,
            checkPeriod = checkPeriod,
            pageable = pageable,
        )
        val checkResponses = checks.map { CheckResponse.from(it) }

        return ResponseEntity.ok(checkResponses)
    }

    @PutMapping("/{checkId}")
    fun updateCheck(
        @PathVariable checkId: String,
        @RequestBody checkUpdateRequest: CheckUpdateRequest
    ): ResponseEntity<CheckResponse> {
        val updatedCheck = checkUseCase.updateCheck(
            checkId = checkId,
            mainItems = checkUpdateRequest.mainItems,
            status = checkUpdateRequest.status,
            checkCompletePeriod = LocalDate.now().toString(), // modifiedAt == check completion time
            checkerId = checkUpdateRequest.checkerId,
            checkerName = checkUpdateRequest.checkerName,
            checkerContact = checkUpdateRequest.checkerContact
        )
        val checkResponse = CheckResponse.from(updatedCheck)
        return ResponseEntity.ok(checkResponse)
    }
}
