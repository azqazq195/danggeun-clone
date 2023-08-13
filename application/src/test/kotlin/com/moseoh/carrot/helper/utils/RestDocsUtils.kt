package com.moseoh.carrot.helper.utils

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.QueryParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.ResultActions

fun ResultActions.makeDocument(
    identifier: String,
    vararg snippets: Snippet
): ResultActions {
    return andDo(MockMvcRestDocumentation.document(identifier, *snippets))
}

fun queryParam(vararg fields: RestDocParam): QueryParametersSnippet =
    RequestDocumentation.queryParameters(fields.map { it.descriptor })

fun pathParam(vararg fields: RestDocParam): PathParametersSnippet =
    RequestDocumentation.pathParameters(fields.map { it.descriptor })

fun requestBody(vararg fields: RestDocField): RequestFieldsSnippet =
    PayloadDocumentation.requestFields(fields.map { it.descriptor })

fun responseBody(vararg fields: RestDocField): ResponseFieldsSnippet =
    PayloadDocumentation.relaxedResponseFields(fields.map { it.descriptor })

/**
 * query, path parameters
 */
infix fun String.means(
    value: String
): RestDocParam {
    return createParam(this, value)
}

private fun createParam(
    value: String,
    means: String,
    optional: Boolean = true
): RestDocParam {
    val descriptor = RequestDocumentation
        .parameterWithName(value)
        .description(means)

    if (optional) descriptor.optional()

    return RestDocParam(descriptor)
}

open class RestDocParam(
    val descriptor: ParameterDescriptor,
) {
    val isIgnored: Boolean = descriptor.isIgnored
    val isOptional: Boolean = descriptor.isOptional

    open infix fun means(value: String): RestDocParam {
        descriptor.description(value)
        return this
    }

    open infix fun attributes(block: RestDocParam.() -> Unit): RestDocParam {
        block()
        return this
    }

    open infix fun isOptional(value: Boolean): RestDocParam {
        if (value) descriptor.optional()
        return this
    }

    open infix fun isIgnored(value: Boolean): RestDocParam {
        if (value) descriptor.ignored()
        return this
    }
}

/**
 * request, response body
 */
infix fun String.fieldType(
    docsFieldType: DocsFieldType
): RestDocField {
    return createField(this, docsFieldType.type)
}

private fun createField(
    value: String,
    type: JsonFieldType,
    optional: Boolean = true
): RestDocField {
    val descriptor = PayloadDocumentation
        .fieldWithPath(value)
        .type(type)
        .description("")

    if (optional) descriptor.optional()

    return RestDocField(descriptor)
}

open class RestDocField(
    val descriptor: FieldDescriptor,
) {
    val isIgnored: Boolean = descriptor.isIgnored
    val isOptional: Boolean = descriptor.isOptional

    open infix fun means(value: String): RestDocField {
        descriptor.description(value)
        return this
    }

    open infix fun attributes(block: RestDocField.() -> Unit): RestDocField {
        block()
        return this
    }

    open infix fun isOptional(value: Boolean): RestDocField {
        if (value) descriptor.optional()
        return this
    }

    open infix fun isIgnored(value: Boolean): RestDocField {
        if (value) descriptor.ignored()
        return this
    }
}

sealed class DocsFieldType(val type: JsonFieldType)

data object ARRAY : DocsFieldType(JsonFieldType.ARRAY)
data object BOOLEAN : DocsFieldType(JsonFieldType.BOOLEAN)
data object OBJECT : DocsFieldType(JsonFieldType.OBJECT)
data object NUMBER : DocsFieldType(JsonFieldType.NUMBER)
data object NULL : DocsFieldType(JsonFieldType.NULL)
data object STRING : DocsFieldType(JsonFieldType.STRING)
data object ANY : DocsFieldType(JsonFieldType.VARIES)
data object DATE : DocsFieldType(JsonFieldType.STRING)
data object DATETIME : DocsFieldType(JsonFieldType.STRING)
