package com.moseoh.carrot.domain.auth.application.mapper

import com.moseoh.carrot.domain.auth.application.dto.SignUpRequest
import com.moseoh.carrot.domain.user.entity.User
import com.moseoh.carrot.domain.user.entity.repository.RegionRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val passwordEncoder: PasswordEncoder,
    private val regionRepository: RegionRepository,
) {
    fun dtoToEntity(dto: SignUpRequest): User {
        val encodedPassword = passwordEncoder.encode(dto.password)
        val regions = regionRepository.findAllById(dto.regionIds)

        return dto.toEntity(encodedPassword, regions)
    }
}