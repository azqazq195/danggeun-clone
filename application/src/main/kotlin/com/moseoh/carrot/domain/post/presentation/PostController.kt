package com.moseoh.carrot.domain.post.presentation

import com.moseoh.carrot._common.dto.EmptyResult
import com.moseoh.carrot._common.dto.ListResult
import com.moseoh.carrot._common.dto.ResponseDto
import com.moseoh.carrot._common.dto.SingleResult
import com.moseoh.carrot.domain.post.application.PostQueryService
import com.moseoh.carrot.domain.post.application.PostService
import com.moseoh.carrot.domain.post.application.dto.PostRequest
import com.moseoh.carrot.domain.post.application.dto.PostSearchParam
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService,
    private val postQueryService: PostQueryService,
) {
    @PostMapping
    fun create(
        @RequestBody @Valid postRequest: PostRequest
    ): ResponseEntity<EmptyResult> {
        postService.create(postRequest)

        return ResponseDto.of(
            message = "게시글 생성 완료.",
            status = HttpStatus.CREATED
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid postRequest: PostRequest
    ): ResponseEntity<EmptyResult> {
        postService.update(id, postRequest)

        return ResponseDto.of(
            message = "게시글 수정 완료.",
            status = HttpStatus.OK
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<EmptyResult> {
        postService.delete(id)

        return ResponseDto.of(
            message = "게시글 삭제 완료.",
            status = HttpStatus.OK
        )
    }

    @GetMapping("/{id}")
    fun fetchOne(
        @PathVariable id: Long
    ): ResponseEntity<SingleResult> {
        val response = postQueryService.fetchOne(id)

        return ResponseDto.of(
            message = "게시글 조회 완료.",
            status = HttpStatus.OK,
            content = response
        )
    }

    @GetMapping("/search")
    fun search(
        searchParam: PostSearchParam,
        pageable: Pageable,
    ): ResponseEntity<ListResult> {
        val response = postQueryService.search(searchParam, pageable)

        return ResponseDto.of(
            message = "게시글 검색 완료.",
            status = HttpStatus.OK,
            content = response
        )
    }
}