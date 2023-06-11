package com.moseoh.danggeunclone.auth.application

import com.moseoh.danggeunclone._common.utils.RedisDao
import com.moseoh.danggeunclone.auth.application.dto.TokenResponse
import com.moseoh.danggeunclone.auth.domain.repository.TokenRepository
import com.moseoh.danggeunclone.auth.domain.repository.getByRefreshToken
import com.moseoh.danggeunclone.support.createRefreshTokenRequest
import com.moseoh.danggeunclone.support.createToken
import com.moseoh.danggeunclone.support.createUser
import com.moseoh.danggeunclone.user.domain.repository.UserRepository
import com.moseoh.danggeunclone.user.domain.repository.getByEmail
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

internal class TokenProviderTest : BehaviorSpec({
    val accessTokenExpireTime = 720000L
    val refreshTokenExpireTime = 1209600000L
    val redisDao = mockk<RedisDao>()
    val userRepository = mockk<UserRepository>()
    val tokenRepository = mockk<TokenRepository>()
    val tokenProvider = spyk<TokenProvider>(
        TokenProviderFactory(
            accessTokenExpireTime,
            refreshTokenExpireTime,
            redisDao,
            userRepository,
            tokenRepository
        )
    )

    afterTest {
        clearAllMocks()
    }

    Given("create") {
        val user = createUser()
        val token = createToken()
        val tokenResponse = token.let(::TokenResponse)

        When("요청을 한다면") {
            every { tokenRepository.save(any()) } returns token

            val result = tokenProvider.create(user)

            Then("토큰 정보를 반환한다.") {
                result shouldBe tokenResponse

                verify(exactly = 1) { tokenRepository.save(any()) }
            }
        }
    }

    Given("deleteByAccessToken") {
        val accessToken = "accessToken"

        When("요청을 한다면") {
            every { tokenProvider["setBlackList"](accessToken) } returns Unit
            every { tokenRepository.deleteByAccessToken(accessToken) } just runs

            tokenProvider.deleteByAccessToken(accessToken)

            Then("토큰을 삭제한다.") {
                verify(exactly = 1) { tokenRepository.deleteByAccessToken(accessToken) }
            }
        }
    }

    Given("refresh") {
        val refreshTokenRequest = createRefreshTokenRequest()
        val token = createToken(refreshToken = refreshTokenRequest.refreshToken)
        val user = createUser()
        val tokenResponse = token.let(::TokenResponse)

        When("요청을 한다면") {
            every { tokenRepository.getByRefreshToken(refreshTokenRequest.refreshToken) } returns token
            every { tokenProvider["deleteByAccessToken"](token.accessToken) } returns Unit
            every { userRepository.getByEmail(token.email) } returns user
            every { tokenRepository.save(any()) } returns token

            val result = tokenProvider.refresh(refreshTokenRequest)

            Then("토큰을 반환한다.") {
                result shouldBe tokenResponse

                verify(exactly = 1) { tokenRepository.getByRefreshToken(refreshTokenRequest.refreshToken) }
                verify(exactly = 1) { userRepository.getByEmail(token.email) }
                verify(exactly = 1) { tokenRepository.save(any()) }
            }
        }
    }
})