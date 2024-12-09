package com.g2s.alpha.check

data class CheckPeriod(
    val startDate: String,
    val endDate: String,
) {
    override fun toString(): String {
        return "$startDate to $endDate"
    }
}
