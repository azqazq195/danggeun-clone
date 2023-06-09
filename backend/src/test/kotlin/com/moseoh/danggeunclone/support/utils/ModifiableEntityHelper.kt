package com.moseoh.danggeunclone.support.utils

import com.moseoh.danggeunclone._common.domain.ModifiableEntity
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

fun <T : ModifiableEntity> T.filledDate(): T {
    ReflectionTestUtils.setField(this, "createdAt", LocalDateTime.now())
    ReflectionTestUtils.setField(this, "modifiedAt", LocalDateTime.now())
    return this
}
