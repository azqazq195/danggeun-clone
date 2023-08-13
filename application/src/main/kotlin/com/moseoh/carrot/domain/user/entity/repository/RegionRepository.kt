package com.moseoh.carrot.domain.user.entity.repository

import com.moseoh.carrot.domain.user.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository : JpaRepository<Region, Long>