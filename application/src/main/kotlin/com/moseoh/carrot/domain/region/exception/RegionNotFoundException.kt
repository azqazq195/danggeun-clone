package com.moseoh.carrot.domain.region.exception

import com.moseoh.carrot._common.exception.ApiException
import com.moseoh.carrot._common.exception.ErrorCode

class RegionNotFoundException : ApiException(ErrorCode.REGION_NOT_FOUND)