package com.moseoh.carrot.domain.auth.application.mapper

import com.moseoh.carrot.AuthFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder

class UserMapperTest : BehaviorSpec({
    val passwordEncoder = mockk<PasswordEncoder>()
    val userMapper = UserMapper(passwordEncoder)

    Given("dtoToEntity") {
        val signUpRequest = AuthFixture.createSignUpRequest()
        val encodedPassword = "encoded password"
        every { passwordEncoder.encode(signUpRequest.password) } returns encodedPassword

        When("요청을 한다면") {
            val result = userMapper.dtoToEntity(signUpRequest)

            Then("encoded 된 password를 가진 User 를 반환한다.") {
                result.password shouldBe encodedPassword

                verify(exactly = 1) { passwordEncoder.encode(signUpRequest.password) }
            }
        }
    }
})