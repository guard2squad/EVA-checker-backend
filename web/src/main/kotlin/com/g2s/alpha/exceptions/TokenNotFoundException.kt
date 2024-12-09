package com.g2s.alpha.exceptions

import com.g2s.alpha.common.ErrorCode

class TokenNotFoundException : CustomAuthenticationException(
    ErrorCode.TOKEN_NOT_FOUND,
    "Token not found"
)
