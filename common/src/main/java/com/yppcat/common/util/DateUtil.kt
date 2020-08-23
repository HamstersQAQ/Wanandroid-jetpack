@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.yppcat.common.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object DateUtil {

    fun ms2Year(ms: Long): Int {
        val year = SimpleDateFormat("yyyy").format(Date(ms))
        return year.toInt()
    }

    fun ms2Month(ms : Long) :Int{
        val month = SimpleDateFormat("MM").format(Date(ms))
        return month.toInt()
    }

    fun ms2Day(ms : Long) :Int{
        val day = SimpleDateFormat("dd").format(Date(ms))
        return day.toInt()
    }

    fun ms2Hour(ms : Long) :Int{
        val hour = SimpleDateFormat("H").format(Date(ms))
        return hour.toInt()
    }

    fun ms2Minute(ms : Long) :Int{
        val minute = SimpleDateFormat("mm").format(Date(ms))
        return minute.toInt()
    }


}