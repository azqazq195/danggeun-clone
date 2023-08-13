package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.AuthFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation

class SignUpRequestTest : BehaviorSpec({
    val validator = Validation.buildDefaultValidatorFactory().validator

    Given("SignUpRequest") {
        When("올바른 email 값이 들어왔을 때") {
            val dto = AuthFixture.createSignUpRequest(email = "moseoh@danggeun.com")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 email 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createSignUpRequest(email = ""),
                AuthFixture.createSignUpRequest(email = "moseohdanggeun.com"),
                AuthFixture.createSignUpRequest(email = "moseoh@danggeuncom"),
                AuthFixture.createSignUpRequest(email = "@danggeun.com"),
                AuthFixture.createSignUpRequest(email = "moseoh@.com"),
                AuthFixture.createSignUpRequest(email = "moseoh@danggeun.com."),
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
            val dto = AuthFixture.createSignUpRequest(password = "password")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 password 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createSignUpRequest(password = ""),
                AuthFixture.createSignUpRequest(password = "zsdw"),
                AuthFixture.createSignUpRequest(password = "1234567"),
                AuthFixture.createSignUpRequest(password = "123456789012345678901"),
                AuthFixture.createSignUpRequest(password = "ejioij2o3ijlK@f90j()9jsdflakj"),
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

        When("올바른 nickname 값이 들어왔을 때") {
            val dto = AuthFixture.createSignUpRequest(nickname = "nickname")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 nickname 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createSignUpRequest(nickname = ""),
            )
            val results = dtos.map { validator.validate(it) }

            Then("오류가 발생한다.") {
                results.forEach {
                    it.size shouldBe 1
                    val violation = it.first()

                    violation.propertyPath.toString() shouldBe "nickname"
                    violation.message shouldBe "공백일 수 없습니다"
                }
            }
        }

        When("올바른 phone 값이 들어왔을 때") {
            val dto = AuthFixture.createSignUpRequest(phone = "01012345678")
            val result = validator.validate(dto)

            Then("오류가 발생하지 않는다.") {
                result.isEmpty() shouldBe true
            }
        }

        When("올바르지 않은 phone 값이 들어왔을 때") {
            val dtos = listOf(
                AuthFixture.createSignUpRequest(phone = ""),
                AuthFixture.createSignUpRequest(phone = "0101234567"),
                AuthFixture.createSignUpRequest(phone = "0101234567890"),
            )
            val results = dtos.map { validator.validate(it) }

            Then("오류가 발생한다.") {
                results.forEach {
                    it.size shouldBe 1
                    val violation = it.first()

                    violation.propertyPath.toString() shouldBe "phone"
                    violation.message shouldBe "크기가 11에서 11 사이여야 합니다"
                }
            }
        }
    }
})
