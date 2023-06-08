package com.moseoh.danggeunclone.auth.application

import com.moseoh.danggeunclone._common.util.RedisDao
import com.moseoh.danggeunclone._common.util.logger
import com.moseoh.danggeunclone.auth.application.dto.RefreshTokenRequest
import com.moseoh.danggeunclone.auth.application.dto.TokenResponse
import com.moseoh.danggeunclone.auth.domain.Token
import com.moseoh.danggeunclone.auth.domain.repository.TokenRepository
import com.moseoh.danggeunclone.auth.domain.repository.getByRefreshToken
import com.moseoh.danggeunclone.user.domain.User
import com.moseoh.danggeunclone.user.domain.repository.UserRepository
import com.moseoh.danggeunclone.user.domain.repository.getByEmail
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.time.Duration
import java.util.*
import java.util.stream.Collectors


@Service
class TokenProvider(
    @Value("\${jwt.secret}")
    private val accessSecret: String,
    @Value("\${jwt.access-token-expire-time}")
    private val accessTokenExpireTime: Long,
    @Value("\${jwt.refresh-secret}")
    private val refreshSecret: String,
    @Value("\${jwt.refresh-token-expire-time}")
    private val refreshTokenExpireTime: Long,
    private val redisDao: RedisDao,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
) {

    companion object {
        const val ROLE = "role"
    }

    private val log = logger()
    private lateinit var accessKey: Key
    private lateinit var refreshKey: Key

    @PostConstruct
    protected fun init() {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret))
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret))
    }

    @Transactional
    fun create(user: User): TokenResponse {
        val token = Token(
            email = user.email,
            accessToken = createAccessToken(user),
            refreshToken = createRefreshToken(user),
            expiredAt = Date(System.currentTimeMillis() + refreshTokenExpireTime)
        )
        return tokenRepository.save(token).let(::TokenResponse)
    }

    @Transactional
    fun deleteByAccessToken(accessToken: String) {
        setBlackList(accessToken)
        tokenRepository.deleteByAccessToken(accessToken)
    }

    @Transactional
    fun refresh(refreshTokenRequest: RefreshTokenRequest): TokenResponse {
        val token = tokenRepository.getByRefreshToken(refreshTokenRequest.refreshToken)
        check(token.accessToken == refreshTokenRequest.accessToken) { "토큰 정보가 일치하지 않습니다." }
        deleteByAccessToken(token.accessToken)
        return userRepository.getByEmail(token.email).let(::create)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val email = claims.subject
        val authorities: Collection<GrantedAuthority> =
            Arrays.stream(claims[ROLE].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
                .map { role: String -> SimpleGrantedAuthority("ROLE_$role") }
                .collect(Collectors.toList())
        return UsernamePasswordAuthenticationToken(email, token, authorities)
    }

    fun validateToken(token: String): Boolean {
        if (redisDao.hasKeyInBlackList(token)) return false

        try {
            getClaims(token)
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
        val expiration = Date(now.time + accessTokenExpireTime)

        return Jwts.builder()
            .setSubject(user.email)
            .claim(ROLE, user.role)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(accessKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun createRefreshToken(user: User): String {
        val now = Date()
        val expiration = Date(now.time + refreshTokenExpireTime)

        return Jwts.builder()
            .setSubject(user.email)
            .claim(ROLE, user.role)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(refreshKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun setBlackList(accessToken: String) {
        val claims = getClaims(accessToken)
        val expiration = claims.expiration
        redisDao.setBlackList(accessToken, "access", Duration.ofMillis(expiration.time))
    }

    private fun getClaims(accessToken: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(accessKey)
            .build()
            .parseClaimsJws(accessToken)
            .body
    }
}