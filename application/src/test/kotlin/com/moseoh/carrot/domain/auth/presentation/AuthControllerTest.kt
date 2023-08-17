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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*

@WebMvcTestWithConfig(AuthController::class)
class AuthControllerTest @Autowired constructor(
    @MockkBean val authService: AuthService,
) : RestControllerTest() {

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
                "accessToken" fieldType STRING means "Access Token",
                "refreshToken" fieldType STRING means "Refresh Token"
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
                "phone" fieldType STRING means "핸드폰 번호" isOptional false,
                "profileUrl" fieldType STRING means "프로필 URL" isOptional true,
                "regionIds" fieldType ARRAY means "지역 IDs" isOptional true,
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
                "accessToken" fieldType STRING means "Access Token" isOptional false,
                "refreshToken" fieldType STRING means "Refresh Token" isOptional false,
            ),
            responseBody(
                "accessToken" fieldType STRING means "Access Token",
                "refreshToken" fieldType STRING means "Refresh Token"
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
                "id" fieldType NUMBER means "사용자 ID",
                "role" fieldType STRING means "역할",
                "email" fieldType STRING means "이메일",
                "nickname" fieldType STRING means "닉네임",
                "phone" fieldType STRING means "핸드폰 번호",
                "profileUrl" fieldType STRING means "프로필 URL",
                "temperature" fieldType NUMBER means "온도",
                "regions" fieldType ARRAY means "지역",
                "createdAt" fieldType DATETIME means "생성 시간",
                "modifiedAt" fieldType DATETIME means "수정 시간"
            )
        )
    }
}