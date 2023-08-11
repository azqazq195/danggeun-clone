package com.moseoh.carrot.domain.user.entity.repository

import com.moseoh.carrot.domain.user.entity.User
import com.moseoh.carrot.domain.user.exception.UserNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Optional<User>
}

fun UserRepository.getEntityByEmail(email: String): User =
    findByEmail(email).orElseThrow(::UserNotFoundException)

fun UserRepository.getEntityById(id: Long): User =
    findById(id).orElseThrow(::UserNotFoundException)