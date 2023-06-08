package com.moseoh.danggeunclone.support.config

import com.moseoh.danggeunclone.support.createUser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class MockUserSecurityContextFactory : WithSecurityContextFactory<MockUser> {
    override fun createSecurityContext(annotation: MockUser): SecurityContext {
        val securityContext = SecurityContextHolder.createEmptyContext()
        val user = createUser(annotation.role)
        val authorities: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        val authenticationToken = UsernamePasswordAuthenticationToken(user.email, "", authorities)
        securityContext.authentication = authenticationToken
        return securityContext
    }
}