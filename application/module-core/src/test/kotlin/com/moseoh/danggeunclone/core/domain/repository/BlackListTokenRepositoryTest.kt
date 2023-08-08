package com.moseoh.danggeunclone.core.domain.repository

import com.moseoh.danggeunclone.core.BlackListTokenFixtures
import com.moseoh.danggeunclone.core.EmbeddedRedisConfig
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import

@DataRedisTest
@Import(EmbeddedRedisConfig::class)
class BlackListTokenRepositoryTest @Autowired constructor(
    private val blackListTokenRepository: BlackListTokenRepository,
) : BehaviorSpec({
    Given("existsByAccessToken") {
        val token = BlackListTokenFixtures.createEntity()
        blackListTokenRepository.save(token)

        When("존재하는 토큰의 accessToken로 찾는다면") {
            val result = blackListTokenRepository.existsByAccessToken(token.accessToken)

            Then("true를 반환한다.") {
                result shouldBe true
            }
        }

        When("존재하지 않는 토큰의 accessToken로 찾는다면") {
            val result = blackListTokenRepository.existsByAccessToken("not exists token")

            Then("빈 Optional을 반환한다.") {
                result shouldBe false
            }
        }
    }

})