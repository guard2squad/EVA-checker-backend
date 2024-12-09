package com.g2s.alpha.auth

import org.springframework.stereotype.Component

@Component
interface PasswordEncoder {
    fun encode(rawPassword: String): String
    fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean
}