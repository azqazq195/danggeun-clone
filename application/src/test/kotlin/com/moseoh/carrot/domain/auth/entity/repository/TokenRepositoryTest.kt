package com.moseoh.carrot.domain.auth.entity.repository

import com.moseoh.carrot.AuthFixture
import com.moseoh.carrot.domain.auth.exception.TokenNotFoundException
import com.moseoh.carrot.helper.RedisRepositoryTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class TokenRepositoryTest @Autowired constructor(
    private val tokenRepository: TokenRepository,
) : RedisRepositoryTest({
    Given("findByRefreshToken") {
        val token = AuthFixture.createToken()
        tokenRepository.save(token)

        When("존재하는 토큰의 refreshToken로 찾는다면") {
            val result = tokenRepository.findByRefreshToken(token.refreshToken)

            Then("Token이 담긴 Optional을 반환한다.") {
                result.get().refreshToken shouldBe token.refreshToken
            }
        }

        When("존재하지 않는 토큰의 refreshToken로 찾는다면") {
            val result = tokenRepository.findByRefreshToken("not exists token")

            Then("빈 Optional을 반환한다.") {
                result shouldBe Optional.empty()
            }
        }
    }

    Given("getEntityByAccessToken") {
        val token = AuthFixture.createToken()
        tokenRepository.save(token)

        When("존재하는 토큰의 accessToken 으로 찾는다면") {
            val result = tokenRepository.getEntityByAccessToken(token.accessToken)

            Then("Token을 반환한다.") {
                result.accessToken shouldBe token.accessToken
            }
        }

        When("존재하지 않는 토큰의 accessToken 으로 찾는다면") {
            val exception = shouldThrow<TokenNotFoundException> {
                tokenRepository.getEntityByAccessToken("not exists token")
            }

            Then("TokenNotFoundException 이 발생한다.") {
                exception shouldBe TokenNotFoundException()
            }
        }
    }

    Given("getEntityByRefreshToken") {
        val token = AuthFixture.createToken()
        tokenRepository.save(token)

        When("존재하는 토큰의 refreshToken 으로 찾는다면") {
            val result = tokenRepository.getEntityByRefreshToken(token.refreshToken)

            Then("Token 을 반환한다.") {
                result.refreshToken shouldBe token.refreshToken
            }
        }

        When("존재하지 않는 토큰의 refreshToken 으로 찾는다면") {
            val exception = shouldThrow<TokenNotFoundException> {
                tokenRepository.getEntityByRefreshToken("not exists token")
            }

            Then("TokenNotFoundException 이 발생한다.") {
                exception shouldBe TokenNotFoundException()
            }
        }
    }
})