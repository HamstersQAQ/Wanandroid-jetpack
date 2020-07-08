package com.yppcat.wanandroid.network

import com.yppcat.wanandroid.network.data.LogOutData
import com.yppcat.wanandroid.network.data.UserData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("/user/login")
    fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): Call<UserData>

    @POST("/user/register")
    fun register(
        @Field("username") userName: String, @Field("password") passWord: String,
        @Field("repassword") repassWord: String
    ): Call<UserData>

    @GET("/logout/json")
    fun logOut() :Call<LogOutData>
}