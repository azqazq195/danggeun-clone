package com.moseoh.carrot.domain.auth.application.dto.constraints

import jakarta.validation.Constraint
import jakarta.validation.Payload
import jakarta.validation.constraints.Email
import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import kotlin.reflect.KClass

@Email(regexp = "^[\\w!#\$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#\$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}\$")
@ConstraintComposition(CompositionType.AND)
@Constraint(validatedBy = [])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
annotation class EmailValidation(
    val message: String = "{jakarta.validation.constraints.Email.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)