package com.moseoh.danggeunclone.user.exception

import com.moseoh.danggeunclone._common.exception.ApiException
import com.moseoh.danggeunclone._common.exception.ErrorCode

class NotFoundUserException : ApiException(ErrorCode.NOT_FOUND_USER)