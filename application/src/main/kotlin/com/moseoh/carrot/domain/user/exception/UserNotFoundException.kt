package com.moseoh.carrot.domain.user.exception

import com.moseoh.carrot._common.exception.ApiException
import com.moseoh.carrot._common.exception.ErrorCode


class UserNotFoundException : ApiException(ErrorCode.USER_NOT_FOUND)