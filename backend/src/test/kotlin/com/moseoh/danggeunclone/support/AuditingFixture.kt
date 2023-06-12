package com.moseoh.danggeunclone.support

import com.moseoh.danggeunclone._common.domain.AuditingEntity
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

fun createCreatedAuditing(
    createdBy: Long = 0L,
    createdAt: LocalDateTime = LocalDateTime.now(),
    modifiedBy: Long = 0L,
    modifiedAt: LocalDateTime = LocalDateTime.now(),
): AuditingEntity {
    val auditingEntity = AuditingEntity()
    ReflectionTestUtils.setField(auditingEntity, "createdBy", createdBy)
    ReflectionTestUtils.setField(auditingEntity, "createdAt", createdAt)
    ReflectionTestUtils.setField(auditingEntity, "modifiedBy", modifiedBy)
    ReflectionTestUtils.setField(auditingEntity, "modifiedAt", modifiedAt)
    return auditingEntity
}