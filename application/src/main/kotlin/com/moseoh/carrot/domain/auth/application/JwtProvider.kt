package com.moseoh.carrot.domain.auth.application

import com.moseoh.carrot._common.environment.JwtProperties
import com.moseoh.carrot._common.utils.logger
import com.moseoh.carrot.domain.auth.application.dto.TokenRequest
import com.moseoh.carrot.domain.auth.application.dto.TokenResponse
import com.moseoh.carrot.domain.auth.entity.BlackListToken
import com.moseoh.carrot.domain.auth.entity.Token
import com.moseoh.carrot.domain.auth.entity.repository.BlackListTokenRepository
import com.moseoh.carrot.domain.auth.entity.repository.TokenRepository
import com.moseoh.carrot.domain.auth.entity.repository.getEntityByAccessToken
import com.moseoh.carrot.domain.auth.entity.repository.getEntityByRefreshToken
import com.moseoh.carrot.domain.auth.exception.TokenNotMatchException
import com.moseoh.carrot.domain.user.entity.User
import com.moseoh.carrot.domain.user.entity.repository.UserRepository
import com.moseoh.carrot.domain.user.entity.repository.getEntityById
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.annotation.PostConstruct
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.util.*

@Service
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val blackListTokenRepository: BlackListTokenRepository,
) {

    companion object {
        const val ROLE = "role"
    }

    private val log = logger()
    private lateinit var accessKey: Key
    private lateinit var refreshKey: Key

    @PostConstruct
    protected fun init() {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.accessSecret))
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.refreshSecret))
    }

    @Transactional
    fun create(user: User): TokenResponse {
        val token = Token(
            userId = user.id,
            accessToken = createAccessToken(user),
            refreshToken = createRefreshToken(user),
            expiration = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpireTime).time
        )
        return tokenRepository.save(token).let(::TokenResponse)
    }

    @Transactional
    fun deleteByAccessToken(accessToken: String) {
        setBlackList(accessToken)
        val token = tokenRepository.getEntityByAccessToken(accessToken)
        tokenRepository.delete(token)
    }

    @Transactional
    fun refresh(tokenRequest: TokenRequest): TokenResponse {
        val token = tokenRepository.getEntityByRefreshToken(tokenRequest.refreshToken)
        check(token.accessToken == tokenRequest.accessToken) { TokenNotMatchException() }
        deleteByAccessToken(token.accessToken)
        return userRepository.getEntityById(token.userId).let(::create)
    }

    fun getAuthentication(accessToken: String): Authentication {
        val claims = getClaims(accessToken)
        val id = claims.subject.toLong()
        val authorities: Collection<GrantedAuthority> =
            claims[ROLE].toString()
                .split(",")
                .filter { it.isNotEmpty() }
                .map { SimpleGrantedAuthority("ROLE_$it") }
        return UsernamePasswordAuthenticationToken(id, accessToken, authorities)
    }

    fun validateToken(accessToken: String): Boolean {
        if (blackListTokenRepository.existsByAccessToken(accessToken)) return false

        try {
            getClaims(accessToken)
            return true
        } catch (e: SignatureException) {
            log.error("Invalid JWT signature: {}", e.message);
        } catch (e: MalformedJwtException) {
            log.error("Invalid JWT token: {}", e.message);
        } catch (e: ExpiredJwtException) {
            log.error("JWT token is expired: {}", e.message);
        } catch (e: UnsupportedJwtException) {
            log.error("JWT token is unsupported: {}", e.message);
        } catch (e: IllegalArgumentException) {
            log.error("JWT claims string is empty: {}", e.message);
        } catch (e: Exception) {
            log.error("JWT token is invalid: {}", e.message);
        }

        return false
    }

    private fun createAccessToken(user: User): String {
        val now = Date()
        val expiration = Date(now.time + jwtProperties.accessTokenExpireTime)

        return Jwts.builder()
            .setSubject(user.id.toString())
            .claim(ROLE, user.role)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(accessKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun createRefreshToken(user: User): String {
        val now = Date()
        val expiration = Date(now.time + jwtProperties.refreshTokenExpireTime)

        return Jwts.builder()
            .setSubject(user.id.toString())
            .claim(ROLE, user.role)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(refreshKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun setBlackList(accessToken: String) {
        val claims = getClaims(accessToken)
        val expiration = claims.expiration.time
        blackListTokenRepository.save(BlackListToken(accessToken, expiration))
    }

    private fun getClaims(accessToken: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(accessKey)
            .build()
            .parseClaimsJws(accessToken)
            .body
    }
}