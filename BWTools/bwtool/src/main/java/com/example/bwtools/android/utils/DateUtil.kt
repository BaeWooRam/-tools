package com.example.bwtools.android.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    enum class DatePattern(private val pattern:String){
        yyyy_MM_dd_HHmmss("yyyy-mm-dd HH:mm:ss"),
        yyyy_MM_dd("yyyy-MM-dd"),
        yyyy년_MM월_dd일("yyyy년 MM월 dd일"),
        dd일("dd일");

        fun value() = pattern
    }

    fun currentDate(pattern: DatePattern):String{
        val date = Date(System.currentTimeMillis())
        return SimpleDateFormat(pattern.value()).format(date)
    }

    fun transformStringToDate(pattern: DatePattern, dateString: String): Date {
        val transFormat = SimpleDateFormat(pattern.value())
        return transFormat.parse(dateString)
    }

    fun transformStringToDate(dateString: String): Date {
        return transformStringToDate(DatePattern.yyyy_MM_dd_HHmmss, dateString)
    }

    fun transformDateToString(pattern: DatePattern, date: Date): String {
        val transFormat = SimpleDateFormat(pattern.value())
        return transFormat.format(date)
    }

    fun transformDateToString(date: Date): String {
        return transformDateToString(DatePattern.yyyy_MM_dd_HHmmss, date)
    }

    /**
     * 오늘 처음과 끝을 가져옵니다.
     * @author 배우람
     */
    fun currentThisDay(): Pair<Date, Date> {
        val currentTime = transformStringToDate(DatePattern.yyyy_MM_dd, currentDate(DatePattern.yyyy_MM_dd))

        val cal = Calendar.getInstance(Locale.KOREA)
        cal.time = currentTime
        cal.add(Calendar.HOUR, 0)
        cal.add(Calendar.MINUTE, 0)
        cal.add(Calendar.SECOND, 0)
        val start = cal.time

        cal.time = currentTime
        cal.add(Calendar.HOUR, 23)
        cal.add(Calendar.MINUTE, 59)
        cal.add(Calendar.SECOND, 59)
        val end = cal.time

        return Pair(start, end)
    }

    /**
     * 특정 Day 처음과 끝을 가져옵니다.
     * @author 배우람
     */
    fun currentTargetDay(date: Date): Pair<Date, Date> {
        val cal = Calendar.getInstance(Locale.KOREA)
        cal.time = date
        cal.add(Calendar.HOUR, 0)
        cal.add(Calendar.MINUTE, 0)
        cal.add(Calendar.SECOND, 0)
        val start = cal.time

        cal.time = date
        cal.add(Calendar.HOUR, 23)
        cal.add(Calendar.MINUTE, 59)
        cal.add(Calendar.SECOND, 59)
        val end = cal.time

        return Pair(start, end)
    }


    /**
     * 이번 주 월요일과 일요일 날짜를 가져옵니다.
     * @author 배우람
     */
    fun currentThisWeek(): Pair<Date, Date> {
        val currentTime = transformStringToDate(DatePattern.yyyy_MM_dd, currentDate(DatePattern.yyyy_MM_dd))

        val cal = Calendar.getInstance(Locale.KOREA)
        cal.time = currentTime
        cal.add(Calendar.DATE, 1 - cal.get(Calendar.DAY_OF_WEEK))
        val monday = cal.time

        cal.time = currentTime
        cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK))
        cal.add(Calendar.HOUR, 23)
        cal.add(Calendar.MINUTE, 59)
        cal.add(Calendar.SECOND, 59)
        val sunday = cal.time
        return Pair(monday, sunday)
    }


    /**
     * 이번 주 전체 날짜를 가져옵니다.
     * @author 배우람
     */
    fun currentThisWeekAllDay(): List<Date> {
        val currentTime = transformStringToDate(DatePattern.yyyy_MM_dd, currentDate(DatePattern.yyyy_MM_dd))
        val cal = Calendar.getInstance(Locale.KOREA)
        val tempList = mutableListOf<Date>()

        for (amount in 1..7){
            cal.time = currentTime
            cal.add(Calendar.DATE, amount - cal.get(Calendar.DAY_OF_WEEK))
            tempList.add(cal.time)
        }

        return tempList
    }

    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    fun fromDate(date: Date): Long {
        return date.time
    }
}