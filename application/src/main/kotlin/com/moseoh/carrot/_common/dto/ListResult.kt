package com.moseoh.carrot._common.dto

class ListResult(
    message: String?,
    val content: List<Any>,
) : EmptyResult(message)