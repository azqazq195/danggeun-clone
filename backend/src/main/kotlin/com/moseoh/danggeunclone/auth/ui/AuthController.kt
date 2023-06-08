package com.moseoh.danggeunclone.auth.ui

import com.moseoh.danggeunclone._common.application.dto.EmptyResult
import com.moseoh.danggeunclone._common.application.dto.ResponseDto
import com.moseoh.danggeunclone._common.application.dto.SingleResult
import com.moseoh.danggeunclone.auth.application.AuthService
import com.moseoh.danggeunclone.auth.application.dto.RefreshTokenRequest
import com.moseoh.danggeunclone.auth.application.dto.SignInRequest
import com.moseoh.danggeunclone.auth.application.dto.SignUpRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody @Valid signInRequest: SignInRequest
    ): ResponseEntity<SingleResult> {
        val tokenResponse = authService.signIn(signInRequest)

        return ResponseDto.of(
            message = "로그인 완료.",
            status = HttpStatus.OK,
            data = tokenResponse
        )
    }

    @GetMapping("/sign-out")
    fun signOut(
        auth: Authentication
    ): ResponseEntity<EmptyResult> {
        authService.signOut(auth)

        return ResponseDto.of(
            message = "로그아웃 완료.",
            status = HttpStatus.OK,
        )
    }

    @PostMapping("/sign-up")
    fun signUp(
        @RequestBody @Valid signUpRequest: SignUpRequest
    ): ResponseEntity<EmptyResult> {
        authService.signUp(signUpRequest)

        return ResponseDto.of(
            message = "사용자 생성 완료.",
            status = HttpStatus.CREATED
        )
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody @Valid refreshTokenRequest: RefreshTokenRequest
    ): ResponseEntity<SingleResult> {
        val tokenResponse = authService.refresh(refreshTokenRequest)

        return ResponseDto.of(
            message = "토큰 갱신 완료.",
            status = HttpStatus.OK,
            data = tokenResponse
        )
    }

    @GetMapping("/me")
    fun me(
        auth: Authentication
    ): ResponseEntity<SingleResult> {
        val user = authService.me(auth)

        return ResponseDto.of(
            message = "사용자 조회 완료.",
            status = HttpStatus.OK,
            data = user
        )
    }
}