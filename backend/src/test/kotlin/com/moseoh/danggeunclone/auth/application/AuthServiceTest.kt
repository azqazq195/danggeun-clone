package com.moseoh.danggeunclone.auth.application

import com.moseoh.danggeunclone._common.exception.ErrorCode
import com.moseoh.danggeunclone.support.*
import com.moseoh.danggeunclone.user.application.dto.UserResponse
import com.moseoh.danggeunclone.user.domain.repository.UserRepository
import com.moseoh.danggeunclone.user.domain.repository.getByEmail
import com.moseoh.danggeunclone.user.exception.NotFoundUserException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder

internal class AuthServiceTest : BehaviorSpec({
    val tokenProvider = mockk<TokenProvider>()
    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val authService = AuthService(tokenProvider, userRepository, passwordEncoder)

    afterTest {
        clearAllMocks()
    }

    Given("signIn") {
        val signInRequest = createSignInRequest()
        val user = createUser()
        val tokenResponse = createTokenResponse()

        When("요청을 한다면") {
            every { userRepository.getByEmail(signInRequest.email) } returns user
            every { passwordEncoder.matches(signInRequest.password, user.password) } returns true
            every { tokenProvider.create(user) } returns tokenResponse

            val result = authService.signIn(signInRequest)

            Then("토큰 정보를 반환한다.") {
                result shouldBe tokenResponse

                verify(exactly = 1) { userRepository.getByEmail(signInRequest.email) }
                verify(exactly = 1) { passwordEncoder.matches(signInRequest.password, user.password) }
                verify(exactly = 1) { tokenProvider.create(user) }
            }
        }

        When("비밀번호가 다르다면") {
            every { userRepository.getByEmail(signInRequest.email) } returns user
            every { passwordEncoder.matches(signInRequest.password, user.password) } returns false

            val exception = shouldThrow<IllegalStateException> {
                authService.signIn(signInRequest)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe "비밀번호가 올바르지 않습니다."

                verify(exactly = 1) { userRepository.getByEmail(signInRequest.email) }
                verify(exactly = 1) { passwordEncoder.matches(signInRequest.password, user.password) }
                verify(exactly = 0) { tokenProvider.create(user) }
            }
        }

        When("유저가 존재하지 않는다면") {
            every { userRepository.findByEmail(signInRequest.email) } returns null

            val exception = shouldThrow<NotFoundUserException> {
                authService.signIn(signInRequest)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe ErrorCode.NOT_FOUND_USER.message

                verify(exactly = 1) { userRepository.getByEmail(signInRequest.email) }
                verify(exactly = 0) { passwordEncoder.matches(signInRequest.password, user.password) }
                verify(exactly = 0) { tokenProvider.create(user) }
            }
        }
    }

    Given("signOut") {
        val auth = mockk<Authentication>()
        val accessToken = "accessToken"

        When("로그아웃 요청을 한다면") {
            every { auth.credentials } returns accessToken
            every { tokenProvider.deleteByAccessToken(accessToken) } just runs

            authService.signOut(auth)

            Then("토큰을 삭제한다") {
                verify(exactly = 1) { tokenProvider.deleteByAccessToken(accessToken) }
            }
        }
    }

    Given("signUp") {
        val signUpRequest = createSignUpRequest()
        val user = createUser()

        When("요청을 한다면") {
            every { userRepository.existsByEmail(signUpRequest.email) } returns false
            every { userRepository.save(any()) } returns user
            every { passwordEncoder.encode(signUpRequest.password) } returns "encoded"

            authService.signUp(signUpRequest)

            Then("회원을 저장한다.") {
                verify(exactly = 1) { userRepository.existsByEmail(signUpRequest.email) }
                verify(exactly = 1) { userRepository.save(any()) }
                verify(exactly = 1) { passwordEncoder.encode(signUpRequest.password) }
            }
        }

        When("이미 존재하는 이메일이라면") {
            every { userRepository.existsByEmail(signUpRequest.email) } returns true

            val exception = shouldThrow<IllegalStateException> {
                authService.signUp(signUpRequest)
            }

            Then("에러가 발생한다.") {
                exception.message shouldBe "이미 존재하는 이메일입니다."

                verify(exactly = 1) { userRepository.existsByEmail(signUpRequest.email) }
                verify(exactly = 0) { userRepository.save(any()) }
                verify(exactly = 0) { passwordEncoder.encode(signUpRequest.password) }
            }
        }
    }

    Given("refresh") {
        val refreshTokenRequest = createRefreshTokenRequest()
        val tokenResponse = createTokenResponse()

        When("요청을 한다면") {
            every { tokenProvider.refresh(refreshTokenRequest) } returns tokenResponse

            val result = authService.refresh(refreshTokenRequest)

            Then("토큰을 반환한다.") {
                result shouldBe tokenResponse

                verify(exactly = 1) { tokenProvider.refresh(refreshTokenRequest) }
            }
        }
    }

    Given("me") {
        val auth = mockk<Authentication>()
        val user = createUser()
        val userResponse = user.let(::UserResponse)

        When("요청을 한다면") {
            every { auth.principal } returns user.email
            every { userRepository.getByEmail(user.email) } returns user

            val result = authService.me(auth)

            Then("내 정보를 반환한다.") {
                result shouldBe userResponse

                verify(exactly = 1) { userRepository.getByEmail(user.email) }
            }
        }
    }
})