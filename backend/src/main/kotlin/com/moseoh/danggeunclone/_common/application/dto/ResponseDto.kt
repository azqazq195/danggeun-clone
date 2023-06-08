package com.moseoh.danggeunclone._common.application.dto

import com.moseoh.danggeunclone._common.exception.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ResponseDto {
    companion object {
        // noContent
        fun of(
            status: HttpStatus,
            message: String? = null
        ): ResponseEntity<EmptyResult> =
            ResponseEntity(
                EmptyResult(
                    statusCode = status.value(),
                    message = message,
                ), status
            )

        // single data
        fun of(
            status: HttpStatus,
            message: String? = null,
            data: Any
        ): ResponseEntity<SingleResult> =
            ResponseEntity(
                SingleResult(
                    statusCode = status.value(),
                    message = message,
                    data = data
                ), status
            )

        // list data
        fun of(
            status: HttpStatus,
            message: String? = null,
            data: List<Any>
        ): ResponseEntity<ListResult> =
            ResponseEntity(
                ListResult(
                    statusCode = status.value(),
                    message = message,
                    data = data
                ), status
            )

        // error response
        fun of(
            error: ErrorCode
        ): ResponseEntity<EmptyResult> =
            ResponseEntity(
                EmptyResult(
                    statusCode = error.status.value(),
                    message = error.message
                ), error.status
            )
    }
}