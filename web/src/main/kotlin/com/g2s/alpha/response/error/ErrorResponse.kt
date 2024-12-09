package com.g2s.alpha.response.error

import com.g2s.alpha.common.ErrorCode
import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
) {
    companion object {
        fun from(errorCode: ErrorCode, path: String): ErrorResponse {
            return ErrorResponse(
                timestamp = LocalDateTime.now().toString(),
                status = errorCode.status,
                error = errorCode.code,
                message = errorCode.message,
                path = path
            )
        }
    }
}
