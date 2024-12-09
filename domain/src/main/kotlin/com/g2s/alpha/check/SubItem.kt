package com.g2s.alpha.check

import com.g2s.alpha.enums.CheckSubItemStatus

data class SubItem(
    val name: String,
    val videoUrl: String,
    val description: String,
    val status: CheckSubItemStatus,
)
