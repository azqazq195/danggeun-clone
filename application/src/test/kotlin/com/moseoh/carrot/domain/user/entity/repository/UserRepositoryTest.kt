package com.moseoh.carrot.domain.user.entity.repository

import com.moseoh.carrot.UserFixture
import com.moseoh.carrot.domain.user.exception.UserNotFoundException
import com.moseoh.carrot.helper.JpaRepositoryTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository,
) : JpaRepositoryTest({

    Given("existsByEmail") {
        val user = UserFixture.createUser()
        userRepository.save(user)

        When("Email이 이미 존재한다면") {
            val result = userRepository.existsByEmail(user.email)

            Then("true를 반환한다.") {
                result shouldBe true
            }
        }

        When("Email이 존재하지 않는다면") {
            val result = userRepository.existsByEmail("not exists email")

            Then("false를 반환한다.") {
                result shouldBe false
            }
        }
    }

    Given("findByEmail") {
        val user = UserFixture.createUser()
        userRepository.save(user)

        When("존재하는 유저의 Email로 찾는다면") {
            val result = userRepository.findByEmail(user.email)

            Then("User가 담긴 Optional을 반환한다.") {
                result.get().email shouldBe user.email
            }
        }

        When("존재하지 않는 유저의 Email로 찾는다면") {
            val result = userRepository.findByEmail("not exists email")

            Then("빈 Optional을 반환한다.") {
                result shouldBe Optional.empty()
            }
        }
    }

    Given("getEntityByEmail") {
        val user = UserFixture.createUser()
        userRepository.save(user)

        When("존재하는 User 의 email 로 찾는다면") {
            val result = userRepository.getEntityByEmail(user.email)

            Then("User 를 반환한다.") {
                result.email shouldBe user.email
            }
        }

        When("존재하지 않는 User 의 email 로 찾는다면") {
            val result = shouldThrow<UserNotFoundException> {
                userRepository.getEntityByEmail("not exists email")
            }

            Then("UserNotFoundException 이 발생한다.") {
                result shouldBe UserNotFoundException()
            }
        }
    }

    Given("getEntityById") {
        val user = UserFixture.createUser()
        userRepository.save(user)

        When("존재하는 User 의 id 로 찾는다면") {
            val result = userRepository.getEntityById(user.id)

            Then("User 를 반환한다.") {
                result.id shouldBe user.id
            }
        }

        When("존재하지 않는 User 의 id 로 찾는다면") {
            val result = shouldThrow<UserNotFoundException> {
                userRepository.getEntityById(0L)
            }

            Then("UserNotFoundException 이 발생한다.") {
                result shouldBe UserNotFoundException()
            }
        }
    }
})