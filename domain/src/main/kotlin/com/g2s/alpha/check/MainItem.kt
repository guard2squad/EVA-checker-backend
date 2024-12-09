package com.g2s.alpha.check

import com.g2s.alpha.enums.CheckMainItemStatus

data class MainItem(
    val name: String,
    val subItems: List<SubItem>,
    val status: CheckMainItemStatus
)
