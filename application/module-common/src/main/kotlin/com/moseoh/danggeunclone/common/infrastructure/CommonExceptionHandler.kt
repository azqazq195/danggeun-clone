package com.moseoh.danggeunclone.common.infrastructure

import com.moseoh.danggeunclone.common.dto.EmptyResult
import com.moseoh.danggeunclone.common.dto.ResponseDto
import com.moseoh.danggeunclone.common.exception.ApiException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonExceptionHandler {
    // Custom Api Exception
    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ResponseEntity<EmptyResult> {
        return ResponseDto.of(e.errorCode)
    }
}