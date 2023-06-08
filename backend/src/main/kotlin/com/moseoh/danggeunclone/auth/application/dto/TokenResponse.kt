package com.moseoh.danggeunclone.auth.application.dto

import com.moseoh.danggeunclone.auth.domain.Token

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
) {
    constructor(token: Token) : this(
        accessToken = token.accessToken,
        refreshToken = token.refreshToken
    )
}