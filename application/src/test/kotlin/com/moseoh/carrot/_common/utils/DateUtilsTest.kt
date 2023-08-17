package com.moseoh.carrot._common.utils

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class DateUtilsTest : BehaviorSpec({
    Given("30초 전 시간을") {
        val createdAt = LocalDateTime.now().minusSeconds(30)

        When("요청을 한다면") {
            val result = DateUtils.timeAgo(createdAt)

            Then("'방금 전' 이라고 표기") {
                result shouldBe "방금 전"
            }
        }
    }

    Given("30분 전 시간을") {
        val createdAt = LocalDateTime.now().minusMinutes(30)

        When("요청을 한다면") {
            val result = DateUtils.timeAgo(createdAt)

            Then("'30분 전' 이라고 표기") {
                result shouldBe "30분 전"
            }
        }
    }

    Given("21시간 전 시간을") {
        val createdAt = LocalDateTime.now().minusHours(21)

        When("요청을 한다면") {
            val result = DateUtils.timeAgo(createdAt)

            Then("'21시간 전' 이라고 표기") {
                result shouldBe "21시간 전"
            }
        }
    }

    Given("30시간 전 시간을") {
        val createdAt = LocalDateTime.now().minusHours(30)

        When("요청을 한다면") {
            val result = DateUtils.timeAgo(createdAt)

            Then("'1일 전' 이라고 표기") {
                result shouldBe "1일 전"
            }
        }
    }
})
