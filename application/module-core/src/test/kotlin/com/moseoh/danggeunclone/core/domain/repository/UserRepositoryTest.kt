package com.moseoh.danggeunclone.core.domain.repository

import com.moseoh.danggeunclone.core.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest @Autowired constructor(
    private val userRepository: UserRepository,
) : BehaviorSpec({

    Given("existsByEmail") {
        val user = UserFixture.createEntity()
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
        val user = UserFixture.createEntity()
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
})