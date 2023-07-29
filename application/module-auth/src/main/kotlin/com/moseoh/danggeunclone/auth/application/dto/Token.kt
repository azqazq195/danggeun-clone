package com.moseoh.danggeunclone.auth.application.dto

data class Token(
    val accessToken: String = "token",
    val refreshToken: String = "token",
)