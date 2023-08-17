package com.moseoh.carrot.domain.post.exception

import com.moseoh.carrot._common.exception.ApiException
import com.moseoh.carrot._common.exception.ErrorCode

class PostNotFoundException : ApiException(ErrorCode.POST_NOT_FOUND)
