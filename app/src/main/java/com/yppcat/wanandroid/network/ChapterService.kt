package com.yppcat.wanandroid.network

import com.yppcat.wanandroid.network.data.ArticleList
import com.yppcat.wanandroid.network.data.Chapter
import com.yppcat.wanandroid.network.data.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChapterService {

    @GET("/wxarticle/chapters/json")
    fun getChapters(): Call<Chapter>

    @GET("/wxarticle/list/{id}/{page}/json")
    fun getArticleList(@Path("id") id: Int, @Path("page") page: Int): Call<ArticleList>

    @GET("/wxarticle/list/{id}/{page}/json?k={value}")
    fun searchArticle(@Path("id") id: Int, @Path("page") page: Int, @Path("value") value: String): Call<SearchResult>
}