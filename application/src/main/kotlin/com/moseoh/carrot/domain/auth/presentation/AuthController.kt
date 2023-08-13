package com.moseoh.carrot.domain.auth.presentation

import com.moseoh.carrot._common.dto.EmptyResult
import com.moseoh.carrot._common.dto.ResponseDto
import com.moseoh.carrot._common.dto.SingleResult
import com.moseoh.carrot.domain.auth.application.AuthService
import com.moseoh.carrot.domain.auth.application.dto.SignInRequest
import com.moseoh.carrot.domain.auth.application.dto.SignUpRequest
import com.moseoh.carrot.domain.auth.application.dto.TokenRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/sign-in")
    fun signIn(
        @RequestBody @Valid signInRequest: SignInRequest
    ): ResponseEntity<SingleResult> {
        val tokenResponse = authService.signIn(signInRequest)

        return ResponseDto.of(
            message = "로그인 완료.",
            status = HttpStatus.OK,
            content = tokenResponse
        )
    }

    @DeleteMapping("/sign-out")
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
        @RequestBody @Valid tokenRequest: TokenRequest
    ): ResponseEntity<SingleResult> {
        val tokenResponse = authService.refresh(tokenRequest)

        return ResponseDto.of(
            message = "토큰 갱신 완료.",
            status = HttpStatus.OK,
            content = tokenResponse
        )
    }

    @GetMapping("/me")
    fun me(
        auth: Authentication
    ): ResponseEntity<SingleResult> {
        val meResponse = authService.me(auth)

        return ResponseDto.of(
            message = "사용자 조회 완료.",
            status = HttpStatus.OK,
            content = meResponse
        )
    }
}