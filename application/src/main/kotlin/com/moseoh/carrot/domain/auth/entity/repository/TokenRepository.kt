package com.moseoh.carrot.domain.auth.entity.repository

import com.moseoh.carrot.domain.auth.entity.Token
import com.moseoh.carrot.domain.auth.exception.TokenNotFoundException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TokenRepository : CrudRepository<Token, Long> {
    fun findByAccessToken(accessToken: String): Optional<Token>
    fun findByRefreshToken(refreshToken: String): Optional<Token>
}

fun TokenRepository.getEntityByAccessToken(accessToken: String): Token =
    findByAccessToken(accessToken).orElseThrow(::TokenNotFoundException)

fun TokenRepository.getEntityByRefreshToken(refreshToken: String): Token =
    findByRefreshToken(refreshToken).orElseThrow(::TokenNotFoundException)