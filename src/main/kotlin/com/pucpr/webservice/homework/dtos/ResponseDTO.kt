package com.pucpr.webservice.homework.dtos

data class ResponseDTO<T>(
        val data: T? = null,
        val errors: MutableList<String>? = null
)