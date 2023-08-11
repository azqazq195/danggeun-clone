package com.moseoh.carrot._common.exception

open class ApiException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
