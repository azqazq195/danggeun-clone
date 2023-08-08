package com.moseoh.danggeunclone.common.exception

open class ApiException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)
