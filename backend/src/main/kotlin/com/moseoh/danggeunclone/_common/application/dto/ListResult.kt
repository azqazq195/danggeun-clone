package com.moseoh.danggeunclone._common.application.dto

class ListResult(
    statusCode: Int,
    message: String?,
    val data: List<Any>,
) : EmptyResult(statusCode, message)