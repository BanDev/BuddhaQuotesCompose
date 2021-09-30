package org.bandev.buddhaquotescompose

import java.util.*

object Date {

    fun getAsStringLong(): String {
        val cal = Calendar.getInstance()
        val weekday = when(cal.get(Calendar.DAY_OF_WEEK)) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            else -> "Saturday"
        }
        val month = when(cal.get(Calendar.MONTH)) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            else -> "December"
        }
        val aft = when(cal.get(Calendar.DAY_OF_MONTH) % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
        return weekday + " " + cal.get(Calendar.DAY_OF_MONTH).toString() + aft + " " + month
    }

}