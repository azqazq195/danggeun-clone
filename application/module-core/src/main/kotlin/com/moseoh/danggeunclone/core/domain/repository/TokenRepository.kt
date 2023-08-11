package com.moseoh.danggeunclone.core.domain.repository

import com.moseoh.danggeunclone.core.domain.Token
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TokenRepository : CrudRepository<Token, Long> {
    fun findByAccessToken(accessToken: String): Optional<Token>
    fun findByRefreshToken(refreshToken: String): Optional<Token>
}