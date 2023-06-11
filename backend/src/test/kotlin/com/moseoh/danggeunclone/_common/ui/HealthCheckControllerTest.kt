package com.moseoh.danggeunclone._common.ui

import com.moseoh.danggeunclone.support.ui.RestControllerTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.get

@WebMvcTest(HealthCheckController::class)
class HealthCheckControllerTest : RestControllerTest() {

    @Test
    fun healthCheck() {
        // when
        val result = mockMvc.get("/health")

        // then
        result.andExpect {
            status { isOk() }
        }.andDo {
            document(
                "health",
            )
        }
    }
}