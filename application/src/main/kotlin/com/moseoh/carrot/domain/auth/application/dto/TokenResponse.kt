package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.domain.auth.entity.Token

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
) {
    constructor(token: Token) : this(
        accessToken = token.accessToken,
        refreshToken = token.refreshToken
    )
}