package com.moseoh.carrot.domain.region.entity.repository

import com.moseoh.carrot.RegionFixture
import com.moseoh.carrot.domain.region.exception.RegionNotFoundException
import com.moseoh.carrot.helper.JpaRepositoryTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class RegionRepositoryTest @Autowired constructor(
    private val regionRepository: RegionRepository,
) : JpaRepositoryTest({
    Given("getEntityById") {
        val region = RegionFixture.createRegion()
        regionRepository.save(region)

        When("존재하는 Region 의 id 로 찾는다면") {
            val result = regionRepository.getEntityById(region.id)

            Then("Region 를 반환한다.") {
                result.id shouldBe region.id
            }
        }

        When("존재하지 않는 Region 의 id 로 찾는다면") {
            val result = shouldThrow<RegionNotFoundException> {
                regionRepository.getEntityById(0L)
            }

            Then("RegionNotFoundException 이 발생한다.") {
                result shouldBe RegionNotFoundException()
            }
        }
    }
})
