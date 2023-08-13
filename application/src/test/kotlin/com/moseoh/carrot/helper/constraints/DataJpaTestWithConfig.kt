package com.moseoh.carrot.helper.constraints

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
annotation class DataJpaTestWithConfig
