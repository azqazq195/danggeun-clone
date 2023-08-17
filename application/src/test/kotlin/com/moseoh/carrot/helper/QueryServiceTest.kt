package com.moseoh.carrot.helper

import com.moseoh.carrot.helper.constraints.SpringBootTestWithConfig
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.transaction.annotation.Transactional

@SpringBootTestWithConfig
@Transactional
abstract class QueryServiceTest(
    body: BehaviorSpec.() -> Unit = {}
) : BehaviorSpec({
    body()
})