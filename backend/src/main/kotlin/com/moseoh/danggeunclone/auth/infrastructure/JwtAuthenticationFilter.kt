package com.moseoh.danggeunclone.auth.infrastructure

import com.moseoh.danggeunclone.auth.application.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean


class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
) : GenericFilterBean() {
    companion object {
        const val AUTH_HEADER = "Authorization"
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        val token = resolveToken((request as HttpServletRequest))

        if (token != null && tokenProvider.validateToken(token)) {
            val authentication = tokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader(AUTH_HEADER)
    }
}