package com.moseoh.danggeunclone._common.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuditingConfigTest {
    @Autowired
    lateinit var auditingConfig: AuditingConfig

    @Test
    fun `AuditingConfig is not null`() {
        assertNotNull(auditingConfig)
    }
}
