package com.moseoh.carrot.domain.region.entity.repository

import com.moseoh.carrot.domain.region.entity.Region
import com.moseoh.carrot.domain.region.exception.RegionNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository : JpaRepository<Region, Long>

fun RegionRepository.getEntityById(id: Long): Region =
    findById(id).orElseThrow(::RegionNotFoundException)