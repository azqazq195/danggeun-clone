package com.moseoh.danggeunclone._common.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest
class PasswordConfigTest {
    @Autowired
    lateinit var passwordEncoder: BCryptPasswordEncoder

    @Test
    fun `passwordEncoder is not null`() {
        assertNotNull(passwordEncoder)
    }

    @Test
    fun `passwordEncoder bean encodes password`() {
        val password = "test1234"
        val encodedPassword = passwordEncoder.encode(password)
        assertTrue(passwordEncoder.matches(password, encodedPassword))
    }
}
