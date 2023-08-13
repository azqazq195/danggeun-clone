package com.moseoh.carrot.domain.auth.exception

import com.moseoh.carrot._common.exception.ApiException
import com.moseoh.carrot._common.exception.ErrorCode

class EmailExistsException : ApiException(ErrorCode.EMAIL_EXISTS)