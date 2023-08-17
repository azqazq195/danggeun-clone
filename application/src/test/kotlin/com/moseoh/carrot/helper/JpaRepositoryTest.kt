package com.moseoh.carrot.helper

import com.moseoh.carrot.helper.constraints.DataJpaTestWithConfig
import io.kotest.core.spec.style.BehaviorSpec

@DataJpaTestWithConfig
abstract class JpaRepositoryTest(
    body: BehaviorSpec.() -> Unit = {},
) : BehaviorSpec({
    body()
})