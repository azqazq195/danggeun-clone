package com.moseoh.danggeunclone.core.domain.repository

import com.moseoh.danggeunclone.core.EmbeddedRedisConfig
import com.moseoh.danggeunclone.core.TokenFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import java.util.*

@DataRedisTest
@Import(EmbeddedRedisConfig::class)
class TokenRepositoryTest @Autowired constructor(
    private val tokenRepository: TokenRepository,
) : BehaviorSpec({
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