package com.moseoh.danggeunclone.post.exception

import com.moseoh.danggeunclone._common.exception.ApiException
import com.moseoh.danggeunclone._common.exception.ErrorCode

class NotFoundCategoryException : ApiException(ErrorCode.NOT_FOUND_CATEGORY)
