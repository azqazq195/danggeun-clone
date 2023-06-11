package com.moseoh.danggeunclone.auth.exception

import com.moseoh.danggeunclone._common.exception.ApiException
import com.moseoh.danggeunclone._common.exception.ErrorCode

class NotFoundRefreshTokenException : ApiException(ErrorCode.NOT_FOUND_REFRESH_TOKEN)