package com.moseoh.danggeunclone.auth.ui

import com.moseoh.danggeunclone.auth.application.AuthService
import com.moseoh.danggeunclone.support.*
import com.moseoh.danggeunclone.support.config.MockUser
import com.moseoh.danggeunclone.support.ui.RestControllerTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(AuthController::class)
class AuthControllerTest : RestControllerTest() {

    @MockkBean
    private lateinit var authService: AuthService

    @Test
    fun signIn() {
        val response = createTokenResponse()
        every { authService.signIn(any()) } returns response

        mockMvc.post("/auth/sign-in") {
            jsonContent(createSignInRequest())
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            document(
                "auth/sign-in",
                null,
                listOf(
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("password").description("비밀번호"),
                ),
                listOf(
                    fieldWithPath("accessToken").description("Access Token"),
                    fieldWithPath("refreshToken").description("Refresh Token"),
                ),
            )
        }
    }

    @Test
    @MockUser
    fun signOut() {
        every { authService.signOut(any()) } just runs

        mockMvc.get("/auth/sign-out") {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
            content { success() }
        }.andDo {
            document(
                "auth/sign-out",
            )
        }
    }

    @Test
    fun signUp() {
        every { authService.signUp(any()) } just runs

        mockMvc.post("/auth/sign-up") {
            jsonContent(createSignUpRequest())
        }.andExpect {
            status { isCreated() }
            content { success() }
        }.andDo {
            document(
                "auth/sign-up",
                null,
                listOf(
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("password").description("비밀번호"),
                    fieldWithPath("name").description("이름"),
                    fieldWithPath("age").description("나이").optional(),
                ),
                null
            )
        }
    }

    @Test
    fun refresh() {
        // given
        val request = createRefreshTokenRequest()
        val response = createTokenResponse()
        every { authService.refresh(request) } returns response

        mockMvc.post("/auth/refresh") {
            jsonContent(request)
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            document(
                "auth/refresh",
                null,
                listOf(
                    fieldWithPath("accessToken").description("Access Token"),
                    fieldWithPath("refreshToken").description("Refresh Token"),
                ),
                listOf(
                    fieldWithPath("accessToken").description("Access Token"),
                    fieldWithPath("refreshToken").description("Refresh Token"),
                ),
            )
        }
    }

    @Test
    @MockUser
    fun me() {
        val response = createUserResponse()
        every { authService.me(any()) } returns response

        mockMvc.get("/auth/me") {
            bearer("valid_token")
        }.andExpect {
            status { isOk() }
            content { success(response) }
        }.andDo {
            document(
                "auth/me",
                null,
                null,
                listOf(
                    fieldWithPath("id").description("ID"),
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("role").description("역할"),
                    fieldWithPath("createdAt").description("생성 시간"),
                    fieldWithPath("modifiedAt").description("수정 시간"),
                ),
            )
        }.andReturn().response
    }

}