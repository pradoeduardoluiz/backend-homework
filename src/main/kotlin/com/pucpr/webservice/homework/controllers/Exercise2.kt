package com.pucpr.webservice.homework.controllers

import com.pucpr.webservice.homework.dtos.*
import com.pucpr.webservice.homework.extensions.diffBetweenDate
import com.pucpr.webservice.homework.extensions.getDifferenceInMonths
import com.pucpr.webservice.homework.extensions.getDifferenceInWeeks
import com.pucpr.webservice.homework.utils.DateUtils
import com.pucpr.webservice.homework.utils.Error
import com.pucpr.webservice.homework.utils.RegexConstants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@RestController
class Exercise2() {

    private val format = SimpleDateFormat(DateUtils.DEFAULT_DATE_PATTERN, Locale.getDefault())

    @PostMapping("api/exercise2/date", produces = ["application/json"])
    fun post(@RequestBody dateDTO: Exercise2DateDTO): ResponseEntity<ResponseDTO<Exercise2DateResponseDTO>> {

        try {
            val start: Date = format.parse(dateDTO.dateStart)
            val finish: Date = format.parse(dateDTO.dateFinish)

            val differenceInDays = start.diffBetweenDate(finish)
            val differenceInWeeks = start.getDifferenceInWeeks(finish)
            val differenceInMonths = start.getDifferenceInMonths(finish)

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

    @PostMapping("api/exercise2/numberSequence", produces = ["application/json"])
    fun post(@RequestBody numbersSequenceDTO: Exercise2NumbersSequenceDTO): ResponseEntity<ResponseDTO<Exercise2NumbersSequenceResponseDTO>> {

        val sequence = numbersSequenceDTO.numbersSequence
                .trim()
                .replace(RegexConstants.WHITE_SPACE, "")
                .split(";")

        // using map for convert string item in int
        val numberSequence = sequence.map { it.toInt() }

        // using filter for get only pair number
        val pairNumbers = numberSequence.filter { it % 2 == 0 }

        val numbersSequenceResponse = Exercise2NumbersSequenceResponseDTO(
                ascendingOrder = numberSequence.sorted().toString(),
                descendingOrder = numberSequence.sortedDescending().toString(),
                pairNumbers = pairNumbers.toString()
        )
        val response = ResponseDTO(data = numbersSequenceResponse)
        return ResponseEntity.ok(response)
    }
}