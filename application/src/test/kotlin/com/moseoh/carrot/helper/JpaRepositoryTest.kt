package com.moseoh.carrot.helper

import io.kotest.core.spec.style.BehaviorSpec

@DataJpaTestWithConfig
abstract class JpaRepositoryTest(
    body: BehaviorSpec.() -> Unit = {},
) : BehaviorSpec() {
    init {
        body()
    }
}