package com.example.bwtools.android.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {
    const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    fun getCurrentDate(): String {
        val format1 = SimpleDateFormat(DATE_FORMAT)
        val date = Date()
        return format1.format(date)
    }

    fun transformStringToDate(dateString: String): Date {
        val transFormat = SimpleDateFormat(DATE_FORMAT);
        return transFormat.parse(dateString)
    }

    fun transformDateToString(date: Date): String {
        val transFormat = SimpleDateFormat(DATE_FORMAT);
        return transFormat.format(date)
    }

    fun getDate(
        milliSeconds: Long
    ): String? { // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(DATE_FORMAT)
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}