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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(AuthController::class)
class AuthControllerTest : RestControllerTest() {

    @MockkBean
    private lateinit var authService: AuthService

    @Test
    fun signIn() {
        val request = createSignInRequest()
        val response = createTokenResponse()
        every { authService.signIn(any()) } returns response

        mockMvc.perform(
            post("/auth/sign-in")
                .jsonContent(request)
        ).andExpect {
            status().isOk
            success(response)
        }.andDo {
            document(
                identifier = "auth/sign-in",
                requestFields = listOf(
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("password").description("비밀번호"),
                ),
                responseFields = listOf(
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

        mockMvc.perform(
            get("/auth/sign-out")
                .bearer()
        ).andExpect {
            status().isOk
            success()
        }.andDo {
            document(
                identifier = "auth/sign-out",
            )
        }
    }

    @Test
    fun signUp() {
        val request = createSignInRequest()
        every { authService.signUp(any()) } just runs

        mockMvc.perform(
            post("/auth/sign-up")
                .jsonContent(request)
        ).andExpect {
            status().isCreated
            success()
        }.andDo {
            document(
                identifier = "auth/sign-up",
                requestFields = listOf(
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("password").description("비밀번호"),
                    fieldWithPath("name").description("이름"),
                    fieldWithPath("age").description("나이").optional(),
                ),
            )
        }
    }

    @Test
    fun refresh() {
        val request = createRefreshTokenRequest()
        val response = createTokenResponse()
        every { authService.refresh(request) } returns response

        mockMvc.perform(
            post("/auth/refresh")
                .jsonContent(request)
        ).andExpect {
            status().isOk
            success(response)
        }.andDo {
            document(
                identifier = "auth/refresh",
                requestFields = listOf(
                    fieldWithPath("accessToken").description("Access Token"),
                    fieldWithPath("refreshToken").description("Refresh Token"),
                ),
                responseFields = listOf(
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

        mockMvc.perform(
            get("/auth/me")
                .bearer()
        ).andExpect {
            status().isOk
            success(response)
        }.andDo {
            document(
                identifier = "auth/me",
                responseFields = listOf(
                    fieldWithPath("id").description("ID"),
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("role").description("역할"),
                    fieldWithPath("createdAt").description("생성 시간"),
                    fieldWithPath("modifiedAt").description("수정 시간"),
                ),
            )
        }
    }
}