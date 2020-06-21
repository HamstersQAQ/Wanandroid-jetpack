package com.yppcat.wanandroid.model

import android.util.Log
import com.yppcat.wanandroid.network.WanNetwork
import com.yppcat.wanandroid.network.fire
import kotlinx.coroutines.Dispatchers

object ArticleModel {

    fun getChapters() = fire(Dispatchers.IO) {

        val chapterResponse = WanNetwork.getChapters()
        if (chapterResponse.errorCode == 0) {
            val chapters = chapterResponse.data
            Result.success(chapters)
        } else {
            Log.d("ArticleListViewModel","hh")
            Result.failure(RuntimeException("response status is ${chapterResponse.errorMsg}"))
        }
    }

    fun getArticles(id : Int,page : Int) = fire(Dispatchers.IO){
        val articleResponse  = WanNetwork.getArticles(id, page)
        if(articleResponse.errorCode == 0){
            val articles = articleResponse.data.datas
            Result.success(articles)
        }else{
            Result.failure(RuntimeException("response status is ${articleResponse.errorMsg}"))
        }
    }


}