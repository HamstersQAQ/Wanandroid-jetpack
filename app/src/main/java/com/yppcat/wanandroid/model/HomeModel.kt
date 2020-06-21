package com.yppcat.wanandroid.model

import androidx.lifecycle.liveData
import com.yppcat.wanandroid.network.WanNetwork
import com.yppcat.wanandroid.network.fire
import kotlinx.coroutines.Dispatchers

class HomeModel {

    fun loadBannerData() = fire(Dispatchers.IO) {
        val interResponse = WanNetwork.getBannerData()
        if (interResponse.errorCode == 0) {
            Result.success(interResponse.data)
        } else {
            Result.failure(Exception("response status is ${interResponse.errorMsg}"))
        }
    }

    fun loadTopArticle() = fire(Dispatchers.IO){
        val response = WanNetwork.getTopArticle()
        if (response.errorCode == 0){
            Result.success(response.data)
        }else{
            Result.failure(Exception("response status is ${response.errorMsg}"))
        }
    }

    fun loadHomeArticle(page : Int) = fire(Dispatchers.IO){
        val response = WanNetwork.getHomeArticle(page)
        if (response.errorCode == 0){
            Result.success(response.data.datas)
        }else{
            Result.failure(Exception("response status is ${response.errorMsg}"))
        }
    }
}