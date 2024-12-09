package com.g2s.alpha.auth

import com.g2s.alpha.enums.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${alpha.jwt.secret}")
    private var secretKey: String,
    private val userDetailsService: CustomUserDetailsService
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val validityInMilliseconds: Long = 600000 * 2;  // 20분

    init {
        logger.debug("Jwt token provider init")
        logger.debug(secretKey.substring(0, 4))
        // HMAC-SHA256 방식으로 암호화
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(principal: Principal): String {
        val claims: Claims = Jwts.claims().setSubject(principal.id)
        claims["role"] = principal.role.toString()

        val now = Date()
        val validity = Date(now.time + validityInMilliseconds) // 현재 시간 + 유효 시간

        return Jwts.builder()
            .setClaims(claims) // 사용자 정보 및 권한을 클레임으로 설정
            .setIssuedAt(now)  // 토큰 발행 시간
            .setExpiration(validity) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS256, secretKey) // HMAC-SHA256을 이용해 서명
            .compact() // 직렬화하여 토큰 생성
    }

    // JWT 토큰에서 인증 정보 추출
    fun getAuthentication(token: String): Authentication {
        val id = getId(token)
        val role = getRole(token)
        val userDetails: UserDetails = userDetailsService.loadUserByTypeAndUsername(role, id)
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getId(token: String): String {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun getRole(token: String): Role {
        val role = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body["role"].toString()

        return Role.valueOf(role)
    }

    // JWT 토큰 유효성 검증
    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            !claims.body.expiration.before(Date()) // 만료 시간이 현재 시간보다 이후인지 확인
        } catch (e: ExpiredJwtException) {
            logger.warn("토큰 만료됨: ${e.claims.expiration}")
            false
        } catch (e: Exception) {
            throw e
        }
    }

    // HTTP 요청 헤더에서 토큰을 추출하는 메서드 (Authorization 헤더에서 Bearer 토큰 형태로 전달됨)
    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7) // "Bearer " 이후의 토큰 값 반환
        } else null
    }
}