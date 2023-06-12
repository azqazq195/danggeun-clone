package com.moseoh.danggeunclone.post.domain

import com.moseoh.danggeunclone._common.domain.AuditingEntity
import com.moseoh.danggeunclone.post.application.dto.UpdatePostRequest
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@EntityListeners(AuditingEntityListener::class)
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 20, nullable = false)
    val title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val category: Category,

    @Column(nullable = false)
    val status: Status,

    @Lob
    @Column(nullable = false)
    val content: String,

    @Embedded
    val address: Address,

    @Embedded
    val location: Location,

    @Embedded
    val auditing: AuditingEntity = AuditingEntity(),
) {
    fun updated(updatePostRequest: UpdatePostRequest, category: Category? = null): Post {
        return this.copy(
            title = updatePostRequest.title ?: this.title,
            category = category ?: this.category,
            content = updatePostRequest.content ?: this.content,
            status = updatePostRequest.status ?: this.status
        )
    }
}