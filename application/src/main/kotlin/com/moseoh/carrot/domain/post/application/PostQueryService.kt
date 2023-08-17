package com.moseoh.carrot.domain.post.application

import com.moseoh.carrot.domain.category.entity.QCategory
import com.moseoh.carrot.domain.post.application.dto.*
import com.moseoh.carrot.domain.post.entity.QPhoto
import com.moseoh.carrot.domain.post.entity.QPost
import com.moseoh.carrot.domain.post.exception.PostNotFoundException
import com.moseoh.carrot.domain.region.entity.QRegion
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.EntityPathBase
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PostQueryService(
    private val jpaQueryFactory: JPAQueryFactory
) {
    fun fetchOne(id: Long): PostDetailsResponse {
        val post = QPost.post
        val category = QCategory.category
        val region = QRegion.region
        val photo = QPhoto.photo

        return jpaQueryFactory
            .select(QPostDetailsResponse(post)).from(post)
            .leftJoin(post.category, category).fetchJoin()
            .leftJoin(post.region, region).fetchJoin()
            .leftJoin(post.photos, photo).fetchJoin()
            .where(post.id.eq(id))
            .fetchOne()
            ?: throw PostNotFoundException();
    }

    fun search(param: PostSearchParam, pageable: Pageable): List<PostSummaryResponse> {
        val post = QPost.post
        val category = QCategory.category
        val region = QRegion.region
        val photo = QPhoto.photo

        val searchFilter = searchFilter(param, post, category, region)

        val orderSpecifiers = getOrderSpecifiers(post, pageable)
        // join 마다 fetchJoin해주어야 한 쿼리로 불러온다.
        val content = jpaQueryFactory
            .from(post)
            .leftJoin(post.category, category).fetchJoin()
            .leftJoin(post.region, region).fetchJoin()
            .leftJoin(post.photos, photo).fetchJoin()
            .where(searchFilter)
            .select(QPostSummaryResponse(post))
            .orderBy(*orderSpecifiers)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        // post 의 개수를 세는 것으로 leftJoin을 fetchJoin 설정시 count가 부정확할 수 있으며 컴파일 에러 발생
        // 조건에 맞는 post 갯수를 세어야 하기 때문에 join이 없을 수는 없다.
        val total = jpaQueryFactory
            .select(post.count())
            .from(post)
            .leftJoin(post.category, category)
            .leftJoin(post.region, region)
            .leftJoin(post.photos, photo)
            .where(searchFilter)
            .fetchOne() ?: 0L

        return content
    }

    private fun searchFilter(
        param: PostSearchParam,
        post: QPost,
        category: QCategory,
        region: QRegion,
    ): BooleanBuilder {
        val builder = BooleanBuilder()

        param.title?.let {
            builder.and(post.title.contains(param.title))
        }
        param.content?.let {
            builder.and(post.content.contains(param.content))
        }
        param.minPrice?.let {
            builder.and(post.price.goe(param.minPrice))
        }
        param.maxPrice?.let {
            builder.and(post.price.loe(param.maxPrice))
        }
        param.hasPhoto?.let {
            builder.and(if (it) post.photos.isNotEmpty else post.photos.isEmpty)
        }

        param.categoryId?.let {
            builder.and(category.id.eq(param.categoryId))
        }
        param.regionId?.let {
            builder.and(region.id.eq(param.regionId))
        }

        return builder
    }

    private fun getOrderSpecifiers(entity: EntityPathBase<*>, pageable: Pageable): Array<OrderSpecifier<*>> {
        return pageable.sort.map { order ->
            val direction = if (order.isAscending) Order.ASC else Order.DESC
            val pathBuilder = PathBuilder(entity.type, entity.metadata)
            val path = Expressions.stringPath(pathBuilder, order.property)
            OrderSpecifier(direction, path)
        }.toList().toTypedArray()
    }
}