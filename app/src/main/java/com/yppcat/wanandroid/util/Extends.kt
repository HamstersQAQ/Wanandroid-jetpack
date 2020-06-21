package com.yppcat.wanandroid.util

import com.yppcat.wanandroid.database.Daily

fun Daily.equal(daily1: Daily): Boolean {
    return daily1.year == this.year && daily1.month == this.month && daily1.day == this.day
}