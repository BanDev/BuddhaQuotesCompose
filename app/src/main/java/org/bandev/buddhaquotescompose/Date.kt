package org.bandev.buddhaquotescompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

object Date {

    @Composable
    fun getWeekday(calendar: Calendar = Calendar.getInstance()): String {
        return stringResource(
            arrayOf(
                R.string.sunday, R.string.monday, R.string.tuesday, R.string.wednesday,
                R.string.thursday, R.string.friday, R.string.saturday
            )[calendar.get(Calendar.DAY_OF_WEEK) - 1]
        )
    }

    @Composable
    fun getOrdinalSuffix(calendar: Calendar = Calendar.getInstance()): String = stringResource(
        when(calendar.get(Calendar.DAY_OF_MONTH) % 10) {
            1 -> R.string.st
            2 -> R.string.nd
            3 -> R.string.rd
            else -> R.string.th
        }
    )

    @Composable
    fun getOrdinalConcatenation(calendar: Calendar = Calendar.getInstance()): String {
        return calendar.get(Calendar.DAY_OF_MONTH).toString() + getOrdinalSuffix(calendar)
    }

    @Composable
    fun getMonthAndYear(calendar: Calendar = Calendar.getInstance()): String {
        return "${SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)} ${calendar.get(Calendar.YEAR)}"
    }

    @Composable
    fun getWeekdayWithOrdinal(calendar: Calendar = Calendar.getInstance()): String {
        return "${getWeekday(calendar)} ${stringResource(R.string.the)} ${getOrdinalConcatenation(calendar)}"
    }

}