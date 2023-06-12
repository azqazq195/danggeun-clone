package com.moseoh.danggeunclone.post.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    @Column(length = 30, nullable = false)
    val state: String,

    @Column(length = 30, nullable = false)
    val city: String,

    @Column(length = 30, nullable = false)
    val town: String,
)
