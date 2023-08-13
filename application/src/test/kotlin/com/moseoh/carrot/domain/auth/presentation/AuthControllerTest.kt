package com.moseoh.carrot.domain.auth.presentation

import com.moseoh.carrot.AuthFixture
import com.moseoh.carrot.domain.auth.application.AuthService
import com.moseoh.carrot.helper.RestControllerTest
import com.moseoh.carrot.helper.constraints.WebMvcTestWithConfig
import com.moseoh.carrot.helper.utils.*
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*

@WebMvcTestWithConfig(AuthController::class)
class AuthControllerTest : RestControllerTest() {

    @MockkBean
    private lateinit var authService: AuthService

    @Test
    fun signIn() {
        // given
        val url = "/auth/sign-in"
        val signInRequest = AuthFixture.createSignInRequest()
        val tokenResponse = AuthFixture.createTokenResponse()
        val request = post(url).body(signInRequest)

        // when
        every { authService.signIn(signInRequest) } returns tokenResponse
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "auth/sign-in",
            requestBody(
                "email" fieldType STRING means "이메일" isOptional false,
                "password" fieldType STRING means "비밀번호" isOptional false,
            ),
            responseBody(
                "content.accessToken" fieldType STRING means "accessToken",
                "content.refreshToken" fieldType STRING means "refreshToken"
            )
        )
    }

    @Test
    fun signOut() {
        // given
        val url = "/auth/sign-out"
        val request = delete(url).isAuthenticated()

        // when
        every { authService.signOut(any()) } just Runs
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "auth/sign-out",
        )
    }

    @Test
    fun signUp() {
        // given
        val url = "/auth/sign-up"
        val signUpRequest = AuthFixture.createSignUpRequest()
        val request = post(url).body(signUpRequest)

        // when
        every { authService.signUp(signUpRequest) } just Runs
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.CREATED
        result.makeDocument(
            "auth/sign-up",
            requestBody(
                "email" fieldType STRING means "이메일" isOptional false,
                "password" fieldType STRING means "비밀번호" isOptional false,
                "nickname" fieldType STRING means "닉네임" isOptional false,
            ),
        )
    }

    @Test
    fun refresh() {
        // given
        val url = "/auth/refresh"
        val tokenRequest = AuthFixture.createTokenRequest()
        val tokenResponse = AuthFixture.createTokenResponse()
        val request = post(url).body(tokenRequest)

        // when
        every { authService.refresh(tokenRequest) } returns tokenResponse
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "auth/refresh",
            requestBody(
                "accessToken" fieldType STRING means "accessToken" isOptional false,
                "refreshToken" fieldType STRING means "refreshToken" isOptional false,
            ),
            responseBody(
                "content.accessToken" fieldType STRING means "accessToken",
                "content.refreshToken" fieldType STRING means "refreshToken"
            )
        )
    }

    @Test
    fun me() {
        // given
        val url = "/auth/me"
        val meResponse = AuthFixture.createMeResponse()
        val request = get(url).isAuthenticated()

        // when
        every { authService.me(any()) } returns meResponse
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "auth/me",
            responseBody(
                "content.id" fieldType NUMBER means "사용자 ID",
                "content.email" fieldType STRING means "이메일",
                "content.nickname" fieldType STRING means "닉네임",
                "content.role" fieldType STRING means "역할",
                "content.createdAt" fieldType DATETIME means "생성 시간",
                "content.modifiedAt" fieldType DATETIME means "수정 시간"
            )
        )
    }
}