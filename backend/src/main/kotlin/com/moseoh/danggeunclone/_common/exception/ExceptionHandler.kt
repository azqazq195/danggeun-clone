package com.moseoh.danggeunclone._common.exception

import com.moseoh.danggeunclone._common.application.dto.EmptyResult
import com.moseoh.danggeunclone._common.application.dto.ResponseDto
import com.moseoh.danggeunclone._common.utils.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    private val logger = logger()

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

    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ResponseEntity<EmptyResult> {
        return ResponseDto.of(e.errorCode)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<EmptyResult> {
        // TODO print stack trace only in development
        e.printStackTrace()
        logger.error("unhandled exception: {}", e.message)
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, e.message)
    }
}