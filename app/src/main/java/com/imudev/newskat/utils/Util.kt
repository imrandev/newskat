package com.imudev.newskat.utils

import android.graphics.Color
import java.text.SimpleDateFormat
import java.util.*

class CalendarUtil {

    companion object {
        val instance = CalendarUtil()
    }

    fun getCurrentTime() : String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return simpleDateFormat.format(calendar.time)
    }

    fun convertTimeToLocal(date : String) : String {
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val datetime = dateFormat.parse(date)
            return SimpleDateFormat("dd MMM hh:mm a", Locale.getDefault()).format(datetime)
        } catch (ex : Exception){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val datetime = dateFormat.parse(date)
            return SimpleDateFormat("dd MMM hh:mm a", Locale.getDefault()).format(datetime)
        }
    }

    fun getDate(date : String) : Date? {
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.parse(date)
        } catch (ex : Exception){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.parse(date)
        }
    }

    fun comparableTime(date : String) : String {
        val calendar = Calendar.getInstance()
        val diff: Long = calendar.time.time - getDate(date)?.time!!
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        if (hours == 0L && minutes == 0L){
            val sec = if (seconds > 1) "seconds" else "second"
            return "${seconds} ${sec} ago"
        } else if (hours == 0L){
            val min = if (minutes > 1) "minutes" else "minute"
            return "${minutes} ${min} ago"
        } else {
            val hr = if (hours > 1) "hours" else "hour"
            return "${hours} ${hr} ago"
        }
    }
}

class ColorUtil {

    companion object {
        var instance = ColorUtil()
    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    val mRandom = Random(System.currentTimeMillis())

    fun generateRandomColor(): Int {
        // This is the base color which will be mixed with the generated one
        val baseColor = Color.WHITE
        val baseRed = Color.red(baseColor)
        val baseGreen = Color.green(baseColor)
        val baseBlue = Color.blue(baseColor)
        val red = (baseRed + mRandom.nextInt(256)) / 2
        val green = (baseGreen + mRandom.nextInt(256)) / 2
        val blue = (baseBlue + mRandom.nextInt(256)) / 2
        return Color.rgb(red, green, blue)
    }
}

class ViewType {

    companion object {
        val EMPTY_VIEW = -1
        val LOADER_VIEW = 0
        val NORMAL_VIEW = 1
    }
}