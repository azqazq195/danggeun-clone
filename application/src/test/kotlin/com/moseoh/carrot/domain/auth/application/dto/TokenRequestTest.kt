package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.AuthFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation

class TokenRequestTest : BehaviorSpec({
    val validator = Validation.buildDefaultValidatorFactory().validator

    Given("TokenRequest") {
        When("올바른 accessToken 값이 들어왔을 때") {
            val dto = AuthFixture.createTokenRequest(accessToken = "accessToken")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 accessToken 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createTokenRequest(accessToken = ""),
            )
            val results = dtos.map { validator.validate(it) }

            Then("오류가 발생한다.") {
                results.forEach {
                    it.size shouldBe 1
                    val violation = it.first()

                    violation.propertyPath.toString() shouldBe "accessToken"
                    violation.message shouldBe "공백일 수 없습니다"
                }
            }
        }

        When("올바른 refreshToken 값이 들어왔을 때") {
            val dto = AuthFixture.createTokenRequest(refreshToken = "refreshToken")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 refreshToken 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createTokenRequest(refreshToken = ""),
            )
            val results = dtos.map { validator.validate(it) }

            Then("오류가 발생한다.") {
                results.forEach {
                    it.size shouldBe 1
                    val violation = it.first()

                    violation.propertyPath.toString() shouldBe "refreshToken"
                    violation.message shouldBe "공백일 수 없습니다"
                }
            }
        }
    }
})
