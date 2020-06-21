package com.yppcat.wanandroid.model

import androidx.lifecycle.liveData
import com.yppcat.wanandroid.database.Daily
import com.yppcat.wanandroid.database.Interview
import kotlinx.coroutines.Dispatchers
import org.litepal.LitePal
import org.litepal.extension.findAll
import java.lang.Exception

class DailyModel() {

    fun saveDailyData(daily: Daily){
        daily.save()
    }

    fun loadAllInterView() = liveData(Dispatchers.IO) {
        val result = try {
            val interResponse = LitePal.findAll<Daily>()
            println("interResponse ${interResponse.size}")
            Result.success(interResponse)
        } catch (e: Exception) {
            Result.failure<MutableList<Daily>>(e)
        }
        emit(result)
    }

    fun loadLast() : Daily?{
        return LitePal.findLast(Daily::class.java)
    }

}