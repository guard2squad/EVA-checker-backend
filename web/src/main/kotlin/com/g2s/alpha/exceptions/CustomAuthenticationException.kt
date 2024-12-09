package com.g2s.alpha.exceptions

import com.g2s.alpha.common.ErrorCode
import org.springframework.security.core.AuthenticationException


abstract class CustomAuthenticationException(
    val errorCode: ErrorCode,
    message: String
) : AuthenticationException(message)
