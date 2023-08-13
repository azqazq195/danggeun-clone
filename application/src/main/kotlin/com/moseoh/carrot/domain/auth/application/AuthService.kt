package com.moseoh.carrot.domain.auth.application

import com.moseoh.carrot.domain.auth.application.dto.*
import com.moseoh.carrot.domain.auth.application.mapper.UserMapper
import com.moseoh.carrot.domain.auth.exception.EmailExistsException
import com.moseoh.carrot.domain.auth.exception.PasswordNotMatchException
import com.moseoh.carrot.domain.user.entity.repository.UserRepository
import com.moseoh.carrot.domain.user.entity.repository.getEntityByEmail
import com.moseoh.carrot.domain.user.entity.repository.getEntityById
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val jwtProvider: JwtProvider,
) {
    @Transactional
    fun signIn(signInRequest: SignInRequest): TokenResponse {
        val user = userRepository.getEntityByEmail(signInRequest.email)
        check(passwordEncoder.matches(signInRequest.password, user.password)) { throw PasswordNotMatchException() }
        return jwtProvider.create(user)
    }

    @Transactional
    fun signOut(auth: Authentication) {
        val accessToken = auth.credentials as String
        jwtProvider.deleteByAccessToken(accessToken)
    }

    @Transactional
    fun signUp(signUpRequest: SignUpRequest) {
        check(!userRepository.existsByEmail(signUpRequest.email)) { throw EmailExistsException() }
        val user = userMapper.dtoToEntity(signUpRequest)
        userRepository.save(user)
    }

    fun refresh(tokenRequest: TokenRequest): TokenResponse {
        return jwtProvider.refresh(tokenRequest)
    }

    fun me(auth: Authentication): MeResponse {
        val id = auth.principal as Long
        val user = userRepository.getEntityById(id)
        return user.let(::MeResponse)
    }
}