package com.moseoh.danggeunclone.user.domain.repository

import com.moseoh.danggeunclone.user.domain.User
import com.moseoh.danggeunclone.user.exception.NotFoundUserException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}

fun UserRepository.getByEmail(email: String) = findByEmail(email)
    ?: throw NotFoundUserException()