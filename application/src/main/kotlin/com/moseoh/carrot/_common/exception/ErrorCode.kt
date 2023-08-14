package com.moseoh.carrot._common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    /**
     * COMMON
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "올바른 요청형식이 아닙니다."),

    /**
     * AUTH
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증정보가 유효하지 않습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "인증정보가 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "인증정보가 유효하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "인증정보가 유효하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "인증정보가 만료되었습니다."),
    TOKEN_NOT_MATCH(HttpStatus.UNAUTHORIZED, "토큰 정보가 일치하지 않습니다."),


    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일 입니다."),

    /**
     * TOKEN
     */
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),

    /**
     * USER
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    /**
     * REGION
     */
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "지역을 찾을 수 없습니다."),

    /**
     * POST
     */
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),

    /**
     * CATEGORY
     */
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),

}