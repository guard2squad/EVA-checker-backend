package com.g2s.alpha.common

enum class ErrorCode(val code: String, val status: Int, val message: String) {
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", 401, "Token not found"),
    TOKEN_INVALID("TOKEN_INVALID", 401, "Token invalid"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", 401, "Authentication failed"),
    EMAIL_NOT_REGISTERED("EMAIL_NOT_REGISTERED", 401, "Email not registered"),
    ADMIN_NOT_REGISTERED("ADMIN_NOT_REGISTERED", 401, "Admin not registered"),
    USER_NOT_FOUND("USER_NOT_FOUND", 404, "User not found"),
    USER_DELETION_FAILED("USER_DELETION_FAILED", 500, "Failed to delete user"),
    MANAGER_NOT_FOUND("MANAGER_NOT_FOUND", 404, "Manager not found"),
    MANAGER_DELETION_FAILED("MANAGER_DELETION_FAILED", 500, "Failed to delete manager"),
    INVALID_PASSWORD("INVALID_PASSWORD", 401, "Invalid password"),
    CONFLICT_EMAIL("CONFLICT_EMAIL", 409, "Conflict email"),
    CONFLICT_UNIT("CONFLICT_UNIT", 409, "Conflict unit"),
    CONFLICT_APARTMENT("CONFLICT_APARTMENT", 409, "Conflict apartment"),
    CHECK_NOT_FOUND("CHECK_NOT_FOUND", 404, "Check not found"),
    APARTMENT_NOT_FOUND("APARTMENT_NOT_FOUND", 404, "Apartment not found"),
}
