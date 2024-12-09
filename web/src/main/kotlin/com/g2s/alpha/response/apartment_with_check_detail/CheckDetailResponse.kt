package com.g2s.alpha.response.apartment_with_check_detail

data class CheckDetailResponse(
    val good: Int,
    val fault: Int,
    val notChecked: Int,
    val notApplicable: Int,
)
