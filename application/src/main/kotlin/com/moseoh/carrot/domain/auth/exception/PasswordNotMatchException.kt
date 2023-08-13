package com.moseoh.carrot.domain.auth.exception

import com.moseoh.carrot._common.exception.ApiException
import com.moseoh.carrot._common.exception.ErrorCode

class PasswordNotMatchException : ApiException(ErrorCode.PASSWORD_NOT_MATCH)