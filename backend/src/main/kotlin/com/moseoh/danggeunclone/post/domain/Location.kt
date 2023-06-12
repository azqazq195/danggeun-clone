package com.moseoh.danggeunclone.post.domain

import jakarta.persistence.Embeddable

@Embeddable
data class Location(
    val longitude: Double,
    val latitude: Double,
)