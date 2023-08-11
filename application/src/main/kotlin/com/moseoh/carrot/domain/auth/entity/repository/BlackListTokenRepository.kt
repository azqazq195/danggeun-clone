package com.moseoh.carrot.domain.auth.entity.repository

import com.moseoh.carrot.domain.auth.entity.BlackListToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BlackListTokenRepository : CrudRepository<BlackListToken, String> {
    fun existsByAccessToken(accessToken: String): Boolean
}