package com.yppcat.wanandroid.model

import androidx.lifecycle.liveData
import com.yppcat.wanandroid.database.Interview
import com.yppcat.wanandroid.network.fire
import kotlinx.coroutines.Dispatchers
import org.litepal.LitePal
import org.litepal.extension.findAll
import java.lang.Exception

object InterModel {
    fun loadAllInterView() = liveData(Dispatchers.IO) {
        val result = try {

            val interResponse = LitePal.findAll<Interview>()
            println("interResponse ${interResponse.size}")
            Result.success(interResponse)
        } catch (e: Exception) {
            Result.failure<MutableList<Interview>>(e)
        }
        emit(result)
    }
}