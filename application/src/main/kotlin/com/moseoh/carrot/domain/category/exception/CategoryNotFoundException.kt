package com.moseoh.carrot.domain.category.exception

import com.moseoh.carrot._common.exception.ApiException
import com.moseoh.carrot._common.exception.ErrorCode

class CategoryNotFoundException : ApiException(ErrorCode.CATEGORY_NOT_FOUND)
