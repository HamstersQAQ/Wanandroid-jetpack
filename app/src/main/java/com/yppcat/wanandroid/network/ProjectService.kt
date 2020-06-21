package com.yppcat.wanandroid.network

import com.yppcat.wanandroid.network.data.BannerData
import com.yppcat.wanandroid.network.data.HomeArticle
import com.yppcat.wanandroid.network.data.ProjectData
import com.yppcat.wanandroid.network.data.TopArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectService {

    @GET("/article/listproject/{page}/json")
    fun getProjectList(@Path("page")page : Int):Call<ProjectData>

    @GET("/banner/json")
    fun getBannerList() : Call<BannerData>

    @GET("/article/top/json")
    fun getTopArticle() : Call<TopArticle>

    @GET("/article/list/{page}/json")
    fun getHomeArticle(@Path("page")page: Int) : Call<HomeArticle>
}