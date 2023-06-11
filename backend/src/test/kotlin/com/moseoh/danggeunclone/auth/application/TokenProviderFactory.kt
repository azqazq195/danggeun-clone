package com.moseoh.danggeunclone.auth.application

import com.moseoh.danggeunclone._common.utils.RedisDao
import com.moseoh.danggeunclone.auth.domain.repository.TokenRepository
import com.moseoh.danggeunclone.user.domain.repository.UserRepository

class TokenProviderFactory(
    accessTokenExpireTime: Long,
    refreshTokenExpireTime: Long,
    redisDao: RedisDao,
    userRepository: UserRepository,
    tokenRepository: TokenRepository
) : TokenProvider(
    accessSecret = "and0LWV4YW1wbGUtYWNjZXNzLXRva2VuLXNlY3JldC1rZXk=",
    accessTokenExpireTime,
    refreshSecret = "and0LWV4YW1wbGUtcmVmcmVzaC10b2tlbi1zZWNyZXQta2V5",
    refreshTokenExpireTime,
    redisDao,
    userRepository,
    tokenRepository
) {
    init {
        super.init()
    }
}