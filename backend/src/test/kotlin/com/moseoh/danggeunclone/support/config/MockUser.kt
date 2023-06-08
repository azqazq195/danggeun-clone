package com.moseoh.danggeunclone.support.config

import com.moseoh.danggeunclone.user.domain.Role
import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = MockUserSecurityContextFactory::class)
annotation class MockUser(
    val role: Role = Role.USER
)
