package com.moseoh.carrot

import com.moseoh.carrot.domain.region.entity.Region

object RegionFixture {
    fun createRegion(
        name: String = "삼평동"
    ): Region {
        return Region(
            name
        )
    }
}