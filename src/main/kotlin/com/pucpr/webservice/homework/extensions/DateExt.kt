package com.pucpr.webservice.homework.extensions

import com.pucpr.webservice.homework.utils.DateUtils
import java.util.*
import java.util.concurrent.TimeUnit

fun Date.diffBetweenDate(finish: Date): Int {
    val milliseconds = finish.time - this.time
    return TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS).toInt()
}

fun Date.getDifferenceInWeeks(finish: Date): Int {
    val diff = this.diffBetweenDate(finish)
    return if (diff >= DateUtils.DAYS_IN_WEEK) diff / DateUtils.DAYS_IN_WEEK else DateUtils.NONE_DIFF
}

fun Date.getDifferenceInMonths(finish: Date): Int {
    val diff = this.diffBetweenDate(finish)
    return if (diff >= DateUtils.DAYS_IN_MONTH) diff / DateUtils.DAYS_IN_MONTH else DateUtils.NONE_DIFF
}