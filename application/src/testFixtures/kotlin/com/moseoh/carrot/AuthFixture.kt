package com.moseoh.carrot

import com.moseoh.carrot.domain.auth.application.dto.*
import com.moseoh.carrot.domain.auth.entity.BlackListToken
import com.moseoh.carrot.domain.auth.entity.Token
import com.moseoh.carrot.domain.user.entity.Role
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.time.LocalDateTime
import java.util.*

object AuthFixture {
    fun createToken(
        userId: Long = 1L,
        accessToken: String = "accessToken",
        refreshToken: String = "refreshToken",
        expiration: Long = 1000,
    ): Token {
        return Token(
            userId,
            accessToken,
            refreshToken,
            expiration,
        )
    }

    fun createBlackListToken(
        accessToken: String = "accessToken",
        expiration: Long = 1000,
    ): BlackListToken {
        return BlackListToken(
            accessToken,
            expiration,
        )
    }

    fun createSignInRequest(
        email: String = "moseoh@danggeun.com",
        password: String = "password"
    ): SignInRequest {
        return SignInRequest(
            email,
            password
        )
    }

    fun createSignUpRequest(
        email: String = "moseoh@danggeun.com",
        password: String = "password",
        nickname: String = "nickname",
    ): SignUpRequest {
        return SignUpRequest(
            email,
            password,
            nickname
        )
    }

    fun createTokenRequest(
        accessToken: String = "accessToken",
        refreshToken: String = "refreshToken",
    ): TokenRequest {
        return TokenRequest(
            accessToken,
            refreshToken,
        )
    }

    fun createTokenResponse(
        accessToken: String = "accessToken",
        refreshToken: String = "refreshToken",
    ): TokenResponse {
        return TokenResponse(
            accessToken,
            refreshToken,
        )
    }

    fun createMeResponse(
        id: Long = 1L,
        email: String = "moseoh@danggeun.com",
        nickname: String = "moseoh",
        role: Role = Role.USER,
        createdAt: LocalDateTime = LocalDateTime.now(),
        modifiedAt: LocalDateTime = LocalDateTime.now()
    ): MeResponse {
        return MeResponse(
            id,
            email,
            nickname,
            role,
            createdAt,
            modifiedAt,
        )
    }

    fun createAuthentication(): Authentication {
        return UsernamePasswordAuthenticationToken(
            1L,
            "accessToken",
            Collections.emptyList()
        )
    }
}