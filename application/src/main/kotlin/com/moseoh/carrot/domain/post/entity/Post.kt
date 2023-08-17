package com.moseoh.carrot.domain.post.entity

import com.moseoh.carrot._common.entity.BaseAuditingEntity
import com.moseoh.carrot.domain.category.entity.Category
import com.moseoh.carrot.domain.region.entity.Region
import jakarta.persistence.*

@Entity
class Post(
    title: String,
    content: String,
    category: Category,
    region: Region,
    price: Int,
    photos: List<Photo> = listOf(),
) : BaseAuditingEntity() {
    @Column(nullable = false)
    var title: String = title
        private set

    @Column(nullable = false, columnDefinition = "text")
    var content: String = content
        private set

    @ManyToOne
    var category: Category = category
        private set

    @ManyToOne
    var region: Region = region
        private set

    @Column(nullable = false)
    var price: Int = price
        private set

    @Column(nullable = false)
    var likeCnt: Int = 0
        private set

    @Column(nullable = false)
    var readCnt: Int = 0
        private set

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var photos: List<Photo> = photos
        private set

    fun update(updateValue: Post) {
        this.title = updateValue.title
        this.content = updateValue.content
        this.category = updateValue.category
        this.region = updateValue.region
        this.price = updateValue.price
        this.likeCnt = updateValue.likeCnt
        this.readCnt = updateValue.readCnt
        this.photos = updateValue.photos
    }
}