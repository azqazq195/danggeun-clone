package com.moseoh.danggeunclone.core.common

import com.moseoh.danggeunclone.core.config.DataJpaTestWithConfig
import io.kotest.core.spec.style.BehaviorSpec

@DataJpaTestWithConfig
abstract class JpaRepositoryTest(
    body: BehaviorSpec.() -> Unit = {},
) : BehaviorSpec() {
    init {
        body()
    }
}