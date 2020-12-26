package com.smartperty.smartperty.tools

import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    //2018-03-22 19:02:12
    fun getCurrentDateTime(): String {
        return DateTimeFormatter
            .ofPattern("yyyy/MM/dd HH:mm")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    }

    fun getCurrentUnixTimeStamp(): Long {
        return DateTimeToStamp(getCurrentDateTime(), Locale.TAIWAN)
    }

    @JvmStatic
    fun StampToDateTime(time: Long, locale: Locale): String {
        // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意

        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", locale)

        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun DateTimeToStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", locale)

        /// 輸出為毫秒為單位
        return simpleDateFormat.parse(date).time
    }

    @JvmStatic
    fun StampToDate(time: Long, locale: Locale): String {
        // 進來的time以秒為單位，Date輸入為毫秒為單位，要注意

        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", locale)

        return simpleDateFormat.format(Date(time))
    }

    @JvmStatic
    fun DateToStamp(date: String, locale: Locale): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", locale)

        /// 輸出為毫秒為單位
        return simpleDateFormat.parse(date).time / 1000
    }
}

/*
        println(TimeUtil.StampToDate(1560839160000, Locale.TAIWAN))
        println(TimeUtil.DateToStamp("2019-06-18 14:26:00", Locale.TAIWAN))

以台灣為例，GMT+8
Date 格式是 "yyyy-MM-dd HH:mm:ss"
假如 台灣 Date = "2020-10-21 18:00:00"
轉換成時間戳是 1603274400
同時間
在 GMT +0 時區的地方 Date = "2020-10-21 10:00:00"
轉換成時間戳也是 1603274400
*/