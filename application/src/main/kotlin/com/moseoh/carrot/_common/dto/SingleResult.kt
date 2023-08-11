package com.moseoh.carrot._common.dto

class SingleResult(
    message: String?,
    val content: Any,
) : EmptyResult(message)