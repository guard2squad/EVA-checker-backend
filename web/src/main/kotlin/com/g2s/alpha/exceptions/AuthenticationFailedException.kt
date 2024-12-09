package com.g2s.alpha.exceptions

import com.g2s.alpha.common.ErrorCode

class AuthenticationFailedException : CustomAuthenticationException(
    ErrorCode.AUTHENTICATION_FAILED,
    "Authentication failed"
)
