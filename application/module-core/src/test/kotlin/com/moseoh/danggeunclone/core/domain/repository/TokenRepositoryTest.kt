package com.moseoh.danggeunclone.core.domain.repository

import com.moseoh.danggeunclone.core.TokenFixture
import com.moseoh.danggeunclone.core.common.RedisRepositoryTest
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class TokenRepositoryTest @Autowired constructor(
    private val tokenRepository: TokenRepository,
) : RedisRepositoryTest({
    Given("findByRefreshToken") {
        val token = TokenFixture.createEntity()
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
})