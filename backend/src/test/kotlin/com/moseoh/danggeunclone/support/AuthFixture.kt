package com.moseoh.danggeunclone.support

import com.moseoh.danggeunclone.auth.application.dto.RefreshTokenRequest
import com.moseoh.danggeunclone.auth.application.dto.SignInRequest
import com.moseoh.danggeunclone.auth.application.dto.SignUpRequest
import com.moseoh.danggeunclone.auth.application.dto.TokenResponse
import com.moseoh.danggeunclone.auth.domain.Token
import java.util.*

fun createToken(
    email: String = "user@example.com",
    accessToken: String = "accessToken",
    refreshToken: String = "refreshToken",
    expiredAt: Date = Date()
): Token {
    return Token(
        email = email,
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiredAt = expiredAt
    )
}

fun createTokenResponse(
    accessToken: String = "accessToken",
    refreshToken: String = "refreshToken"
): TokenResponse {
    return TokenResponse(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun createRefreshTokenRequest(
    refreshToken: String = "refreshToken",
    accessToken: String = "accessToken"
): RefreshTokenRequest {
    return RefreshTokenRequest(
        refreshToken = refreshToken,
        accessToken = accessToken
    )
}

fun createSignInRequest(
    email: String = "user@example.com",
    password: String = "password"
): SignInRequest {
    return SignInRequest(
        email = email,
        password = password
    )
}

fun createSignUpRequest(
    name: String = "name",
    email: String = "user@example.com",
    password: String = "password",
    age: Int = 30
): SignUpRequest {
    return SignUpRequest(
        name = name,
        password = password,
        email = email,
        age = age
    )
}
