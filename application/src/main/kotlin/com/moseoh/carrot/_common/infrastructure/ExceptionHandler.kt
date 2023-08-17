package com.moseoh.carrot._common.infrastructure

import com.moseoh.carrot._common.dto.EmptyResult
import com.moseoh.carrot._common.dto.ResponseDto
import com.moseoh.carrot._common.exception.ApiException
import com.moseoh.carrot._common.exception.ErrorCode
import com.moseoh.carrot._common.utils.logger
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    private val logger = logger()

    // Api Exception
    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ResponseEntity<EmptyResult> {
        return ResponseDto.of(e.errorCode)
    }

    // Request Dto 양식이 올바르지 않는 경우 발생
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<EmptyResult> {
        return ResponseDto.of(ErrorCode.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<EmptyResult> {
        fun MethodArgumentNotValidException.messages(): String {
            return bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage.orEmpty()}" }
        }
        return ResponseDto.of(HttpStatus.BAD_REQUEST, e.messages())
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    fun handleAccessDeniedException(e: org.springframework.security.access.AccessDeniedException): ResponseEntity<EmptyResult> {
        return ResponseDto.of(ErrorCode.FORBIDDEN)
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleBadRequestException(e: RuntimeException): ResponseEntity<EmptyResult> {
        return ResponseDto.of(HttpStatus.BAD_REQUEST, e.message)
    }

    @Order(Int.MAX_VALUE)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<EmptyResult> {
        logger.error("unhandled exception: {}", e.message)
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }
}