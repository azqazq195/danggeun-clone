package com.moseoh.danggeunclone.post.ui

import com.moseoh.danggeunclone.post.application.PostService
import com.moseoh.danggeunclone.support.createCreatePostRequest
import com.moseoh.danggeunclone.support.createPostResponse
import com.moseoh.danggeunclone.support.createUpdatePostRequest
import com.moseoh.danggeunclone.support.ui.RestControllerTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(PostController::class)
class PostControllerTest : RestControllerTest() {

    @MockkBean
    private lateinit var postService: PostService

    @Test
    fun get() {
        val response = createPostResponse()
        every { postService.get(any()) } returns response

        mockMvc.perform(
            get("/post/{id}", 1)
                .bearer()
        ).andExpect {
            status().isOk
            success(response)
        }.andDo {
            document(
                identifier = "post/{id}",
                pathParameters = listOf(
                    parameterWithName("id").description("조회할 ID")
                ),
                responseFields = listOf(
                    fieldWithPath("id").description("ID"),
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("category").description("분류"),
                    fieldWithPath("status").description("상태"),
                    fieldWithPath("content").description("내용"),
                    fieldWithPath("address.state").description("주소 - 주"),
                    fieldWithPath("address.city").description("주소 - 도시"),
                    fieldWithPath("address.town").description("주소 - 동네"),
                    fieldWithPath("location.longitude").description("좌표 - 경도"),
                    fieldWithPath("location.latitude").description("좌표 - 위도"),
                    fieldWithPath("createdAt").description("생성 시간"),
                    fieldWithPath("modifiedAt").description("수정 시간"),
                )
            )
        }
    }

    @Test
    fun create() {
        val request = createCreatePostRequest()
        val response = createPostResponse()

        mockMvc.perform(
            post("/post")
                .bearer()
                .jsonContent(request)
        ).andExpect {
            status().isCreated
            success(response)
        }.andDo {
            document(
                identifier = "post",
                requestFields = listOf(
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("category").description("분류"),
                    fieldWithPath("content").description("내용")
                ),
                responseFields = listOf(
                    fieldWithPath("id").description("ID"),
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("category").description("분류"),
                    fieldWithPath("status").description("상태"),
                    fieldWithPath("content").description("내용"),
                    fieldWithPath("address.state").description("주소 - 주"),
                    fieldWithPath("address.city").description("주소 - 도시"),
                    fieldWithPath("address.town").description("주소 - 동네"),
                    fieldWithPath("location.longitude").description("좌표 - 경도"),
                    fieldWithPath("location.latitude").description("좌표 - 위도"),
                    fieldWithPath("createdAt").description("생성 시간"),
                    fieldWithPath("modifiedAt").description("수정 시간"),
                )
            )
        }
    }

    @Test
    fun update() {
        val request = createUpdatePostRequest()
        val response = createPostResponse()

        mockMvc.perform(
            put("/post/{id}", 1)
                .bearer()
                .jsonContent(request)
        ).andExpect {
            status().isOk
            success(response)
        }.andDo {
            document(
                identifier = "post/{id}",
                pathParameters = listOf(
                    parameterWithName("id").description("수정할 ID")
                ),
                requestFields = listOf(
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("category").description("분류"),
                    fieldWithPath("content").description("내용"),
                    fieldWithPath("status").description("상태"),
                ),
                responseFields = listOf(
                    fieldWithPath("id").description("ID"),
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("category").description("분류"),
                    fieldWithPath("status").description("상태"),
                    fieldWithPath("content").description("내용"),
                    fieldWithPath("address.state").description("주소 - 주"),
                    fieldWithPath("address.city").description("주소 - 도시"),
                    fieldWithPath("address.town").description("주소 - 동네"),
                    fieldWithPath("location.longitude").description("좌표 - 경도"),
                    fieldWithPath("location.latitude").description("좌표 - 위도"),
                    fieldWithPath("createdAt").description("생성 시간"),
                    fieldWithPath("modifiedAt").description("수정 시간"),
                )
            )
        }
    }

    @Test
    fun delete() {
        mockMvc.perform(
            delete("/post/{id}", 1)
                .bearer()
        ).andExpect {
            status().isOk
            success()
        }.andDo {
            document(
                identifier = "post/{id}",
                pathParameters = listOf(
                    parameterWithName("id").description("삭제할 ID")
                ),
            )
        }
    }

}