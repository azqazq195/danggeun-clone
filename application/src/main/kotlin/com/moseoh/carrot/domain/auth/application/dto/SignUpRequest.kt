package com.moseoh.carrot.domain.auth.application.dto

import com.moseoh.carrot.domain.auth.application.dto.constraints.EmailValidation
import com.moseoh.carrot.domain.region.entity.Region
import com.moseoh.carrot.domain.user.entity.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.intellij.lang.annotations.RegExp

data class SignUpRequest(
    @field:EmailValidation
    val email: String,

    @field:Size(min = 8, max = 20)
    val password: String,

    @field:NotBlank
    val nickname: String,

    @field:Size(min = 11, max = 11)
    @field:RegExp(prefix = "[0-9]*")
    val phone: String,

    val profileUrl: String? = null,

    val regionIds: List<Long> = listOf()
) {
    fun toEntity(encodedPassword: String, regions: List<Region>): User {
        return User(
            email = email,
            password = encodedPassword,
            nickname = nickname,
            phone = phone,
            profileUrl = profileUrl,
            regions = regions
        )
    }
}