package com.moseoh.danggeunclone.auth.application.dto

import com.moseoh.danggeunclone.support.createSignUpRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

internal class SingUpRequestTest : BehaviorSpec({
    val validator: Validator = LocalValidatorFactoryBean().apply {
        afterPropertiesSet()
    }

    Given("올바른 객체를") {
        val signUpRequests = arrayOf(
            createSignUpRequest(email = "example@domain.com"),
            createSignUpRequest(email = "example2@domain3.com"),
        )

        When("유효성 검사한다면") {
            Then("오류가 없다.") {
                signUpRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signUpRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe false
                }
            }
        }
    }

    Given("email 형식이 올바르지 않은 객체를") {
        val signUpRequests = arrayOf(
            createSignUpRequest(email = ""),
            createSignUpRequest(email = "azqazq195@sss."),
            createSignUpRequest(email = "azqazq195@sss//com"),
            createSignUpRequest(email = "azqazq195@sss//./com"),
            createSignUpRequest(email = "azqazq195@ssscom"),
            createSignUpRequest(email = "example34@domain.4com")
        )

        When("유효성 검사한다면") {
            Then("오류가 있다.") {
                signUpRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signUpRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe true
                }
            }
        }
    }

    Given("password 형식이 올바르지 않은 객체를") {
        val signUpRequests = arrayOf(
            createSignUpRequest(password = ""),
        )

        When("유효성 검사한다면") {
            Then("오류가 있다.") {
                signUpRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signUpRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe true
                }
            }
        }
    }

    Given("name 형식이 올바르지 않은 객체를") {
        val signUpRequests = arrayOf(
            createSignUpRequest(name = ""),
        )

        When("유효성 검사한다면") {
            Then("오류가 있다.") {
                signUpRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signUpRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe true
                }
            }
        }
    }

    Given("age 형식이 올바르지 않은 객체를") {
        val signUpRequests = arrayOf(
            createSignUpRequest(age = 0),
            createSignUpRequest(age = -1),
            createSignUpRequest(age = -100),
        )

        When("유효성 검사한다면") {
            Then("오류가 있다.") {
                signUpRequests.forEach {
                    val errors = BeanPropertyBindingResult(it, "signUpRequest")
                    validator.validate(it, errors)

                    errors.hasErrors() shouldBe true
                }
            }
        }
    }
})