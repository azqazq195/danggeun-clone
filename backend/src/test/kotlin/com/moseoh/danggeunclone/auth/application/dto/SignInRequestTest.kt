package com.moseoh.danggeunclone.auth.application.dto

import com.moseoh.danggeunclone.support.createSignInRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

internal class SignInRequestTest : BehaviorSpec({
    val validator: Validator = LocalValidatorFactoryBean().apply {
        afterPropertiesSet()
    }

    Given("올바른 객체를") {
        val signInRequests = arrayOf(
            createSignInRequest(email = "example@domain.com"),
            createSignInRequest(email = "example2@domain3.com"),
        )

        When("유효성 검사한다면") {
            Then("오류가 없다.") {
                signInRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signInRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe false
                }
            }
        }
    }

    Given("email 형식이 올바르지 않은 객체를") {
        val signInRequests = arrayOf(
            createSignInRequest(email = ""),
            createSignInRequest(email = "azqazq195@sss."),
            createSignInRequest(email = "azqazq195@sss//com"),
            createSignInRequest(email = "azqazq195@sss//./com"),
            createSignInRequest(email = "azqazq195@ssscom"),
            createSignInRequest(email = "example34@domain.4com")
        )

        When("유효성 검사한다면") {
            Then("오류가 있다.") {
                signInRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signInRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe true
                }
            }
        }
    }

    Given("password 형식이 올바르지 않은 객체를") {
        val signInRequests = arrayOf(
            createSignInRequest(password = ""),
        )

        When("유효성 검사한다면") {
            Then("오류가 있다.") {
                signInRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signInRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe true
                }
            }
        }
    }
})