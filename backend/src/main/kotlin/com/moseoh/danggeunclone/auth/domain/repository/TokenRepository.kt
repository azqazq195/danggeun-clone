package com.moseoh.danggeunclone.auth.domain.repository

import com.moseoh.danggeunclone.auth.domain.Token
import com.moseoh.danggeunclone.auth.exception.NotFoundRefreshTokenException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

fun TokenRepository.getByRefreshToken(refreshToken: String): Token = findByRefreshToken(refreshToken)
    ?: throw NotFoundRefreshTokenException()

@Repository
interface TokenRepository : JpaRepository<Token, String> {
    fun findByRefreshToken(refreshToken: String): Token?
    fun deleteByAccessToken(accessToken: String)
}