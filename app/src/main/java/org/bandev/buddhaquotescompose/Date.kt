package org.bandev.buddhaquotescompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.util.*

object Date {

    @Composable
    fun getWeekday(calendar: Calendar): String {
        return stringResource(
            arrayOf(
                R.string.sunday, R.string.monday, R.string.tuesday, R.string.wednesday,
                R.string.thursday, R.string.friday, R.string.saturday
            )[calendar.get(Calendar.DAY_OF_WEEK) - 1]
        )
    }

    @Composable
    fun getMonth(calendar: Calendar): String {
        return stringResource(
            arrayOf(
                R.string.january, R.string.february, R.string.march, R.string.april, R.string.may,
                R.string.june, R.string.july, R.string.august, R.string.september, R.string.october,
                R.string.november, R.string.december
            )[calendar.get(Calendar.MONTH)]
        )
    }

    @Composable
    fun getOrdinalSuffix(calendar: Calendar): String {
        return stringResource(
            when(calendar.get(Calendar.DAY_OF_MONTH) % 10) {
                1 -> R.string.st
                2 -> R.string.nd
                3 -> R.string.rd
                else -> R.string.th
            }
        )
    }

    @Composable
    fun getFormattedDate(): String {
        Calendar.getInstance().also {
            return getWeekday(it) + " " + it.get(Calendar.DAY_OF_MONTH).toString() + getOrdinalSuffix(it) + " " + getMonth(it)
        }
    }

}