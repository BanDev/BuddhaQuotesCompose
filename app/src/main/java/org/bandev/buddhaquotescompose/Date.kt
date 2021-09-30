package org.bandev.buddhaquotescompose

import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

object Date {

    fun getAsString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(get())
    }

    fun get(): Date {
        return Calendar.getInstance().time
    }

}