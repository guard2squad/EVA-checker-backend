package com.g2s.alpha.response.common

//@RestControllerAdvice
//class GlobalErrorHandler(
//    private val apiResponseService: ApiResponseService
//) {
//    @ExceptionHandler(value = [Exception::class])
//    fun handleException(
//        exception: Exception,
//        request: HttpServletRequest
//    ): ResponseEntity<ApiResult> {
//        val apiResponse = apiResponseService.failure(request, exception)
//
//        return apiResponse.toResponseEntity()
//    }
//}