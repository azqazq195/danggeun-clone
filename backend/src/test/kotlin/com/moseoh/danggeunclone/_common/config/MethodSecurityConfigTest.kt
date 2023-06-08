package com.moseoh.danggeunclone._common.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MethodSecurityConfigTest {
    @Autowired
    lateinit var methodSecurityConfig: MethodSecurityConfig

    @Test
    fun `MethodSecurityConfig is not null`() {
        assertNotNull(methodSecurityConfig)
    }
}
