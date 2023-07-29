package com.moseoh.danggeunclone.auth.presentation

import com.moseoh.danggeunclone.auth.application.AuthService
import com.moseoh.danggeunclone.auth.application.dto.Token
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @GetMapping
    fun getToken(): Token {
        return authService.getToken()
    }
}