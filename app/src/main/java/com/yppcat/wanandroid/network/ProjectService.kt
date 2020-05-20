package com.yppcat.wanandroid.network

import com.yppcat.wanandroid.network.data.ProjectData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectService {

    @GET("/article/listproject/{page}/json")
    fun getProjectList(@Path("page")page : Int):Call<ProjectData>
}