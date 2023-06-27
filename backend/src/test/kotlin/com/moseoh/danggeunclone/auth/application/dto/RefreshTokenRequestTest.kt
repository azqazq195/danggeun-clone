package com.moseoh.danggeunclone.auth.application.dto

import com.moseoh.danggeunclone.support.createRefreshTokenRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

internal class RefreshTokenRequestTest : BehaviorSpec({
    val validator: Validator = LocalValidatorFactoryBean().apply {
        afterPropertiesSet()
    }

    Given("올바른 객체를") {
        val refreshTokenRequest = createRefreshTokenRequest()
        val errors = BeanPropertyBindingResult(refreshTokenRequest, "refreshTokenRequest")

        When("유효성 검사한다면") {
            validator.validate(refreshTokenRequest, errors)

            Then("오류가 없다.") {
                errors.hasErrors() shouldBe false
            }
        }
    }

    Given("accessToken 이 올바르지 않은 객체를") {
        val refreshTokenRequest = createRefreshTokenRequest(accessToken = "")
        val errors = BeanPropertyBindingResult(refreshTokenRequest, "refreshTokenRequest")

        When("유효성 검사한다면") {
            validator.validate(refreshTokenRequest, errors)

            Then("오류가 있다.") {
                errors.hasErrors() shouldBe true
                errors.fieldError!!.field shouldBe "accessToken"
                errors.fieldError!!.defaultMessage shouldBe "공백일 수 없습니다"
            }
        }
    }

    Given("refreshToken 이 올바르지 않은 객체를") {
        val refreshTokenRequest = createRefreshTokenRequest(refreshToken = "")
        val errors = BeanPropertyBindingResult(refreshTokenRequest, "refreshTokenRequest")

        When("유효성 검사한다면") {
            validator.validate(refreshTokenRequest, errors)

            Then("오류가 있다.") {
                errors.hasErrors() shouldBe true
                errors.fieldError!!.field shouldBe "refreshToken"
                errors.fieldError!!.defaultMessage shouldBe "공백일 수 없습니다"
            }
        }
    }
})