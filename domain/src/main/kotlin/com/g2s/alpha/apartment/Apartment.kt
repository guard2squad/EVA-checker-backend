package com.g2s.alpha.apartment

import com.g2s.alpha.check.CheckPeriod

data class Apartment(
    val zonecode: String,
    val name: String,
    val address: String,
    val checkPeriod: CheckPeriod,
    val buildings: List<Building>,
    val completionDate: String, // 준공일자
)
