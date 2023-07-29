package com.moseoh.danggeunclone.core.domain.repository

import com.moseoh.danggeunclone.core.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>