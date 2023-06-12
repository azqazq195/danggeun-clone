package com.moseoh.danggeunclone.post.domain

import jakarta.persistence.*

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 20, nullable = false, unique = true)
    val name: String,
)