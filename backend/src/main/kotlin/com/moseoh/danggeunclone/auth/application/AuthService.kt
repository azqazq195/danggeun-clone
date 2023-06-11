package com.moseoh.danggeunclone.auth.application

import com.moseoh.danggeunclone.auth.application.dto.RefreshTokenRequest
import com.moseoh.danggeunclone.auth.application.dto.SignInRequest
import com.moseoh.danggeunclone.auth.application.dto.SignUpRequest
import com.moseoh.danggeunclone.auth.application.dto.TokenResponse
import com.moseoh.danggeunclone.user.application.dto.UserResponse
import com.moseoh.danggeunclone.user.domain.Role
import com.moseoh.danggeunclone.user.domain.repository.UserRepository
import com.moseoh.danggeunclone.user.domain.repository.getByEmail
import com.moseoh.danggeunclone.user.exception.NotFoundUserException
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signIn(signInRequest: SignInRequest): TokenResponse {
        val user = userRepository.getByEmail(signInRequest.email)
        check(passwordEncoder.matches(signInRequest.password, user.password)) { "비밀번호가 올바르지 않습니다." }
        return tokenProvider.create(user)
    }

    fun signOut(auth: Authentication) {
        val accessToken = auth.credentials as String
        tokenProvider.deleteByAccessToken(accessToken)
    }

    @Transactional
    fun signUp(signUpRequest: SignUpRequest) {
        check(!userRepository.existsByEmail(signUpRequest.email)) { "이미 존재하는 이메일입니다." }
        userRepository.save(
            signUpRequest.toUser(
                passwordEncoder.encode(signUpRequest.password),
                Role.USER
            )
        )
    }

    fun refresh(refreshTokenRequest: RefreshTokenRequest): TokenResponse {
        return tokenProvider.refresh(refreshTokenRequest)
    }

    fun me(auth: Authentication): UserResponse {
        val id = auth.principal as Long
        return userRepository
            .findById(id).orElseThrow { NotFoundUserException() }
            .let(::UserResponse)
    }
}