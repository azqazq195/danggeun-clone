package com.moseoh.danggeunclone._common.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(formatter.format(value))
    }

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    }
}

