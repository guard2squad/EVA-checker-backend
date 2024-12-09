package com.g2s.alpha.auth

import com.g2s.alpha.exceptions.AuthenticationFailedException
import com.g2s.alpha.exceptions.CustomAuthenticationException
import com.g2s.alpha.exceptions.TokenInvalidException
import com.g2s.alpha.exceptions.TokenNotFoundException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 로그인, 회원가입, ADMIN은 필터 통과
        val requestURI = request.requestURI
        if (requestURI.startsWith("/api/auth")
            || requestURI.startsWith("/api/apartment-filter")
            || requestURI.startsWith("/api/admin")
            || requestURI.startsWith("/health")
        ) {
            filterChain.doFilter(request, response)
            return
        }

        val token = jwtTokenProvider.resolveToken(request)

        try {
            if (token == null) {
                logger.debug("Token not found")
                throw TokenNotFoundException()
            } else if (!jwtTokenProvider.validateToken(token)) {
                logger.debug("Token not valid")
                throw TokenInvalidException()
            } else {
                val auth = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: CustomAuthenticationException) {
            logger.error("Authentication failed: ${ex.errorCode.message}", ex)
            SecurityContextHolder.clearContext()
            throw ex // AuthenticationEntryPoint가 처리
        } catch (ex: Exception) {
            logger.error("Authentication failed", ex)
            SecurityContextHolder.clearContext()
            throw AuthenticationFailedException()
        }

        filterChain.doFilter(request, response)
    }
}
