package com.pucpr.webservice.homework.dtos

data class Exercise2DateResponseDTO(
        val differenceInDays: Int,
        val differenceInWeeks: Int,
        val differenceInMonths: Int
)