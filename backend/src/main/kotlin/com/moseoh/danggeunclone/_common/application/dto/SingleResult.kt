package com.moseoh.danggeunclone._common.application.dto

class SingleResult(
    statusCode: Int,
    message: String?,
    val data: Any,
) : EmptyResult(statusCode, message)