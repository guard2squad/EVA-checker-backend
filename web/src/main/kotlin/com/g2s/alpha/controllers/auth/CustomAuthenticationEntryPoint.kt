package com.g2s.alpha.controllers.auth

import com.g2s.alpha.common.ObjectMapperProvider
import com.g2s.alpha.exceptions.CustomAuthenticationException
import com.g2s.alpha.response.error.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.LocalDateTime

// Spring Security 필터 체인에서 발생하는 인증 관련 예외를 처리
@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint::class.java)

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorResponse: ErrorResponse = when (authException) {
            is CustomAuthenticationException -> ErrorResponse(
                timestamp = LocalDateTime.now().toString(),
                status = authException.errorCode.status,
                error = authException.errorCode.code,
                message = authException.errorCode.message,
                path = request.requestURI
            )
            is InsufficientAuthenticationException -> ErrorResponse(
                timestamp = LocalDateTime.now().toString(),
                status = 401,
                error = "AUTHENTICATION_FAILED",
                message = "Authentication failed",
                path = request.requestURI
            )
            else -> ErrorResponse(
                timestamp = LocalDateTime.now().toString(),
                status = 500,
                error = "INTERNAL_SERVER_ERROR",
                message = "An unexpected error occurred.",
                path = request.requestURI
            )
        }

        logger.error("Authentication error: ${errorResponse.message}", authException)
        val om = ObjectMapperProvider.get()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = errorResponse.status
        response.writer.write(om.writeValueAsString(errorResponse))
    }
}
