package com.moseoh.carrot.domain.user.entity

import com.moseoh.carrot._common.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
class User(
    role: Role = Role.USER,
    email: String,
    password: String,
    nickname: String,
    phone: String,
    profileUrl: String? = null,
    regions: List<Region>
) : BaseTimeEntity() {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = role
        private set

    @Column(unique = true)
    var email: String = email
        private set

    @Column(nullable = false)
    var password: String = password
        private set

    @Column(nullable = false)
    var nickname: String = nickname
        private set

    @Column(nullable = false)
    var phone: String = phone
        private set

    @Column(nullable = true)
    var profileUrl: String? = profileUrl
        private set

    @Column(nullable = false)
    var temperature: Double = 36.0
        private set

    @ManyToMany(cascade = [CascadeType.PERSIST])
    @JoinTable(
        name = "user_regions",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "region_id")]
    )
    var regions: List<Region> = regions
        private set
}