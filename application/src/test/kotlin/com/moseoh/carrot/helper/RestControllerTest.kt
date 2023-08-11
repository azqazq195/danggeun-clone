package com.moseoh.carrot.helper

import com.fasterxml.jackson.databind.ObjectMapper
import com.moseoh.carrot.UserFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
abstract class RestControllerTest {
    private val objectMapper = ObjectMapper()

    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider,
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(
                    restDocumentationContextProvider
                )
                    .operationPreprocessors()
                    .withRequestDefaults(Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.prettyPrint())
            )
            .build()
    }

    fun MockHttpServletRequestBuilder.body(value: Any): MockHttpServletRequestBuilder {
        this.contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(value))
        return this
    }

    fun MockHttpServletRequestBuilder.isAuthenticated(): MockHttpServletRequestBuilder {
        val user = UserFixture.createUser()
        val authorities: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        val authentication = UsernamePasswordAuthenticationToken(user.id, "", authorities)

        this.header(HttpHeaders.AUTHORIZATION, "accessToken")
        this.with(SecurityMockMvcRequestPostProcessors.authentication(authentication))

        return this
    }

    infix fun ResultActions.status(value: HttpStatus) {
        this.andExpect(MockMvcResultMatchers.status().`is`(value.value()))
    }
}