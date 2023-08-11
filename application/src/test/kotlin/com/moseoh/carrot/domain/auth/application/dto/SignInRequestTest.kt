package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.AuthFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation

class SignInRequestTest : BehaviorSpec({
    val validator = Validation.buildDefaultValidatorFactory().validator

    Given("SignInRequest") {
        When("올바른 email 값이 들어왔을 때") {
            val dto = AuthFixture.createSignInRequest(email = "moseoh@danggeun.com")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 email 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createSignInRequest(email = ""),
                AuthFixture.createSignInRequest(email = "moseohdanggeun.com"),
                AuthFixture.createSignInRequest(email = "moseoh@danggeuncom"),
                AuthFixture.createSignInRequest(email = "@danggeun.com"),
                AuthFixture.createSignInRequest(email = "moseoh@.com"),
                AuthFixture.createSignInRequest(email = "moseoh@danggeun.com."),
            )
            val results = dtos.map { validator.validate(it) }

            Then("오류가 발생한다.") {
                results.forEach {
                    it.size shouldBe 1
                    val violation = it.first()

                    violation.propertyPath.toString() shouldBe "email"
                    violation.message shouldBe "올바른 형식의 이메일 주소여야 합니다"
                }
            }
        }

        When("올바른 password 값이 들어왔을 때") {
            val dto = AuthFixture.createSignInRequest(password = "password")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 password 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createSignInRequest(password = ""),
                AuthFixture.createSignInRequest(password = "zsdw"),
                AuthFixture.createSignInRequest(password = "1234567"),
                AuthFixture.createSignInRequest(password = "123456789012345678901"),
                AuthFixture.createSignInRequest(password = "ejioij2o3ijlK@f90j()9jsdflakj"),
            )
            val results = dtos.map { validator.validate(it) }

            Then("오류가 발생한다.") {
                results.forEach {
                    it.size shouldBe 1
                    val violation = it.first()

                    violation.propertyPath.toString() shouldBe "password"
                    violation.message shouldBe "크기가 8에서 20 사이여야 합니다"
                }
            }
        }
    }
})
