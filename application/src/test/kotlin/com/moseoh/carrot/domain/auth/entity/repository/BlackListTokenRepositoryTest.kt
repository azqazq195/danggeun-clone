package com.moseoh.carrot.domain.auth.entity.repository

import com.moseoh.carrot.AuthFixture
import com.moseoh.carrot.helper.RedisRepositoryTest
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class BlackListTokenRepositoryTest @Autowired constructor(
    private val blackListTokenRepository: BlackListTokenRepository,
) : RedisRepositoryTest({
    Given("existsByAccessToken") {
        val token = AuthFixture.createBlackListToken()
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