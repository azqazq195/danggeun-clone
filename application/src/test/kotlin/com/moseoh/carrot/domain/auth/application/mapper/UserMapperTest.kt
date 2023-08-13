package com.moseoh.carrot.domain.auth.application.mapper

import com.moseoh.carrot.AuthFixture
import com.moseoh.carrot.UserFixture
import com.moseoh.carrot.domain.user.entity.repository.RegionRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder

class UserMapperTest : BehaviorSpec({
    val passwordEncoder = mockk<PasswordEncoder>()
    val regionRepository = mockk<RegionRepository>()

    val userMapper = UserMapper(passwordEncoder, regionRepository)

    Given("dtoToEntity") {
        val signUpRequest = AuthFixture.createSignUpRequest()
        val encodedPassword = "encoded password"
        val regions = listOf(UserFixture.createRegion(), UserFixture.createRegion("도곡동"))

        every { passwordEncoder.encode(signUpRequest.password) } returns encodedPassword
        every { regionRepository.findAllById(signUpRequest.regionIds) } returns regions

        When("요청을 한다면") {
            val result = userMapper.dtoToEntity(signUpRequest)

            Then("User 를 반환한다.") {
                result.password shouldBe encodedPassword
                result.regions shouldBe regions

                verify(exactly = 1) { passwordEncoder.encode(signUpRequest.password) }
                verify(exactly = 1) { regionRepository.findAllById(signUpRequest.regionIds) }
            }
        }
    }
})