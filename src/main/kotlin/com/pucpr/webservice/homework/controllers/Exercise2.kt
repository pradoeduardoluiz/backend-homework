package com.pucpr.webservice.homework.controllers

import com.pucpr.webservice.homework.dtos.Exercise2DateDTO
import com.pucpr.webservice.homework.dtos.Exercise2DateResponseDTO
import com.pucpr.webservice.homework.dtos.ResponseDTO
import com.pucpr.webservice.homework.utils.DateUtils
import com.pucpr.webservice.homework.utils.Error
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@RestController
class Exercise2() {

    private val format = SimpleDateFormat(DateUtils.DEFAULT_DATE_PATTERN, Locale.getDefault())
    private val DAYS_IN_MONTH = 30
    private val DAYS_IN_WEEK = 7
    private val NONE = 0

    @PostMapping("api/exercise2/date", produces = ["application/json"])
    fun post(@RequestBody dateDTO: Exercise2DateDTO): ResponseEntity<ResponseDTO<Exercise2DateResponseDTO>> {

        try {
            val start: Date = format.parse(dateDTO.dateStart)
            val finish: Date = format.parse(dateDTO.dateFinish)

            val differenceInDays = getDiffDate(start, finish)
            val differenceInWeeks = getDifferenceInWeeks(start, finish)
            val differenceInMonths = getDifferenceInMonths(start, finish)

            val dataResponse = Exercise2DateResponseDTO(
                    differenceInDays = differenceInDays,
                    differenceInWeeks = differenceInWeeks,
                    differenceInMonths = differenceInMonths
            )

            val response = ResponseDTO(data = dataResponse)
            return ResponseEntity.ok(response)
        } catch (e: ParseException) {
            e.printStackTrace()
            return ResponseEntity.badRequest().body(ResponseDTO(errors = mutableListOf(Error.Message.DATA_CONVERSION)))
        }

    }

    private fun getDiffDate(start: Date, finish: Date): Int {
        val milliseconds = finish.time - start.time
        return TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS).toInt()
    }

    private fun getDifferenceInWeeks(start: Date, finish: Date): Int {
        val diff = getDiffDate(start, finish)
        return if (diff >= DAYS_IN_WEEK) diff / DAYS_IN_WEEK else NONE
    }

    private fun getDifferenceInMonths(start: Date, finish: Date): Int {
        val diff = getDiffDate(start, finish)
        return if (diff >= DAYS_IN_MONTH) diff / DAYS_IN_MONTH else NONE
    }
}