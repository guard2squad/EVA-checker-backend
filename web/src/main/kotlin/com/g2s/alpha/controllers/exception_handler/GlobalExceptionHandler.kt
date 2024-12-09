package com.g2s.alpha.controllers.exception_handler

import com.g2s.alpha.common.ErrorCode
import com.g2s.alpha.exceptions.*
import com.g2s.alpha.response.error.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

// 컨트롤러 계층에서 발생하는 비즈니스 로직 관련 예외처리
@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(CheckNotFoundException::class)
    fun handleCheckNotFoundException(
        ex: CheckNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.CHECK_NOT_FOUND
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(ApartmentNotFoundException::class)
    fun handleApartmentNotFoundException(
        ex: ApartmentNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.APARTMENT_NOT_FOUND
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        ex: UserNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.USER_NOT_FOUND
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("User not found: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(UserDeleteFailedException::class)
    fun handleUserDeletionFailedException(
        ex: UserDeleteFailedException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.USER_DELETION_FAILED
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("User deletion failed: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(ManagerNotFoundException::class)
    fun handleManagerNotFoundException(
        ex: ManagerNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.MANAGER_NOT_FOUND
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("Manager not found: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(ManagerDeleteFailedException::class)
    fun handleManagerDeletionFailedException(
        ex: ManagerDeleteFailedException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.MANAGER_DELETION_FAILED
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("User deletion failed: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(ConflictEmailException::class)
    fun handleConflictEmailException(
        ex: ConflictEmailException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.CONFLICT_EMAIL
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("Conflict Email: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(ConflictUnitException::class)
    fun handleConflictUnitException(
        ex: ConflictUnitException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.CONFLICT_UNIT
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("Conflict Unit: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(ConflictApartmentException::class)
    fun handleConflictApartmentException(
        ex: ConflictApartmentException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.CONFLICT_APARTMENT
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("Conflict Apartment: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(InvalidPasswordException::class)
    fun handleInvalidPasswordException(
        ex: InvalidPasswordException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.INVALID_PASSWORD
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("Invalid Password: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(EmailNotRegisteredException::class)
    fun handleEmailNotRegisteredException(
        ex: EmailNotRegisteredException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.EMAIL_NOT_REGISTERED
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("Email Not Registered: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    @ExceptionHandler(AdminNotRegisteredException::class)
    fun handleAdminNotRegisteredException(
        ex: AdminNotRegisteredException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.ADMIN_NOT_REGISTERED
        val errorResponse = ErrorResponse.from(errorCode, request.requestURI)
        logger.error("AdminNotRegistered: ${ex.message}", ex)
        return ResponseEntity.status(HttpStatus.valueOf(errorCode.status)).body(errorResponse)
    }

    // 일반적인 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now().toString(),
            status = 500,
            error = "INTERNAL_SERVER_ERROR",
            message = "An unexpected error occurred.",
            path = request.requestURI
        )
        logger.error("Unexpected error", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
