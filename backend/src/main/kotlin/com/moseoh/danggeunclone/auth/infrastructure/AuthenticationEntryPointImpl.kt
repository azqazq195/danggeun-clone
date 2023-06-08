package com.moseoh.danggeunclone.auth.infrastructure

import com.moseoh.danggeunclone._common.exception.ErrorCode
import com.moseoh.danggeunclone._common.util.ExceptionUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthenticationEntryPointImpl : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        ExceptionUtils.handleException(response, ErrorCode.UNAUTHORIZED)
    }
}