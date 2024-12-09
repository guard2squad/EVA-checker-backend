package com.g2s.alpha.exceptions

import com.g2s.alpha.common.ErrorCode

class TokenInvalidException: CustomAuthenticationException(
    ErrorCode.TOKEN_INVALID,
    "Token invalid"
)
