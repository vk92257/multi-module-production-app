package com.core.util

import android.annotation.SuppressLint
import java.net.URLDecoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Util {


    /**
     * Convert date string.
     *
     * @param timeMilliSecond the timeMilliSecond
     * @param format          the format
     * @return the string
     */
    fun convertDate(timeMilliSecond: Long, format: String?): String {
        var mDate = ""
        val cal = Calendar.getInstance()
        val date = Date(timeMilliSecond)
        val df1: DateFormat = SimpleDateFormat(format)
        df1.timeZone = TimeZone.getDefault()
        mDate = df1.format(date)
        return mDate
    }


    fun replaceExtra(number: String?): String? {
        var number = number
        try {
            if (number != null) {
                number = URLDecoder.decode(number, "UTF-8")
                number =
                    number.replace("[\\-\\+\\.\\^:,]".toRegex(), "").replace("\\s".toRegex(), "")
                        .replace("\\(".toRegex(), "").replace("\\)".toRegex(), "")
                        .replace("\\s+".toRegex(), "").replace("[\\D]".toRegex(), "")
                        .replace("[^0-9]+".toRegex(), "").replace("[^\\d]".toRegex(), "")
                        .replace("\\s+".toRegex(), "")
            } else {
                return ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return number
    }


    @SuppressLint("SimpleDateFormat")
    fun getDateOrTime(date: Date, pattern: String?): String? {
        val timeFormat = SimpleDateFormat(pattern)
        return timeFormat.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun isTodayOrYesterday(date: String, pattern: String): String {
        try {
            val datFormat = SimpleDateFormat(pattern)
            val dateToCompare: Date = datFormat.parse(date) as Date
            val cal = Calendar.getInstance()
            val oldCal = Calendar.getInstance()
            oldCal.time = dateToCompare
            val oldYear = oldCal[Calendar.YEAR]
            val year = cal[Calendar.YEAR]
            val oldDay = oldCal[Calendar.DAY_OF_YEAR]
            val day = cal[Calendar.DAY_OF_YEAR]

            if (oldYear == year) {
                val value = oldDay - day
                when (value) {
                    -1 -> return "Yesterday"
                    0 -> return "Today"
                    1 -> return "Tomorrow"
                    else -> return date
                }
            } else {
                return date
            }

        } catch (e: java.lang.Exception) {
            return ""
        }
    }

    /**
     *  @date is the date in the String format
     *  @pattern is the the patter of the date
     *  It will return the today and yesterday  by comparing with the currentDate
     */


    fun getDateFromStringTime(time: String, pattern: String): Date? {
        try {
            val datFormat = SimpleDateFormat(pattern)
            return datFormat.parse(time) as Date
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun milliSecondsToMinSec(milliseconds: Long): String {
        val seconds: Long = milliseconds / 1000
        val minutes: Long = seconds / 60
        val hours: Long = minutes / 60
        return String.format("%d:%02d:%02d", hours, minutes, seconds)
    }


}