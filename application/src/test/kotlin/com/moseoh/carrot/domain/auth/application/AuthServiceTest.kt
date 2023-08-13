package com.moseoh.carrot.domain.auth.application

import com.moseoh.carrot.AuthFixture
import com.moseoh.carrot.UserFixture
import com.moseoh.carrot.domain.auth.application.dto.MeResponse
import com.moseoh.carrot.domain.auth.application.mapper.UserMapper
import com.moseoh.carrot.domain.auth.exception.EmailExistsException
import com.moseoh.carrot.domain.auth.exception.PasswordNotMatchException
import com.moseoh.carrot.domain.user.entity.repository.UserRepository
import com.moseoh.carrot.domain.user.entity.repository.getEntityByEmail
import com.moseoh.carrot.domain.user.entity.repository.getEntityById
import com.moseoh.carrot.helper.ServiceTest
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class AuthServiceTest @Autowired constructor(
    @MockkBean private val passwordEncoder: PasswordEncoder,
    @MockkBean private val userRepository: UserRepository,
    @MockkBean private val userMapper: UserMapper,
    @MockkBean private val jwtProvider: JwtProvider,
    @MockK private val authService: AuthService,
) : ServiceTest({

    Given("signIn") {
        val signInRequest = AuthFixture.createSignInRequest()
        val user = UserFixture.createUser()
        val tokenResponse = AuthFixture.createTokenResponse()

        When("요청을 한다면") {
            every { userRepository.getEntityByEmail(signInRequest.email) } returns user
            every { passwordEncoder.matches(signInRequest.password, user.password) } returns true
            every { jwtProvider.create(user) } returns tokenResponse

            val result = authService.signIn(signInRequest)

            Then("TokenResponse 를 반환한다.") {
                result shouldBe tokenResponse

                verify(exactly = 1) { userRepository.getEntityByEmail(signInRequest.email) }
                verify(exactly = 1) { passwordEncoder.matches(signInRequest.password, user.password) }
                verify(exactly = 1) { jwtProvider.create(user) }
            }
        }

        When("비밀번호가 다르르다면") {
            every { userRepository.getEntityByEmail(signInRequest.email) } returns user
            every { passwordEncoder.matches(signInRequest.password, user.password) } returns false

            val result = shouldThrow<PasswordNotMatchException> {
                authService.signIn(signInRequest)
            }

            Then("PasswordNotMatchException 이 발생한다.") {
                result shouldBe PasswordNotMatchException()

                verify(exactly = 1) { userRepository.getEntityByEmail(signInRequest.email) }
                verify(exactly = 1) { passwordEncoder.matches(signInRequest.password, user.password) }
            }
        }
    }

    Given("signOut") {
        val auth = AuthFixture.createAuthentication()
        val accessToken = auth.credentials as String

        When("요청을 한다면") {
            every { jwtProvider.deleteByAccessToken(accessToken) } just Runs

            val result = authService.signOut(auth)

            Then("수행한다.") {
                result shouldBe Unit

                verify(exactly = 1) { jwtProvider.deleteByAccessToken(accessToken) }
            }
        }
    }

    Given("signUp") {
        val signUpRequest = AuthFixture.createSignUpRequest()
        val user = UserFixture.createUser()

        When("요청을 한다면") {
            every { userRepository.existsByEmail(signUpRequest.email) } returns false
            every { userMapper.dtoToEntity(signUpRequest) } returns user
            every { userRepository.save(user) } returns user

            val result = authService.signUp(signUpRequest)

            Then("수행한다.") {
                result shouldBe Unit

                verify(exactly = 1) { userRepository.existsByEmail(signUpRequest.email) }
                verify(exactly = 1) { userMapper.dtoToEntity(signUpRequest) }
                verify(exactly = 1) { userRepository.save(user) }
            }
        }

        When("이미 존재하는 Email 이라면") {
            every { userRepository.existsByEmail(signUpRequest.email) } returns true

            val result = shouldThrow<EmailExistsException> {
                authService.signUp(signUpRequest)
            }

            Then("EmailExistsException 이 발생한다.") {
                result shouldBe EmailExistsException()

                verify(exactly = 1) { userRepository.existsByEmail(signUpRequest.email) }
            }
        }
    }

    Given("refresh") {
        val tokenRequest = AuthFixture.createTokenRequest()
        val tokenResponse = AuthFixture.createTokenResponse()

        When("요청을 한다면") {
            every { jwtProvider.refresh(tokenRequest) } returns tokenResponse

            val result = authService.refresh(tokenRequest)

            Then("TokenResponse 를 반환한다.") {
                result shouldBe tokenResponse
            }
        }
    }

    Given("me") {
        val auth = AuthFixture.createAuthentication()
        val id = auth.principal as Long
        val user = UserFixture.createUser()
        val meResponse = user.let(::MeResponse)

        When("요청을 한다면") {
            every { userRepository.getEntityById(id) } returns user

            val result = authService.me(auth)

            Then("MeResponse 를 반환한다.") {
                result shouldBe meResponse
            }
        }
    }
})