package com.moseoh.danggeunclone.core.domain.repository

import com.moseoh.danggeunclone.core.domain.BlackListToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BlackListTokenRepository : CrudRepository<BlackListToken, String> {
    fun existsByAccessToken(accessToken: String): Boolean
}