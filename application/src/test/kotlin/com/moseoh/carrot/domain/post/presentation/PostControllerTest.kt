package com.moseoh.carrot.domain.post.presentation

import com.moseoh.carrot.PostFixture
import com.moseoh.carrot.domain.post.application.PostQueryService
import com.moseoh.carrot.domain.post.application.PostService
import com.moseoh.carrot.helper.RestControllerTest
import com.moseoh.carrot.helper.constraints.WebMvcTestWithConfig
import com.moseoh.carrot.helper.utils.*
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*


@WebMvcTestWithConfig(PostController::class)
class PostControllerTest @Autowired constructor(
    @MockkBean val postService: PostService,
    @MockkBean val postQueryService: PostQueryService,
) : RestControllerTest() {

    @Test
    fun create() {
        // given
        val url = "/post"
        val postRequest = PostFixture.createPostRequest()
        val request = post(url).body(postRequest)

        // when
        every { postService.create(postRequest) } just Runs
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.CREATED
        result.makeDocument(
            "post/create",
            requestBody(
                "photoUrls" fieldType ARRAY means "첨부 사진 url" isOptional true,
                "title" fieldType STRING means "제목" isOptional false,
                "content" fieldType STRING means "내용" isOptional false,
                "categoryId" fieldType NUMBER means "카테고리 ID" isOptional false,
                "regionId" fieldType NUMBER means "지역 ID" isOptional false,
                "price" fieldType NUMBER means "가격" isOptional false,
            ),
        )
    }

    @Test
    fun update() {
        // given
        val url = "/post/{id}"
        val id = 1L
        val postRequest = PostFixture.createPostRequest()
        val request = put(url, id).body(postRequest)

        // when
        every { postService.update(id, postRequest) } just Runs
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "post/update",
            pathParam(
                "id" means "게시글 ID" isOptional false
            ),
            requestBody(
                "photoUrls" fieldType ARRAY means "첨부 사진 url" isOptional true,
                "title" fieldType STRING means "제목" isOptional false,
                "content" fieldType STRING means "내용" isOptional false,
                "categoryId" fieldType NUMBER means "카테고리 ID" isOptional false,
                "regionId" fieldType NUMBER means "지역 ID" isOptional false,
                "price" fieldType NUMBER means "가격" isOptional false,
            ),
        )
    }

    @Test
    fun delete() {
        // given
        val url = "/post/{id}"
        val id = 1L
        val request = delete(url, id)

        // when
        every { postService.delete(id) } just Runs
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "post/delete",
            pathParam(
                "id" means "게시글 ID" isOptional false
            ),
        )
    }

    @Test
    fun fetchOne() {
        // given
        val url = "/post/{id}"
        val id = 1L
        val request = get(url, id)
        val postDetailsResponse = PostFixture.createPostDetailsResponse()

        // when
        every { postQueryService.fetchOne(id) } returns postDetailsResponse
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "post/fetchOne",
            pathParam(
                "id" means "게시글 ID" isOptional false
            ),
            responseBody(
                "id" fieldType NUMBER means "게시글 ID",
                "photoUrls" fieldType ARRAY means "첨부 사진 url",
                "title" fieldType STRING means "제목",
                "content" fieldType STRING means "내용",
                "price" fieldType NUMBER means "가격",
                "timeAgo" fieldType STRING means "등록 이후 시간",
                "category" fieldType OBJECT means "카테고리",
                "category.id" fieldType NUMBER means "카테고리 ID",
                "category.name" fieldType STRING means "카테고리 이름",
                "region" fieldType OBJECT means "지역",
                "region.id" fieldType NUMBER means "지역 ID",
                "region.name" fieldType STRING means "지역 이름",
            ),
        )
    }

    @Test
    fun search() {
        // given
        val url = "/post/search"
        val searchParam = PostFixture.createPostSearchParam(
            title = "title",
            content = "content",
            minPrice = 0,
            maxPrice = 100000,
            hasPhoto = true,
            categoryId = 1L,
            regionId = 1L,
        )
        val pageable = PageRequest.of(0, 10, Direction.ASC, "id")
        val request = get(url).searchParam(searchParam).pageable(pageable)
        val postSummaryResponses = listOf(
            PostFixture.createPostSummaryResponse(id = 1L),
            PostFixture.createPostSummaryResponse(id = 2L),
        )

        // when
        every { postQueryService.search(searchParam, any()) } returns postSummaryResponses
        val result = mockMvc.perform(request)

        // then
        result status HttpStatus.OK
        result.makeDocument(
            "post/search",
            queryParam(
                "title" means "제목" isOptional true,
                "content" means "내용" isOptional true,
                "minPrice" means "최소 가격" isOptional true,
                "maxPrice" means "최대 가격" isOptional true,
                "hasPhoto" means "사진 여부" isOptional true,
                "categoryId" means "카테고리 ID" isOptional true,
                "regionId" means "지역 ID" isOptional true,
                "page" means "페이지 번호" isOptional true,
                "size" means "페이지 크기" isOptional true,
                "sort" means "정렬 항목 및 방향" isOptional true,
            ),
            responseBody(
                "id" fieldType NUMBER means "게시글 ID",
                "photoUrl" fieldType STRING means "첨부 사진 url",
                "title" fieldType STRING means "제목",
                "price" fieldType NUMBER means "가격",
                "timeAgo" fieldType STRING means "등록 이후 시간",
                "categoryName" fieldType STRING means "카테고리 이름",
                "regionName" fieldType STRING means "지역 이름",
            ),
        )
    }


}
