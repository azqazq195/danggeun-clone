package com.moseoh.carrot.helper

import com.moseoh.carrot.helper.constraints.SpringBootTestWithConfig
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks

@SpringBootTestWithConfig
abstract class ServiceTest(body: BehaviorSpec.() -> Unit = {}) : BehaviorSpec({
    afterTest {
        clearAllMocks()
    }

    body()
})