package com.moseoh.danggeunclone.auth.application

import com.moseoh.danggeunclone.auth.application.dto.Token
import org.springframework.stereotype.Service

@Service
class AuthService {

    fun getToken(): Token {
        return Token()
    }
}