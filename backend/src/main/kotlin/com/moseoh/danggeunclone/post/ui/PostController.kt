package com.moseoh.danggeunclone.post.ui

import com.moseoh.danggeunclone._common.application.dto.EmptyResult
import com.moseoh.danggeunclone._common.application.dto.ResponseDto
import com.moseoh.danggeunclone._common.application.dto.SingleResult
import com.moseoh.danggeunclone.post.application.PostService
import com.moseoh.danggeunclone.post.application.dto.CreatePostRequest
import com.moseoh.danggeunclone.post.application.dto.UpdatePostRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService
) {
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: Long,
    ): ResponseEntity<SingleResult> {
        val postResponse = postService.get(id)

        return ResponseDto.of(
            message = "조회 완료.",
            status = HttpStatus.OK,
            data = postResponse
        )
    }

    @PostMapping()
    fun create(
        @RequestBody @Valid createPostRequest: CreatePostRequest,
    ): ResponseEntity<SingleResult> {
        val postResponse = postService.create(createPostRequest)

        return ResponseDto.of(
            message = "생성 완료.",
            status = HttpStatus.CREATED,
            data = postResponse
        )
    }

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid updatePostRequest: UpdatePostRequest,
    ): ResponseEntity<SingleResult> {
        val postResponse = postService.update(id, updatePostRequest)

        return ResponseDto.of(
            message = "업데이트 완료.",
            status = HttpStatus.OK,
            data = postResponse
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<EmptyResult> {
        postService.delete(id)

        return ResponseDto.of(
            message = "삭제 완료.",
            status = HttpStatus.OK
        )
    }
}