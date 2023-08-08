package com.moseoh.danggeunclone.common.dto

import com.moseoh.danggeunclone.common.exception.ErrorCode
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
                    message = message,
                ), status
            )

        // single data
        fun of(
            status: HttpStatus,
            message: String? = null,
            content: Any
        ): ResponseEntity<SingleResult> =
            ResponseEntity(
                SingleResult(
                    message = message,
                    content = content
                ), status
            )

        // list data
        fun of(
            status: HttpStatus,
            message: String? = null,
            content: List<Any>
        ): ResponseEntity<ListResult> =
            ResponseEntity(
                ListResult(
                    message = message,
                    content = content
                ), status
            )

        // error response
        fun of(
            error: ErrorCode
        ): ResponseEntity<EmptyResult> =
            ResponseEntity(
                EmptyResult(
                    message = error.message
                ), error.status
            )
    }
}