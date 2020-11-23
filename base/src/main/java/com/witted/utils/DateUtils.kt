package com.witted.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val simpleYMD = "yyyy-MM-dd"
    private val simpleHM = "HH:mm"
    private val simpleHMS = "HH:mm:ss"
    private val simpleYMDHMS = "yyyy-MM-dd HH:mm:ss"

    private val threadLocal = ThreadLocal<SimpleDateFormat>()

    private val cal: Calendar by lazy {
        Calendar.getInstance()
    }

    init {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"))
    }

    private fun getDateFormat(): SimpleDateFormat {
        var dateFormat = threadLocal.get()
        if (dateFormat == null) {
            dateFormat = SimpleDateFormat()
            threadLocal.set(dateFormat)
        }
        return dateFormat
    }

    fun formatYMD(date: Date, pattern: String): String {
        getDateFormat().applyPattern(pattern)
        return getDateFormat().format(date)
    }

    fun simpleYMD(date: Date?): String {
        if (date == null) {
            return ""
        }
        getDateFormat().applyPattern(simpleYMD)
        return getDateFormat().format(date)
    }

    fun simpleHM(date: Date?): String {
        if (date == null) {
            return ""
        }
        getDateFormat().applyPattern(simpleHM)
        return getDateFormat().format(date)
    }

    fun simpleHMS(date: Date?): String {
        if (date == null) {
            return ""
        }
        getDateFormat().applyPattern(simpleHMS)
        return getDateFormat().format(date)
    }

    fun simpleYMDHMS(date: Date): String {
        getDateFormat().applyPattern(simpleYMDHMS)
        return getDateFormat().format(date)
    }

    fun getChineseWeek(date: Date): String {
        cal.time = date
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            1 -> {
                "星期日"
            }
            2 -> {
                "星期一"
            }
            3 -> {
                "星期二"
            }
            4 -> {
                "星期三"
            }
            5 -> {
                "星期四"
            }
            6 -> {
                "星期五"
            }
            else -> {
                "星期六"
            }
        }
    }
}