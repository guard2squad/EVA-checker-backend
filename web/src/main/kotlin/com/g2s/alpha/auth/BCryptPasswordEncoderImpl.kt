package com.g2s.alpha.auth

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.security.crypto.password.PasswordEncoder as SpringPasswordEncoder

@Component
class BCryptPasswordEncoderImpl(
    private val encoder: SpringPasswordEncoder
) : PasswordEncoder {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    init {
        logger.debug("Using encoder instance: {}", System.identityHashCode(encoder))
    }

    override fun encode(rawPassword: String): String {
        return encoder.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return encoder.matches(rawPassword, encodedPassword)
    }
}
