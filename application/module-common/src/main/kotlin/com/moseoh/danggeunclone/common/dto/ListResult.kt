package com.moseoh.danggeunclone.common.dto

class ListResult(
    message: String?,
    val content: List<Any>,
) : EmptyResult(message)